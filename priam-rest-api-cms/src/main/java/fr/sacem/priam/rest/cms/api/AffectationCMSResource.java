package fr.sacem.priam.rest.cms.api;

import com.google.common.base.Strings;
import fr.sacem.domain.Admap;
import fr.sacem.priam.common.TypeUtilisationEnum;
import fr.sacem.priam.model.dao.jpa.FichierDao;
import fr.sacem.priam.model.dao.jpa.JournalDao;
import fr.sacem.priam.model.dao.jpa.ProgrammeViewDao;
import fr.sacem.priam.model.dao.jpa.cp.ProgrammeDao;
import fr.sacem.priam.model.domain.*;
import fr.sacem.priam.model.domain.dto.AffectationDto;
import fr.sacem.priam.model.domain.dto.DesaffectationDto;
import fr.sacem.priam.model.domain.dto.FileDto;
import fr.sacem.priam.model.domain.dto.ProgrammeDto;
import fr.sacem.priam.model.util.JournalAffectationBuilder;
import fr.sacem.priam.security.model.UserDTO;
import fr.sacem.priam.services.FichierService;

import fr.sacem.priam.services.utils.AffectationUtil;
import org.apache.commons.lang.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by benmerzoukah on 16/11/2017.
 */
@RestController
@RequestMapping("/app/rest/")
public class AffectationCMSResource {
    private static Logger LOGGER = LoggerFactory.getLogger(AffectationCMSResource.class);

    @Autowired
    private FichierService fichierService;

    @Autowired
    private ProgrammeViewDao programmeViewDao;

    @Autowired
    private ProgrammeDao programmeDao;

    @Autowired
    private FichierDao fichierDao;

    @Autowired
    private JournalDao journalDao;

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job jobEligibiliteOctav;

    @Autowired
    Job jobDesaffectation;

    @Autowired
    Job jobEligibiliteCMSAntille;

    @Autowired
    AffectationUtil affectationUtil;

    @Autowired
    Admap admap;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");


    @RequestMapping(value = "programme/affectation",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ProgrammeDto affecterFichiers (@RequestBody AffectationDto affectationDto, UserDTO currentUser) {

        String numProg = affectationDto.getNumProg();
        ProgrammeDto programmeDto =  programmeViewDao.findByNumProg(numProg);
        List<Fichier> fichiers = affectationUtil.getFichiersAffectes(affectationDto);

        List<Fichier> fichiersAvantAffectation = getListFichierByIdFichier(affectationDto.getFichersAvantAffectation());
        String listNomFichiersAvantAffectation = getListNomFichier(fichiersAvantAffectation);

        if(fichiers == null || fichiers.isEmpty()) {
            JournalAffectationBuilder journalAffectationBuilder = new JournalAffectationBuilder();
            Journal journal = journalAffectationBuilder.create(programmeDto.getNumProg(), listNomFichiersAvantAffectation, fichiers, currentUser.getUserId());
            journalDao.save(journal);
            return programmeDto;
        }

        if(!Strings.isNullOrEmpty(numProg)){
            fichierService.majFichiersAffectesAuProgramme(numProg, fichiers, currentUser.getDisplayName());
            programmeDto = programmeViewDao.findByNumProg(numProg);
        }



        launchJobAffectation(programmeDto, currentUser, listNomFichiersAvantAffectation);


        return programmeDto;
    }

    protected void launchJobAffectation(ProgrammeDto programmeDto, UserDTO userDTO, String listNomFichiersAvantAffectation) {
        //lancer le job
        LOGGER.info("====== Lancement du job Affectation CMS ======");

        try {

            Map<String, JobParameter> jobParametersMap = new HashMap<>();
            jobParametersMap.put("time", new JobParameter(System.currentTimeMillis()));
            jobParametersMap.put("input.catalog.octav", new JobParameter(admap.getInputFile()));
            jobParametersMap.put("archives.catalog.octav", new JobParameter(admap.getOutputFile()));
            jobParametersMap.put("numProg", new JobParameter(programmeDto.getNumProg()));
            jobParametersMap.put("userId", new JobParameter(userDTO.getUserId()));

//            String listNomFichier = fichiers.stream().map(e -> e.getNomFichier() + " " + e.getDateFinChargt()).collect(Collectors.joining(","));
            jobParametersMap.put("listNomFichier", new JobParameter(listNomFichiersAvantAffectation));

            JobParameters jobParameters = new JobParameters(jobParametersMap);

            String typeUtilisation = programmeDto.getTypeUtilisation();
            if (TypeUtilisationEnum.CMS_FRA.getCode().equals(typeUtilisation)){
                jobLauncher.run(jobEligibiliteOctav, jobParameters);
            } else if (TypeUtilisationEnum.CMS_ANT.getCode().equals(typeUtilisation)) {
                jobLauncher.run(jobEligibiliteCMSAntille, jobParameters);
            }

        } catch (Exception e) {
            LOGGER.error("Error d'exécution du Batch Affectation CMS", e);
        }

        LOGGER.info("====== Fin de Traitement ======");
    }


    @RequestMapping(value = "programme/toutDesaffecter",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ProgrammeDto desaffecterFichiers (@RequestBody DesaffectationDto desaffectationDto, UserDTO userDTO){
        LOGGER.info("desaffecterFichiers() ==> numProg=" + desaffectationDto.getNumProg());

        Programme programme = programmeDao.findByNumProg(desaffectationDto.getNumProg());

        programme.setStatutEligibilite(StatutEligibilite.EN_COURS_DESAFFECTATION);
        programmeDao.saveAndFlush(programme);

        //lancer le job
        LOGGER.info("====== Lancement du job desaffectation CMS ======");

        try {

            Map<String, JobParameter> jobParametersMap = new HashMap<>();
            jobParametersMap.put("time", new JobParameter(System.currentTimeMillis()));
            jobParametersMap.put("numProg", new JobParameter(desaffectationDto.getNumProg()));
            jobParametersMap.put("username", new JobParameter(userDTO.getDisplayName()));
            jobParametersMap.put("isAllDesaffecte", new JobParameter(String.valueOf(desaffectationDto.isAllDesaffecte())));
            jobParametersMap.put("userId", new JobParameter(userDTO.getUserId()));
            jobParametersMap.put("listIdFichiersAllDesaffectes", new JobParameter(StringUtils.join(desaffectationDto.getFichersAvantDesaffectation(), ',')));




            JobParameters jobParameters = new JobParameters(jobParametersMap);

            jobLauncher.run(jobDesaffectation, jobParameters);

        } catch (Exception e) {
            LOGGER.error("Error d'exécution du job desaffectation CMS", e);
        }

        LOGGER.info("====== Fin de Traitement ======");

        return new ProgrammeDto();
    }

    @RequestMapping(value = "allFichiersAffectesByNumprog/{numProg}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<FileDto> findFichiersAffectesByIdProgramme(@PathVariable("numProg") String numProg) {
        return fichierDao.findFichiersAffecteByIdProgramme(numProg, Status.AFFECTE);

    }

    private String getListNomFichier(List<Fichier> fichiers){
        List<FileDto> list = new ArrayList<>();
        fichiers.forEach(fichier -> {
            list.add(fichierDao.findById(fichier.getId()));
        });
        return list.stream().map(e -> e.getNomFichier() + " " + simpleDateFormat.format(e.getDateFinChargt())).collect(Collectors.joining(","));
    }

    private List<Fichier> getListFichierByIdFichier(List<Long> listIdFichier){
        List<Fichier> result = new ArrayList<>();
        listIdFichier.forEach(id -> {
            result.add(fichierDao.findOne(id));
        });
        return result;
    }



}
