package fr.sacem.priam.batch.fv.octav.info.oeuvre.rep.listener;

import fr.sacem.priam.batch.common.dao.FichierFVEnrichissementLogDao;
import fr.sacem.priam.batch.common.dao.FichierJdbcDao;
import fr.sacem.priam.batch.common.fv.util.EtapeEnrichissementEnum;
import static fr.sacem.priam.batch.common.fv.util.EtapeEnrichissementEnum.ERROR_SRV_ENRICHISSEMENT;
import static fr.sacem.priam.batch.common.fv.util.EtapeEnrichissementLogEnum.LOG_DONE_SRV_INFO_OEUVRE;
import static fr.sacem.priam.batch.common.fv.util.EtapeEnrichissementLogEnum.LOG_ERROR_SRV_INFO_OEUVRE;
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

/**
 * Created by embouazzar on 27/12/2018.
 */
public class JobListener extends JobExecutionListenerSupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobListener.class);

    private static String FICHIER_CSV_EN_COURS = "fichierCSVEnCours";
    private static String NOM_ORIGINAL_FICHIER_CSV = "nomFichierOriginal";
    private static String REPERTOIRE_DE_DESTINATION = "output.archives";
    public static final String MESSAGE_FORMAT_FICHIER = "Le fichier ne peut être chargé car il n'a pas le bon format";
    private static String MESSAGE_ERREUR_TECHNIQUE = "Erreur technique dans l'application priam" ;
    private ExecutionContext executionContext;

    private UtilFile utilFile;

    @Autowired
    private FichierJdbcDao fichierJdbcDao;

    @Autowired
    FichierFVEnrichissementLogDao fichierFVEnrichissementLogDao;

    @Autowired
    public JobListener(UtilFile utilFile) {
        this.utilFile = utilFile;
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        Long idFichier = (Long) jobExecution.getExecutionContext().get("idFichier");
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
//                    Long idFichier = (Long)jobExecution.getExecutionContext().get("idFichier");
                    utilFile.deplacerFichier(parameterFichierCSVEnCours, parameterNomFichierOriginal, outputDirectory);
                    fichierJdbcDao.majStatutEnrichissement(idFichier, EtapeEnrichissementEnum.DONE_SRV_INFO_OEUVRE.getCode());
                    fichierFVEnrichissementLogDao.enregistrerLog(idFichier, LOG_DONE_SRV_INFO_OEUVRE.getLibelle());
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
                    } else if(errors == null || errors.isEmpty()) {
                        errors.add(MESSAGE_ERREUR_TECHNIQUE);
                    }
                }
                LOGGER.info(errors.toString());
                utilFile.deplacerFichier(parameterFichierCSVEnCours, parameterNomFichierOriginal, outputDirectory);
            }

            fichierJdbcDao.majStatutEnrichissement(idFichier, ERROR_SRV_ENRICHISSEMENT.getCode());
            fichierFVEnrichissementLogDao.enregistrerLog(idFichier, LOG_ERROR_SRV_INFO_OEUVRE.getLibelle());

        }
    }
}
