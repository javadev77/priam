package fr.sacem.priam.batch.affectation.listener;

import fr.sacem.priam.batch.common.dao.LigneProgrammeBatchDao;
import fr.sacem.priam.batch.common.dao.ProgrammeBatchDao;
import fr.sacem.priam.batch.common.dao.TraitementCmsDao;
import fr.sacem.priam.batch.common.domain.Programme;
import fr.sacem.priam.batch.common.service.importPenef.FichierBatchService;
import fr.sacem.priam.batch.common.util.UtilFile;
import fr.sacem.priam.common.util.FileUtils;
import fr.sacem.priam.model.dao.jpa.FichierDao;
import fr.sacem.priam.model.dao.jpa.JournalBatchDao;
import fr.sacem.priam.model.domain.Fichier;
import fr.sacem.priam.model.domain.Journal;
import fr.sacem.priam.model.domain.Status;
import fr.sacem.priam.model.util.JournalAffectationBuilder;
import fr.sacem.priam.model.util.TypeUtilisationPriam;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class JobCompletionNotificationAffectationCMSListener extends JobExecutionListenerSupport {
    public static final String LIGNE_PROGRAMME_ERRORS = "ligne-programme-errors";
    public static final String MESSAGE_FICHIER_CHARGE = " - Le fichier \"%s\" a bien été chargé";
    public static final String FORMAT_DATE = "dd/MM/yyyy HH:mm";

    public static final String ERREUR_ELIGIBILITE = "ERREUR_ELIGIBILITE";
    public static final String CHARGEMENT_OK = "CHARGEMENT_OK";

    private static String NOM_FICHIER_CSV_EN_COURS = "nomFichier";
    private static String FICHIER_ZIP_EN_COURS = "fichierZipEnCours";
    private static String NOM_ORIGINAL_FICHIER_ZIP = "nomFichierOriginal";
    private static String REPERTOIRE_DE_DESTINATION = "archives.catalog.octav";

    private ExecutionContext executionContext;

    @Autowired
    private FichierBatchService fichierBatchService;

    private UtilFile utilFile;
    private static final Logger LOG = LoggerFactory.getLogger(JobCompletionNotificationAffectationCMSListener.class);

    @Autowired
    ProgrammeBatchDao programmeBatchDao;

    @Autowired
    FichierDao fichierDao;

    @Autowired
    JournalBatchDao journalBatchDao;

    @Autowired
    TraitementCmsDao traitementCmsDao;

    @Autowired
    LigneProgrammeBatchDao ligneProgrammeBatchDao;

    /*@Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    ProgrammeViewDao programmeViewDao;*/

    @Autowired
    public JobCompletionNotificationAffectationCMSListener() {

        utilFile = new UtilFile();
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {
        //Creer un traitement CMS

        String numProg = jobExecution.getJobParameters().getString("numProg");
        Long nbOeuvres = ligneProgrammeBatchDao.countNbOeuvres(numProg);
        long traitementID = traitementCmsDao.createTraitement(numProg, nbOeuvres);


        jobExecution.getExecutionContext().put("ID_TMT_CMS", traitementID);
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        String numProg = jobExecution.getJobParameters().getString("numProg");

        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {

            String userId = jobExecution.getJobParameters().getString("userId");
            String listNomFichier = jobExecution.getJobParameters().getString("listNomFichier");

            Collection<StepExecution> stepExecutions = jobExecution.getStepExecutions();
            Iterator it = stepExecutions.iterator();
            while (it.hasNext()) {
                StepExecution myStepExecution = (StepExecution) it.next();
                executionContext = myStepExecution.getExecutionContext();
                if (executionContext != null) {
                    JobParameter parameterNomFichierCSV = (JobParameter) executionContext.get(NOM_FICHIER_CSV_EN_COURS);
                    JobParameter parameterFichierZipEnCours = (JobParameter) executionContext.get(FICHIER_ZIP_EN_COURS);
                    JobParameter parameterNomFichierOriginal = (JobParameter) executionContext.get(NOM_ORIGINAL_FICHIER_ZIP);
                    JobParameter outputDirectory = jobExecution.getJobParameters().getParameters().get(REPERTOIRE_DE_DESTINATION);

                    Set<String> errors = (Set<String>) executionContext.get(LIGNE_PROGRAMME_ERRORS);

                    if (errors == null || errors.isEmpty()) {
                        if (parameterNomFichierCSV != null) {
                            JobParameter idFichier = (JobParameter) executionContext.get("idFichier");
                            fichierBatchService.updateFichierById((Long) idFichier.getValue());

                            StringBuilder log = new StringBuilder();
                            log.append(LocalDateTime.now().format(DateTimeFormatter.ofPattern(FORMAT_DATE))).
                                    append(String.format(MESSAGE_FICHIER_CHARGE, parameterNomFichierCSV));
                            fichierBatchService.creerlog((Long) idFichier.getValue(), log.toString());
                        } else {
                            LOG.debug("Pas de ficiher CSV traité ");
                        }
                    } else {
                        JobParameter idFichier = (JobParameter) executionContext.get("idFichier");
                        fichierBatchService.rejeterFichier((Long) idFichier.getValue(), errors);
                    }

                    utilFile.deplacerFichier(parameterFichierZipEnCours, parameterNomFichierOriginal, outputDirectory);



                    programmeBatchDao.majStattutEligibilite(numProg, "FIN_ELIGIBILITE");
                    programmeBatchDao.majStattutProgramme(numProg, "AFFECTE");


                    Long idTraitementCMS = jobExecution.getExecutionContext().getLong("ID_TMT_CMS");
                    Long nbOeuvresRetenues = ligneProgrammeBatchDao.countNbOeuvres(numProg);

                    Programme programme = programmeBatchDao.findByNumProg(numProg);
                    String typeCms = null;
                    if(TypeUtilisationPriam.SONOFRA.getCode().equals(programme.getTypeUtilisation())) {
                        typeCms = FileUtils.CATALOGUE_TYPE_CMS_FR;
                    } else if(TypeUtilisationPriam.SONOANT.getCode().equals(programme.getTypeUtilisation())) {
                        typeCms = FileUtils.CATALOGUE_TYPE_CMS_ANF;
                    }

                    Long nbOeuvresCatalogue = ligneProgrammeBatchDao.countNbOeuvresCatalogue(typeCms);
                    Double sommePoints = ligneProgrammeBatchDao.countSommePoints(numProg);

                    traitementCmsDao.majTraitment(idTraitementCMS, nbOeuvresCatalogue, nbOeuvresRetenues, sommePoints, "FIN_ELIGIBILITE");
                } else {
                    LOG.debug("Pas de excution context pour le step en cours : " + myStepExecution.getStepName());
                }
            }


            List<Fichier> listFichierAffecte = fichierDao.findFichiersByIdProgramme(numProg, Status.AFFECTE);
            JournalAffectationBuilder journalAffectationBuilder = new JournalAffectationBuilder();
            Journal journal = journalAffectationBuilder.create(numProg, listNomFichier, listFichierAffecte, userId);
            Long idJournal = journalBatchDao.saveJournal(journal);
            journalBatchDao.saveSituationAvantJournal(journal.getListSituationAvant(), idJournal);
            journalBatchDao.saveSituationApresJournal(journal.getListSituationApres(), idJournal);

        } else {
            Collection<StepExecution> stepExecutions = jobExecution.getStepExecutions();
            Iterator it = stepExecutions.iterator();
            while (it.hasNext()) {
                StepExecution myStepExecution = (StepExecution) it.next();
                executionContext = myStepExecution.getExecutionContext();
                JobParameter parameterFichierZipEnCours = (JobParameter) executionContext.get(FICHIER_ZIP_EN_COURS);
                JobParameter parameterNomFichierOriginal = (JobParameter) executionContext.get(NOM_ORIGINAL_FICHIER_ZIP);
                JobParameter outputDirectory = jobExecution.getJobParameters().getParameters().get(REPERTOIRE_DE_DESTINATION);


                utilFile.deplacerFichier(parameterFichierZipEnCours, parameterNomFichierOriginal, outputDirectory);
            }


            programmeBatchDao.majStattutEligibilite(numProg, ERREUR_ELIGIBILITE);
            Long idTraitementCMS = jobExecution.getExecutionContext().getLong("ID_TMT_CMS");
            traitementCmsDao.majTraitment(idTraitementCMS, 0L, 0L, 0.0d, ERREUR_ELIGIBILITE);

            fichierBatchService.clearSelectedFichiers(numProg, CHARGEMENT_OK);


        }

        /*final ProgrammeDto payload = programmeViewDao.findByNumProg(numProg);
        simpMessagingTemplate.convertAndSend("/global-message/affectation", payload);*/

    }

    public FichierBatchService getFichierBatchService() {
        return fichierBatchService;
    }

    public void setFichierBatchService(FichierBatchService fichierBatchService) {
        this.fichierBatchService = fichierBatchService;
    }

}

