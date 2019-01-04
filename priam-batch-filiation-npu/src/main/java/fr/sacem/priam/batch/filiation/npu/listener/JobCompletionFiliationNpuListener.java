package fr.sacem.priam.batch.filiation.npu.listener;

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

public class JobCompletionFiliationNpuListener extends JobExecutionListenerSupport {

    private static String FICHIER_CSV_EN_COURS = "fichierCSVEnCours";
    private static String NOM_ORIGINAL_FICHIER_CSV = "nomFichierOriginal";
    private static String REPERTOIRE_DE_DESTINATION = "output.archives";
    public static final String MESSAGE_FORMAT_FICHIER = "Le fichier ne peut être chargé car il n'a pas le bon format";
    private static String MESSAGE_ERREUR_TECHNIQUE = "Erreur technique dans l'application priam" ;
    private ExecutionContext executionContext;
    private UtilFile utilFile;
    private static final Logger LOG = LoggerFactory.getLogger(JobCompletionFiliationNpuListener.class);

    @Autowired
    public JobCompletionFiliationNpuListener() {utilFile = new UtilFile();}

    @Override
    public void afterJob(JobExecution jobExecution) {
        executionContext = jobExecution.getExecutionContext();
        if (jobExecution.getStatus() != BatchStatus.COMPLETED) {
            Set<String> errors = (Set<String>) executionContext.get("npu-errors");
            if(errors != null) {
                 LOG.info(String.valueOf(errors));
             }
        }

        if (executionContext != null) {
            JobParameter parameterFichierCSVEnCours = (JobParameter) executionContext.get(FICHIER_CSV_EN_COURS);
            JobParameter parameterNomFichierOriginal = (JobParameter) executionContext.get(NOM_ORIGINAL_FICHIER_CSV);
            JobParameter outputDirectory = jobExecution.getJobParameters().getParameters().get(REPERTOIRE_DE_DESTINATION);

            utilFile.deplacerFichier(parameterFichierCSVEnCours, parameterNomFichierOriginal, outputDirectory);

        }

    }

}
