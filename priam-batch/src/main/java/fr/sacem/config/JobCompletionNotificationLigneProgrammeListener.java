package fr.sacem.config;

import fr.sacem.service.FichierServiceImpl;
import fr.sacem.util.exception.PriamValidationException;
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

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import static fr.sacem.util.exception.PriamValidationException.ErrorType;


@Component
public class JobCompletionNotificationLigneProgrammeListener extends JobExecutionListenerSupport {
    public static final String LIGNE_PROGRAMME_ERRORS = "ligne-programme-errors";
    public static final String MESSAGE_ERREUR_TECHNIQUE = "Le chargement a été interrompu à cause d'un problème technique";
    public static final String MESSAGE_FICHIER_CHARGE = " - Le fichier \"%s\" a bien été chargé";
    public static final String FORMAT_DATE = "dd/MM/yyyy HH:mm";
    public static final String MESSAGE_FORMAT_FICHIER = "Le fichier ne peut être chargé car il n'a pas le bon format";
    private static String NOM_FICHIER_CSV_EN_COURS = "nomFichier";
    private static String FICHIER_ZIP_EN_COURS = "fichierZipEnCours";
    private static String NOM_ORIGINAL_FICHIER_ZIP = "nomFichierOriginal";
    private static String REPERTOIRE_DE_DESTINATION = "output.archives";
    private ExecutionContext executionContext;
    private FichierServiceImpl fichierService;
    private static final Logger LOG = LoggerFactory.getLogger(JobCompletionNotificationLigneProgrammeListener.class);

    @Autowired
    public JobCompletionNotificationLigneProgrammeListener() {

    }

    @Override
    public void afterJob(JobExecution jobExecution) {

        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {

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

                    if(errors == null || errors.isEmpty()) {
                        if (parameterNomFichierCSV != null) {
                            String nomFichier = (String) parameterNomFichierCSV.getValue();
                            JobParameter idFichier =(JobParameter) executionContext.get("idFichier");
                            fichierService.updateFichierById((Long)idFichier.getValue());

                            StringBuilder log = new StringBuilder();
                            log.append(LocalDateTime.now().format(DateTimeFormatter.ofPattern(FORMAT_DATE))).
                            append(String.format(MESSAGE_FICHIER_CHARGE, parameterNomFichierCSV));
                            fichierService.creerlog((Long)idFichier.getValue(), log.toString());
                        } else {
                            LOG.debug("Pas de ficiher CSV traité ");
                        }
                    } else {
                        JobParameter idFichier =(JobParameter) executionContext.get("idFichier");
                        fichierService.rejeterFichier((Long)idFichier.getValue(), errors);
                    }

                    deplacerFichier(parameterFichierZipEnCours, parameterNomFichierOriginal, outputDirectory);
                } else {
                    LOG.debug("Pas de excution context pour le step en cours : " + myStepExecution.getStepName());
                }
            }
        } else {
            Collection<StepExecution> stepExecutions = jobExecution.getStepExecutions();
            Iterator it = stepExecutions.iterator();
            while (it.hasNext()) {
                StepExecution myStepExecution = (StepExecution) it.next();
                executionContext = myStepExecution.getExecutionContext();
                JobParameter parameterFichierZipEnCours = (JobParameter) executionContext.get(FICHIER_ZIP_EN_COURS);
                JobParameter parameterNomFichierOriginal = (JobParameter) executionContext.get(NOM_ORIGINAL_FICHIER_ZIP);
                JobParameter outputDirectory = jobExecution.getJobParameters().getParameters().get(REPERTOIRE_DE_DESTINATION);

                Set<String> errors = (Set<String>) executionContext.get("ligne-programme-errors");
                if(myStepExecution.getStatus() == BatchStatus.STOPPED){
                    if( ! errors.isEmpty()) {
                        JobParameter idFichier = (JobParameter) executionContext.get("idFichier");
                        fichierService.rejeterFichier((Long) idFichier.getValue(), errors);
                    }
                } else if("FAILED".equals(myStepExecution.getExitStatus().getExitCode()))
                {
                    Throwable exception = myStepExecution.getFailureExceptions().iterator().next();
                    if(exception instanceof PriamValidationException) {
                        PriamValidationException.ErrorType errorType = ((PriamValidationException) exception).getErrorType();

                        if(ErrorType.FORMAT_FICHIER.equals(errorType)) {
                            errors.add(MESSAGE_FORMAT_FICHIER);
                        }
                        else if(ErrorType.FORMAT_ATTRIBUT.equals(errorType)) {
                            errors.add(exception.getMessage());
                        }

                    } else if(errors.isEmpty()) {
                        errors.add(MESSAGE_ERREUR_TECHNIQUE);
                    }

                    JobParameter jobParameter = (JobParameter) executionContext.get("idFichier");

                    Long idFichier;
                    if(jobParameter == null) {
                        idFichier = ((PriamValidationException)exception.getCause()).getIdFichier();
                        errors.clear();
                        errors.add(MESSAGE_FORMAT_FICHIER);
                    } else {
                        idFichier = (Long) jobParameter.getValue();
                    }

                    fichierService.rejeterFichier(idFichier, errors);

                }

                deplacerFichier(parameterFichierZipEnCours, parameterNomFichierOriginal, outputDirectory);
            }
        }
    }

    private void deplacerFichier(JobParameter parameterFichierZipEnCours, JobParameter parameterNomFichierOriginal, JobParameter outputDirectory) {
        if (parameterNomFichierOriginal != null && parameterFichierZipEnCours != null && outputDirectory != null) {
            String nomFichierTraiementEnCours = (String) parameterFichierZipEnCours.getValue();
            File fichierTraitementEnCours = new File(nomFichierTraiementEnCours);
            File fichierTraitementOk = new File((String) outputDirectory.getValue() + (String) parameterNomFichierOriginal.getValue());
            if (fichierTraitementOk.exists())
                fichierTraitementOk.delete();
            Boolean deplacementOK = fichierTraitementEnCours.renameTo(fichierTraitementOk);
            if (deplacementOK) {
                fichierTraitementEnCours.delete();
            } else {
                LOG.error("Déplacement de ficiher en cours de traitement KO ");
            }
        }
    }


    public void setFichierService(FichierServiceImpl fichierService) {
        this.fichierService = fichierService;
    }
}
