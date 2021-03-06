package fr.sacem.priam.batch.fv.adclesprotection.rep.listener;

import fr.sacem.priam.batch.common.dao.FichierFVEnrichissementLogDao;
import fr.sacem.priam.batch.common.dao.FichierJdbcDao;
import fr.sacem.priam.batch.common.fv.util.EnrichissementUtils;
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
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static fr.sacem.priam.batch.common.fv.util.EtapeEnrichissementEnum.DONE_SRV_AD_CLES_PROTECTION;
import static fr.sacem.priam.batch.common.fv.util.EtapeEnrichissementEnum.ERROR_SRV_ENRICHISSEMENT;
import static fr.sacem.priam.batch.common.fv.util.EtapeEnrichissementLogEnum.LOG_DONE_SRV_AD_CLES_PROTECTION;
import static fr.sacem.priam.batch.common.fv.util.EtapeEnrichissementLogEnum.LOG_ERROR_SRV_AD_CLES_PROTECTION;



/**
 * Created with IntelliJ IDEA.
 * @version $Id$
 * @since 1.0
 */
public class JobListener extends JobExecutionListenerSupport {
    private static final Logger LOG = LoggerFactory.getLogger(JobListener.class);

    private static final String FICHIER_CSV_EN_COURS = "fichierCSVEnCours";
    private static final String NOM_ORIGINAL_FICHIER_CSV = "nomFichierOriginal";
    private static final String REPERTOIRE_DE_DESTINATION = "output.archives";
    private static final String MESSAGE_FORMAT_FICHIER = "Le fichier ne peut être chargé car il n'a pas le bon format";
    private static final String MESSAGE_ERREUR_TECHNIQUE = "Erreur technique dans l'application priam" ;
    private static final String SRV_AD_CLES_PROTECTION = "SRV_AD_CLES_PROTECTION";

    @Autowired
    FichierJdbcDao fichierJdbcDao;

    @Autowired
    FichierFVEnrichissementLogDao fichierFVEnrichissementLogDao;

    @Autowired
    EnrichissementUtils enrichissementUtils;

    private ExecutionContext executionContext;

    private UtilFile utilFile;

    @Override
    public void beforeJob(final JobExecution jobExecution) {
        jobExecution.getExecutionContext().putString("SRV", SRV_AD_CLES_PROTECTION);
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        Object idFichier = jobExecution.getExecutionContext().get("idFichier");
        enrichissementUtils.setJobExecution(jobExecution);
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
            enrichissementUtils.checkAllInfosReceived();
        } else {
            Collection<StepExecution> stepExecutions = jobExecution.getStepExecutions();
            Iterator it = stepExecutions.iterator();
            while (it.hasNext()) {
                StepExecution myStepExecution = (StepExecution) it.next();
                executionContext = myStepExecution.getExecutionContext();

                if (executionContext != null && myStepExecution.getStepName().equalsIgnoreCase("stepRep")) {
                    JobParameter parameterFichierCSVEnCours = (JobParameter) executionContext.get(FICHIER_CSV_EN_COURS);
                    JobParameter parameterNomFichierOriginal = (JobParameter) executionContext.get(NOM_ORIGINAL_FICHIER_CSV);
                    JobParameter outputDirectory = jobExecution.getJobParameters().getParameters().get(REPERTOIRE_DE_DESTINATION);

                    utilFile.deplacerFichier(parameterFichierCSVEnCours, parameterNomFichierOriginal, outputDirectory);

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

                        } else if(errors == null || errors.isEmpty()) {
                            errors = new HashSet<>();
                            errors.add(MESSAGE_ERREUR_TECHNIQUE);
                        }
                    }
                    LOG.info(errors.toString());
                }
            }
            enrichissementUtils.majStatutEnrichissementFichier(true);
        }
    }

    public void setUtilFile(final UtilFile utilFile) {
        this.utilFile = utilFile;
    }
}
