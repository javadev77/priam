package fr.sacem.priam.batch.affectation.cp.listener;

import fr.sacem.priam.model.dao.jpa.JournalBatchDao;
import fr.sacem.priam.batch.common.dao.ProgrammeBatchDao;
import fr.sacem.priam.batch.common.service.importPenef.FichierBatchService;
import fr.sacem.priam.batch.common.util.UtilFile;
import fr.sacem.priam.model.dao.jpa.FichierDao;
import fr.sacem.priam.model.domain.Fichier;
import fr.sacem.priam.model.domain.Journal;
import fr.sacem.priam.model.domain.Status;
import fr.sacem.priam.model.util.JournalAffectationBuilder;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class JobCompletionNotificationAffectationCPListener extends JobExecutionListenerSupport {
    public static final String ERREUR_ELIGIBILITE = "ERREUR_ELIGIBILITE";
    public static final String CHARGEMENT_OK = "CHARGEMENT_OK";

    @Autowired
    private FichierBatchService fichierBatchService;

    private UtilFile utilFile;
    private static final Logger LOG = LoggerFactory.getLogger(JobCompletionNotificationAffectationCPListener.class);

    @Autowired
    ProgrammeBatchDao programmeBatchDao;

    @Autowired
    JournalBatchDao journalBatchDao;


    @Autowired
    private FichierDao fichierDao;

    @Autowired
    public JobCompletionNotificationAffectationCPListener() {

        utilFile = new UtilFile();
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {

    }

    @Override
    public void afterJob(JobExecution jobExecution) {

        String numProg = jobExecution.getJobParameters().getString("numProg");
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {


            String userId = jobExecution.getJobParameters().getString("userId");
            String listNomFichier = jobExecution.getJobParameters().getString("listNomFichier");


            programmeBatchDao.majStattutEligibilite(numProg, "FIN_ELIGIBILITE");
            programmeBatchDao.majStattutProgramme(numProg, "AFFECTE");

            List<Fichier> listFichierAffecte = fichierDao.findFichiersByIdProgramme(numProg, Status.AFFECTE);
            JournalAffectationBuilder journalAffectationBuilder = new JournalAffectationBuilder();
            Journal journal = journalAffectationBuilder.create(numProg, listNomFichier, listFichierAffecte, userId);

            Long idJournal = journalBatchDao.saveJournal(journal);

            journalBatchDao.saveSituationAvantJournal(journal.getListSituationAvant(), idJournal);
            journalBatchDao.saveSituationApresJournal(journal.getListSituationApres(), idJournal);

        } else {

            programmeBatchDao.majStattutEligibilite(numProg, ERREUR_ELIGIBILITE);
            fichierBatchService.clearSelectedFichiers(numProg, CHARGEMENT_OK);
        }
    }

    public FichierBatchService getFichierBatchService() {
        return fichierBatchService;
    }

    public void setFichierBatchService(FichierBatchService fichierBatchService) {
        this.fichierBatchService = fichierBatchService;
    }
}

