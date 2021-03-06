package fr.sacem.priam.rest.valorisation.api;

import com.google.common.base.Strings;
import fr.sacem.priam.model.dao.jpa.FichierDao;
import fr.sacem.priam.model.dao.jpa.ProgrammeViewDao;
import fr.sacem.priam.model.dao.jpa.cp.ProgrammeDao;
import fr.sacem.priam.model.domain.*;
import fr.sacem.priam.model.domain.dto.AffectationDto;
import fr.sacem.priam.model.domain.dto.DesaffectationDto;
import fr.sacem.priam.model.domain.dto.FileDto;
import fr.sacem.priam.model.domain.dto.ProgrammeDto;
import fr.sacem.priam.security.model.UserDTO;
import fr.sacem.priam.services.api.AffectationResource;
import fr.sacem.priam.services.utils.AffectationUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by embouazzar on 10/12/2018.
 */
@RestController
@RequestMapping("/app/rest/")
public class AffectationFVResource extends AffectationResource {
    private static Logger LOGGER = LoggerFactory.getLogger(AffectationFVResource.class);

    private static final String TYPE_REPART_OEUVRE = "OEUVRE";

    @Autowired
    private ProgrammeViewDao programmeViewDao;

    @Autowired
    AffectationUtil affectationUtil;

    @Autowired
    private FichierDao fichierDao;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    ProgrammeDao programmeDao;

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job jobAffectationFv;

    @Autowired
    Job jobAffectationFvAyantDroit;

    @Autowired
    Job jobDesaffectationFV;

    @RequestMapping(value = "programme/affectation",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ProgrammeDto affecterFichiers (@RequestBody AffectationDto affectationDto, UserDTO currentUser) {
        ProgrammeDto programmeDto = null;
        TransactionStatus ts = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {

            String numProg = affectationDto.getNumProg();

            programmeDto = programmeViewDao.findByNumProg(numProg);
            List<Fichier> fichiers = affectationUtil.getFichiersAffectes(affectationDto);

            List<Fichier> fichiersAvantAffectation = getListFichierByIdFichier(affectationDto.getFichersAvantAffectation());
            String listNomFichiersAvantAffectation = getListNomFichier(fichiersAvantAffectation);

            Programme programme = programmeDao.findByNumProg(numProg);
            programme.setStatutEligibilite(StatutEligibilite.EN_COURS_DESAFFECTATION);

            programmeDao.save(programme);
            transactionManager.commit(ts);


            if(fichiers == null || fichiers.isEmpty()) {

                launchJobDesaffectation(numProg, currentUser.getDisplayName(),
                    false, currentUser.getUserId(), "", true);

                if(!isTypeRepartOeuvre(numProg)){
                    fichierDao.updateStatutEnrichissementFichiersAffectes(numProg);
                }
                return programmeDto;
            }

            if(!Strings.isNullOrEmpty(numProg)){
                programmeDto = programmeViewDao.findByNumProg(numProg);
            }

            launchJobAffectation(numProg, fichiers,  currentUser, listNomFichiersAvantAffectation);

        }catch (Exception ex) {
            LOGGER.error("Erreur lors l'affectation du programme ", ex);
            transactionManager.rollback(ts);
        }



        return programmeDto;
    }

    private void launchJobAffectation(String numProg, List<Fichier> fichiers, UserDTO userDTO, String listNomFichiersAvantAffectation) {
        //lancer le job
        LOGGER.info("====== Lancement du job Affectation FV ======");

        try {

            Map<String, JobParameter> jobParametersMap = new HashMap<>();
            jobParametersMap.put("time", new JobParameter(System.currentTimeMillis()));
            jobParametersMap.put("numProg", new JobParameter(numProg));
            jobParametersMap.put("currentUser" , new JobParameter(userDTO.getDisplayName()));
            List<Long> collectedIds = fichiers.stream().map(Fichier::getId).collect(Collectors.toList());

            jobParametersMap.put("fichiersAffectes", new JobParameter(StringUtils.join(collectedIds, ",")));

            jobParametersMap.put("userId", new JobParameter(userDTO.getUserId()));
            jobParametersMap.put("listNomFichier", new JobParameter(listNomFichiersAvantAffectation));


            JobParameters jobParameters = new JobParameters(jobParametersMap);


            if(isTypeRepartOeuvre(numProg)){
                jobLauncher.run(jobAffectationFv, jobParameters);
            } else {
                jobLauncher.run(jobAffectationFvAyantDroit, jobParameters);
            }



        } catch (Exception e) {
            LOGGER.error("Error d'exécution du Batch Affectation CP", e);
        }

        LOGGER.info("====== Fin de Traitement ======");
    }


    private boolean isTypeRepartOeuvre(String numProg) {
        ProgrammeDto programmeDto = programmeViewDao.findByNumProg(numProg);
        return TYPE_REPART_OEUVRE.equals(programmeDto.getTypeRepart().name());
    }


    private void launchJobDesaffectation(String numProg, String userName,
        Boolean isAllDesaffecte, String userId, String listIdFichiersAllDesaffectes, Boolean isFichiersAffectesVide) {
        //lancer le job
        LOGGER.info("====== Lancement du job desaffectation FV ======");

        try {

            Map<String, JobParameter> jobParametersMap = new HashMap<>();
            jobParametersMap.put("time", new JobParameter(System.currentTimeMillis()));
            jobParametersMap.put("numProg", new JobParameter(numProg));
            jobParametersMap.put("username", new JobParameter(userName));
            jobParametersMap.put("isAllDesaffecte", new JobParameter(String.valueOf(isAllDesaffecte)));
            jobParametersMap.put("userId", new JobParameter(userId));
            jobParametersMap.put("listIdFichiersAllDesaffectes", new JobParameter(listIdFichiersAllDesaffectes));
            jobParametersMap.put("isFichiersAffectesVide", new JobParameter(String.valueOf(isFichiersAffectesVide)));

            JobParameters jobParameters = new JobParameters(jobParametersMap);

            jobLauncher.run(jobDesaffectationFV, jobParameters);

        } catch (Exception e) {
            LOGGER.error("Error d'exécution du job desaffectation FV", e);
        }

    }

    @RequestMapping(value = "programme/toutDesaffecter",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ProgrammeDto desaffecterFichiers (@RequestBody DesaffectationDto desaffectationDto, UserDTO userDTO){
        LOGGER.info("desaffecterFichiers() ==> numProg=" + desaffectationDto.getNumProg());

        TransactionStatus ts = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {

            Programme programme = programmeDao.findByNumProg(desaffectationDto.getNumProg());

            programme.setStatutEligibilite(StatutEligibilite.EN_COURS_DESAFFECTATION);
            programmeDao.saveAndFlush(programme);

            transactionManager.commit(ts);

        }catch (Exception ex) {
            LOGGER.error("Erreur lors du commit de la transaction !!! ", ex);
            transactionManager.rollback(ts);
        }

        launchJobDesaffectation(desaffectationDto.getNumProg(), userDTO.getDisplayName(),
            desaffectationDto.isAllDesaffecte(), userDTO.getUserId(), StringUtils.join(desaffectationDto.getFichersAvantDesaffectation(), ','),
            false);


        return new ProgrammeDto();
    }

    @RequestMapping(value = "allFichiersAffectesByNumprog/{numProg}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<FileDto> findFichiersAffectesByIdProgramme(@PathVariable("numProg") String numProg) {
        return fichierDao.findFichiersAffecteByIdProgramme(numProg, Status.AFFECTE);
    }



}
