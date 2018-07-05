package fr.sacem.priam.batch.affectation.cp.listener;

import fr.sacem.dao.LigneProgrammeBatchDao;
import fr.sacem.dao.ProgrammeBatchDao;
import fr.sacem.dao.TraitementCmsDao;
import fr.sacem.priam.batch.affectation.cp.dao.JournalBatchDao;
import fr.sacem.priam.common.TypeLog;
import fr.sacem.priam.model.dao.jpa.FichierDao;
import fr.sacem.priam.model.dao.jpa.JournalDao;
import fr.sacem.priam.model.domain.*;
import fr.sacem.priam.model.journal.JournalBuilder;
import fr.sacem.service.importPenef.FichierBatchService;
import fr.sacem.util.UtilFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


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

    //@Autowired
    //FichierDao fichierDao;

    //@Autowired
    //JournalDao journalDao;

    @Autowired
    JournalBatchDao journalBatchDao;

    @Autowired
    TraitementCmsDao traitementCmsDao;

    @Autowired
    LigneProgrammeBatchDao ligneProgrammeBatchDao;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    @Autowired
    public JobCompletionNotificationAffectationCPListener() {

        utilFile = new UtilFile();
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {
        //Creer un traitement CMS

        String numProg = jobExecution.getJobParameters().getString("numProg");
        long traitementID = traitementCmsDao.createTraitement(numProg, 0L);

        jobExecution.getExecutionContext().put("ID_TMT_CMS", traitementID);
    }

    @Override
    public void afterJob(JobExecution jobExecution) {

        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {

            String numProg = jobExecution.getJobParameters().getString("numProg");
            String userId = jobExecution.getJobParameters().getString("userId");
            String listNomFichier = jobExecution.getJobParameters().getString("listNomFichier");


            programmeBatchDao.majStattutEligibilite(numProg, "FIN_ELIGIBILITE");
            programmeBatchDao.majStattutProgramme(numProg, "AFFECTE");

            Long idTraitementCMS = jobExecution.getExecutionContext().getLong("ID_TMT_CMS");

/*
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
            journalBatchDao.saveSituationApresJournal(journal.getListSituationApres(), idJournal);*/
        } else {

            String numProg = jobExecution.getJobParameters().getString("numProg");
            programmeBatchDao.majStattutEligibilite(numProg, ERREUR_ELIGIBILITE);
            Long idTraitementCMS = jobExecution.getExecutionContext().getLong("ID_TMT_CMS");
            traitementCmsDao.majTraitment(idTraitementCMS, 0L, 0L, 0.0d, ERREUR_ELIGIBILITE);

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

