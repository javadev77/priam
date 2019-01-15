package fr.sacem.priam.rest.copieprivee.api;

import com.google.common.base.Strings;
import fr.sacem.priam.model.dao.jpa.FichierDao;
import fr.sacem.priam.model.dao.jpa.JournalDao;
import fr.sacem.priam.model.dao.jpa.ProgrammeViewDao;
import fr.sacem.priam.model.dao.jpa.cp.ProgrammeDao;
import fr.sacem.priam.model.domain.Fichier;
import fr.sacem.priam.model.domain.Journal;
import fr.sacem.priam.model.domain.Programme;
import fr.sacem.priam.model.domain.StatutEligibilite;
import fr.sacem.priam.model.domain.dto.AffectationDto;
import fr.sacem.priam.model.domain.dto.DesaffectationDto;
import fr.sacem.priam.model.domain.dto.ProgrammeDto;
import fr.sacem.priam.model.util.JournalAffectationBuilder;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by benmerzoukah on 16/11/2017.
 */
@RestController
@RequestMapping("/app/rest/")
public class AffectationCPResource extends AffectationResource {
    private static Logger LOGGER = LoggerFactory.getLogger(AffectationCPResource.class);

    @Autowired
    private ProgrammeViewDao programmeViewDao;

    @Autowired
    AffectationUtil affectationUtil;

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job jobAffectationCP;

    @Autowired
    private ProgrammeDao programmeDao;

    @Autowired
    private Job jobDesaffectationCP;

    @Autowired
    private JournalDao journalDao;

    @Autowired
    private FichierDao fichierDao;

    @Autowired
    private PlatformTransactionManager transactionManager;


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


            if(fichiers == null || fichiers.isEmpty()) {
                JournalAffectationBuilder journalAffectationBuilder = new JournalAffectationBuilder();
                Journal journal = journalAffectationBuilder.create(numProg, listNomFichiersAvantAffectation, fichiers, currentUser.getUserId());
                journalDao.save(journal);

                transactionManager.commit(ts);

                return programmeDto;
            }

            if(!Strings.isNullOrEmpty(numProg)){
                programmeDto = programmeViewDao.findByNumProg(numProg);
            }

            Programme programme = programmeDao.findByNumProg(numProg);
            programme.setStatutEligibilite(StatutEligibilite.EN_COURS_DESAFFECTATION);

            programmeDao.saveAndFlush(programme);

            transactionManager.commit(ts);


            launchJobAffectation(numProg, fichiers,  currentUser, listNomFichiersAvantAffectation);

        }catch (Exception ex) {
            LOGGER.error("Erreur lors l'affectation du programme ", ex);
            transactionManager.rollback(ts);
        }



        return programmeDto;
    }

    private void launchJobAffectation(String numProg, List<Fichier> fichiers, UserDTO userDTO, String listNomFichiersAvantAffectation) {
        //lancer le job
        LOGGER.info("====== Lancement du job Affectation CP ======");

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

            jobLauncher.run(jobAffectationCP, jobParameters);


        } catch (Exception e) {
            LOGGER.error("Error d'exécution du Batch Affectation CP", e);
        }

        LOGGER.info("====== Fin de Traitement ======");
    }


    @RequestMapping(value = "programme/toutDesaffecter",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    //@LogFichier(event = TypeLog.ALL_DESAFFECTATION)
    public ProgrammeDto deaffecterFichiers (@RequestBody DesaffectationDto desaffectationDto, UserDTO userDTO){
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



        //lancer le job
        LOGGER.info("====== Lancement du job desaffectation CP ======");

        try {

            Map<String, JobParameter> jobParametersMap = new HashMap<>();
            jobParametersMap.put("time", new JobParameter(System.currentTimeMillis()));
            jobParametersMap.put("numProg", new JobParameter(desaffectationDto.getNumProg()));
            jobParametersMap.put("username", new JobParameter(userDTO.getDisplayName()));
            jobParametersMap.put("isAllDesaffecte", new JobParameter(String.valueOf(desaffectationDto.isAllDesaffecte())));
            jobParametersMap.put("userId", new JobParameter(userDTO.getUserId()));
            jobParametersMap.put("listIdFichiersAllDesaffectes", new JobParameter(StringUtils.join(desaffectationDto.getFichersAvantDesaffectation(), ',')));



            JobParameters jobParameters = new JobParameters(jobParametersMap);

            jobLauncher.run(jobDesaffectationCP, jobParameters);

        } catch (Exception e) {
            LOGGER.error("Error d'exécution du job desaffectation CP", e);
        }

        LOGGER.info("====== Fin de Traitement ======");

        return new ProgrammeDto();
    }




}
