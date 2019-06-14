package fr.sacem.priam.batch.affectation.cp.listener;

import fr.sacem.priam.batch.affectation.cp.dao.JournalBatchDao;
import fr.sacem.priam.batch.common.dao.ProgrammeBatchDao;
import fr.sacem.priam.batch.common.service.importPenef.FichierBatchService;
import fr.sacem.priam.batch.common.util.UtilFile;
import fr.sacem.priam.common.TypeLog;
import fr.sacem.priam.model.dao.jpa.FichierDao;
import fr.sacem.priam.model.dao.jpa.ProgrammeViewDao;
import fr.sacem.priam.model.domain.Fichier;
import fr.sacem.priam.model.domain.Journal;
import fr.sacem.priam.model.domain.SituationApres;
import fr.sacem.priam.model.domain.SituationAvant;
import fr.sacem.priam.model.domain.Status;
import fr.sacem.priam.model.domain.dto.ProgrammeDto;
import fr.sacem.priam.model.journal.JournalBuilder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;


@Component
public class JobCompletionNotificationAffectationCPListener extends JobExecutionListenerSupport {
    public static final String LIGNE_PROGRAMME_ERRORS = "ligne-programme-errors";
    public static final String MESSAGE_ERREUR_TECHNIQUE = "Le chargement a été interrompu à cause d'un problème technique";
    public static final String MESSAGE_FICHIER_CHARGE = " - Le fichier \"%s\" a bien été chargé";
    public static final String FORMAT_DATE = "dd/MM/yyyy HH:mm";
    public static final String MESSAGE_FORMAT_FICHIER = "Le fichier ne peut être chargé car il n'a pas le bon format";
    public static final String ERREUR_ELIGIBILITE = "ERREUR_ELIGIBILITE";
    public static final String CHARGEMENT_OK = "CHARGEMENT_OK";
    public static final String FICHIERS_AVANT_AFFECTATION = "FICHIERS_AVANT_AFFECTATION";
    private static String NOM_FICHIER_CSV_EN_COURS = "nomFichier";
    private static String FICHIER_ZIP_EN_COURS = "fichierZipEnCours";
    private static String NOM_ORIGINAL_FICHIER_ZIP = "nomFichierOriginal";
    private static String REPERTOIRE_DE_DESTINATION = "archives.catalog.octav";
    private static String FILE_ERREUR = "erreur";
    private ExecutionContext executionContext;

    @Autowired
    private FichierBatchService fichierBatchService;

    private UtilFile utilFile;
    private static final Logger LOG = LoggerFactory.getLogger(JobCompletionNotificationAffectationCPListener.class);

    @Autowired
    ProgrammeBatchDao programmeBatchDao;

    @Autowired
    JournalBatchDao journalBatchDao;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    @Autowired
    private FichierDao fichierDao;

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private ProgrammeViewDao programmeViewDao;

    @Autowired
    public JobCompletionNotificationAffectationCPListener() {

        utilFile = new UtilFile();
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {
        String numProg = jobExecution.getJobParameters().getString("numProg");
        /*String[] fichiersAffectes = jobExecution.getJobParameters().getString("fichiersAffectes").split(",");
        List<Long> fichiersAffectesIds = Stream.of(fichiersAffectes).map(Long::parseLong).collect(Collectors.toList());
        List<Fichier> listFichiersByIds = fichierService.findListFichiersByIds(fichiersAffectesIds);
        fichierService.majFichiersAffectesAuProgramme(numProg, listFichiersByIds, "GUEST");*/
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        String numProg = jobExecution.getJobParameters().getString("numProg");

        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {


            String userId = jobExecution.getJobParameters().getString("userId");
            String listNomFichier = jobExecution.getJobParameters().getString("listNomFichier");


            programmeBatchDao.majStattutEligibilite(numProg, "FIN_ELIGIBILITE");
            programmeBatchDao.majStattutProgramme(numProg, "AFFECTE");




            List<SituationAvant> situationAvantList = new ArrayList<>();
            List<SituationApres> situationApresList = new ArrayList<>();

            if(!listNomFichier.isEmpty()) {
                List<String> listNomFichierAvantAffecte = Arrays.asList(listNomFichier.split("\\s*,\\s*"));
                listNomFichierAvantAffecte.forEach(NomFichierAvantAffecte -> {
                    SituationAvant situationAvant = new SituationAvant();
                    situationAvant.setSituation(NomFichierAvantAffecte);
                    situationAvantList.add(situationAvant);
                });
            }

            List<Fichier> listFichierAffecte = fichierDao.findFichiersByIdProgramme(numProg, Status.AFFECTE);
            listFichierAffecte.forEach(fichierAffecte ->{
                SituationApres situationApres = new SituationApres();
                situationApres.setSituation(fichierAffecte.getNomFichier() + " " + simpleDateFormat.format(fichierAffecte.getDateFinChargt()));
                situationApresList.add(situationApres);
            });

            JournalBuilder journalBuilder = new JournalBuilder(numProg,null,userId);
            Journal journal = journalBuilder.addEvenement(TypeLog.AFFECTATION_DESAFFECTATION.getEvenement()).build();
            journal.setListSituationAvant(situationAvantList);
            journal.setListSituationApres(situationApresList);

            Long idJournal = journalBatchDao.saveJournal(journal);
            journalBatchDao.saveSituationAvantJournal(journal.getListSituationAvant(), idJournal);
            journalBatchDao.saveSituationApresJournal(journal.getListSituationApres(), idJournal);



        } else {

            programmeBatchDao.majStattutEligibilite(numProg, ERREUR_ELIGIBILITE);
            fichierBatchService.clearSelectedFichiers(numProg, CHARGEMENT_OK);

        }

        final ProgrammeDto payload = programmeViewDao.findByNumProg(numProg);
        simpMessagingTemplate.convertAndSend("/global-message/affectation", payload);

    }

    public FichierBatchService getFichierBatchService() {
        return fichierBatchService;
    }

    public void setFichierBatchService(FichierBatchService fichierBatchService) {
        this.fichierBatchService = fichierBatchService;
    }
}

