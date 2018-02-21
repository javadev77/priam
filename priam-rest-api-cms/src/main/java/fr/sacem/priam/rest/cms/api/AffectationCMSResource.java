package fr.sacem.priam.rest.cms.api;

import com.google.common.base.Strings;
import fr.sacem.domain.Admap;
import fr.sacem.priam.common.TypeUtilisationEnum;
import fr.sacem.priam.model.dao.jpa.ProgrammeViewDao;
import fr.sacem.priam.model.dao.jpa.cms.TraitementEligibiliteCMSDao;
import fr.sacem.priam.model.dao.jpa.cp.ProgrammeDao;
import fr.sacem.priam.model.domain.Fichier;
import fr.sacem.priam.model.domain.Programme;
import fr.sacem.priam.model.domain.StatutEligibilite;
import fr.sacem.priam.model.domain.dto.AffectationDto;
import fr.sacem.priam.model.domain.dto.ProgrammeDto;
import fr.sacem.priam.security.model.UserDTO;
import fr.sacem.priam.services.FichierService;
import fr.sacem.priam.services.ProgrammeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    JobLauncher jobLauncher;

    @Autowired
    Job jobEligibiliteOctav;

    @Autowired
    Job jobDesaffectation;

    @Autowired
    Job jobEligibiliteCMSAntille;

    @Autowired
    Admap admap;


    @RequestMapping(value = "programme/affectation",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ProgrammeDto affecterFichiers (@RequestBody AffectationDto affectationDto, UserDTO currentUser) {

        ProgrammeDto programmeDto = null;
        String numProg = affectationDto.getNumProg();
        List<Fichier> fichiers = affectationDto.getFichiers();

        if(!Strings.isNullOrEmpty(numProg)){
            fichierService.majFichiersAffectesAuProgramme(numProg, fichiers, currentUser.getDisplayName());
            programmeDto = programmeViewDao.findByNumProg(numProg);
        }

        //lancer le job
        LOGGER.info("====== Lancement du job Affectation CMS ======");

        try {

            Map<String, JobParameter> jobParametersMap = new HashMap<>();
            jobParametersMap.put("time", new JobParameter(System.currentTimeMillis()));
            jobParametersMap.put("input.catalog.octav", new JobParameter(admap.getInputFile()));
            jobParametersMap.put("archives.catalog.octav", new JobParameter(admap.getOutputFile()));
            jobParametersMap.put("numProg", new JobParameter(numProg));

            JobParameters jobParameters = new JobParameters(jobParametersMap);

            String typeUtilisation = programmeDto != null  ? programmeDto.getTypeUtilisation() : "";
            if (TypeUtilisationEnum.CMS_FRA.getCode().equals(typeUtilisation)){
                jobLauncher.run(jobEligibiliteOctav, jobParameters);
            } else if (TypeUtilisationEnum.CMS_ANT.getCode().equals(typeUtilisation)) {
                jobLauncher.run(jobEligibiliteCMSAntille, jobParameters);
            }

        } catch (Exception e) {
            LOGGER.error("Error d'exécution du Batch Affectation CMS", e);
        }

        LOGGER.info("====== Fin de Traitement ======");

        return programmeDto;
    }


    @RequestMapping(value = "programme/toutDesaffecter",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ProgrammeDto desaffecterFichiers (@RequestBody String numProg, UserDTO userDTO){
        LOGGER.info("desaffecterFichiers() ==> numProg=" + numProg);

        Programme programme = programmeDao.findOne(numProg);
        programme.setStatutEligibilite(StatutEligibilite.EN_COURS_DESAFFECTATION);
        programmeDao.saveAndFlush(programme);

        //lancer le job
        LOGGER.info("====== Lancement du job desaffectation CMS ======");

        try {

            Map<String, JobParameter> jobParametersMap = new HashMap<>();
            jobParametersMap.put("time", new JobParameter(System.currentTimeMillis()));
            jobParametersMap.put("numProg", new JobParameter(numProg));
            jobParametersMap.put("username", new JobParameter(userDTO.getDisplayName()));

            JobParameters jobParameters = new JobParameters(jobParametersMap);

            jobLauncher.run(jobDesaffectation, jobParameters);

        } catch (Exception e) {
            LOGGER.error("Error d'exécution du job desaffectation CMS", e);
        }

        LOGGER.info("====== Fin de Traitement ======");

        return new ProgrammeDto();
    }



}
