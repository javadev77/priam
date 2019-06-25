package fr.sacem.priam.batch.participants.rep.listener;

import fr.sacem.priam.batch.common.util.UtilFile;
import fr.sacem.priam.batch.common.util.exception.PriamValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class JobCompletionParticipantListener extends JobExecutionListenerSupport {


    public static final String MESSAGE_FICHIER_CHARGE = " - Le fichier \"%s\" a bien été chargé";
    public static final String FORMAT_DATE = "dd/MM/yyyy HH:mm";
    private static String NOM_FICHIER_CSV_EN_COURS = "nomFichier";
    private static String FICHIER_CSV_EN_COURS = "fichierCSVEnCours";
    private static String NOM_ORIGINAL_FICHIER_CSV = "nomFichierOriginal";
    private static String REPERTOIRE_DE_DESTINATION = "output.archives";
    public static final String MESSAGE_FORMAT_FICHIER = "Le fichier ne peut être chargé car il n'a pas le bon format";
    private static String MESSAGE_ERREUR_TECHNIQUE = "Erreur technique dans l'application priam" ;
    private ExecutionContext executionContext;

    private static final Logger LOG = LoggerFactory.getLogger(JobCompletionParticipantListener.class);


    private UtilFile utilFile;

    @Autowired
    public JobCompletionParticipantListener(UtilFile utilFile) {
        this.utilFile = utilFile;
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
                    JobParameter parameterFichierCSVEnCours = (JobParameter) executionContext.get(FICHIER_CSV_EN_COURS);
                    JobParameter parameterNomFichierOriginal = (JobParameter) executionContext.get(NOM_ORIGINAL_FICHIER_CSV);
                    JobParameter outputDirectory = jobExecution.getJobParameters().getParameters().get(REPERTOIRE_DE_DESTINATION);

                    utilFile.deplacerFichier(parameterFichierCSVEnCours, parameterNomFichierOriginal, outputDirectory);

                }
            }

        } else {
            Collection<StepExecution> stepExecutions = jobExecution.getStepExecutions();
            Iterator it = stepExecutions.iterator();
            while (it.hasNext()) {
                StepExecution myStepExecution = (StepExecution) it.next();
                executionContext = myStepExecution.getExecutionContext();

                JobParameter parameterFichierCSVEnCours = (JobParameter) executionContext.get(FICHIER_CSV_EN_COURS);
                JobParameter parameterNomFichierOriginal = (JobParameter) executionContext.get(NOM_ORIGINAL_FICHIER_CSV);
                JobParameter outputDirectory = jobExecution.getJobParameters().getParameters().get(REPERTOIRE_DE_DESTINATION);

                Set<String> errors = (Set<String>) executionContext.get("repartition-errors");
                if(myStepExecution.getStatus() == BatchStatus.STOPPED || "FAILED".equals(myStepExecution.getExitStatus().getExitCode())){
                    Throwable exception = myStepExecution.getFailureExceptions().iterator().next();
                    if(exception instanceof PriamValidationException) {
                        PriamValidationException.ErrorType errorType = ((PriamValidationException) exception).getErrorType();

                        if(PriamValidationException.ErrorType.FORMAT_FICHIER.equals(errorType)) {
                            errors.add(MESSAGE_FORMAT_FICHIER);
                        }
                        else if(PriamValidationException.ErrorType.FORMAT_ATTRIBUT.equals(errorType)) {
                            errors.add(exception.getMessage());
                        }

                    } else if(errors.isEmpty()) {
                        errors.add(MESSAGE_ERREUR_TECHNIQUE);
                    }
                }
                LOG.info(errors.toString());
                utilFile.deplacerFichier(parameterFichierCSVEnCours, parameterNomFichierOriginal, outputDirectory);



            }
        }

    }

}
