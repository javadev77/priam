package fr.sacem.priam.batch.fv.octav.rep.listener;

import fr.sacem.priam.batch.common.dao.FichierJdbcDao;
import fr.sacem.priam.batch.common.util.UtilFile;
import fr.sacem.priam.batch.common.util.exception.PriamValidationException;
import java.util.Collection;
import java.util.Iterator;
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

import javax.validation.constraints.Null;

import static fr.sacem.priam.batch.common.fv.util.EtapeEnrichissementEnum.DONE_SRV_INFO_OEUVRE;

/**
 * Created with IntelliJ IDEA.
 * @version $Id$
 * @since 1.0
 */
public class JobListener extends JobExecutionListenerSupport {
    private static final Logger LOG = LoggerFactory.getLogger(JobListener.class);

    private static String FICHIER_CSV_EN_COURS = "fichierCSVEnCours";
    private static String NOM_ORIGINAL_FICHIER_CSV = "nomFichierOriginal";
    private static String REPERTOIRE_DE_DESTINATION = "output.archives";
    private static final String MESSAGE_FORMAT_FICHIER = "Le fichier ne peut être chargé car il n'a pas le bon format";
    private static String MESSAGE_ERREUR_TECHNIQUE = "Erreur technique dans l'application priam" ;


    @Autowired
    FichierJdbcDao fichierJdbcDao;

    private ExecutionContext executionContext;

    private UtilFile utilFile;

    @Override
    public void beforeJob(final JobExecution jobExecution) {
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        Object idFichier = jobExecution.getExecutionContext().get("idFichier");

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

                Set<String> errors = (Set<String>) executionContext.get("errors");
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
                fichierJdbcDao.majStatutEnrichissement((Long) idFichier, null);
            }
        }

        if (idFichier != null && jobExecution.getStatus() == BatchStatus.COMPLETED) {
            fichierJdbcDao.majStatutEnrichissement((Long) idFichier, "DONE_SRV_OCTAV_CTNU");
        }
    }

    public void setUtilFile(final UtilFile utilFile) {
        this.utilFile = utilFile;
    }
}
