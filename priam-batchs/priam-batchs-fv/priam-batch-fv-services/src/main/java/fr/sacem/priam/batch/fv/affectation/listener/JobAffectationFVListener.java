package fr.sacem.priam.batch.fv.affectation.listener;

import fr.sacem.priam.batch.common.dao.ProgrammeBatchDao;
import fr.sacem.priam.batch.common.service.importPenef.FichierBatchService;
import fr.sacem.priam.batch.common.util.UtilFile;
import fr.sacem.priam.model.dao.jpa.FichierDao;
import fr.sacem.priam.model.dao.jpa.JournalBatchDao;
import fr.sacem.priam.model.domain.Fichier;
import fr.sacem.priam.model.domain.Journal;
import fr.sacem.priam.model.domain.Status;
import fr.sacem.priam.model.util.JournalAffectationBuilder;
import fr.sacem.priam.services.FichierService;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JobAffectationFVListener extends JobExecutionListenerSupport {
    public static final String ERREUR_ELIGIBILITE = "ERREUR_ELIGIBILITE";
    public static final String CHARGEMENT_OK = "CHARGEMENT_OK";


    @Autowired
    private FichierBatchService fichierBatchService;

    private UtilFile utilFile;


    @Autowired
    ProgrammeBatchDao programmeBatchDao;

    @Autowired
    FichierService fichierService;

    @Autowired
    JournalBatchDao journalBatchDao;


    @Autowired
    private FichierDao fichierDao;

    /*@Autowired
    private ProgrammeViewDao programmeViewDao;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;*/


    @Autowired
    public JobAffectationFVListener() {

        utilFile = new UtilFile();
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {

        String numProg = jobExecution.getJobParameters().getString("numProg");
        String[] fichiersAffectes = jobExecution.getJobParameters().getString("fichiersAffectes").split(",");
        List<Long> fichiersAffectesIds = Stream.of(fichiersAffectes).map(Long::parseLong).collect(Collectors.toList());
        List<Fichier> listFichiersByIds = fichierService.findListFichiersByIds(fichiersAffectesIds);
        String userId = jobExecution.getJobParameters().getString("userId");
        fichierService.majFichiersAffectesAuProgramme(numProg, listFichiersByIds, userId);
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


        //final ProgrammeDto payload = programmeViewDao.findByNumProg(numProg);
        //simpMessagingTemplate.convertAndSend("/global-message/affectation", payload);

    }

    public FichierBatchService getFichierBatchService() {
        return fichierBatchService;
    }

    public void setFichierBatchService(FichierBatchService fichierBatchService) {
        this.fichierBatchService = fichierBatchService;
    }
}

