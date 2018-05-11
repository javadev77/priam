package fr.sacem.priam.batch.catcms.rdo.listener;

import fr.sacem.priam.batch.common.service.importPenef.FichierBatchServiceImpl;
import fr.sacem.priam.batch.common.util.UtilFile;
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
import java.util.Collection;
import java.util.Iterator;

public class JobCompletionNotificationCatcmsRdoListener extends JobExecutionListenerSupport {

    private static final Logger LOG = LoggerFactory.getLogger(JobCompletionNotificationCatcmsRdoListener.class);
    private static String NOM_FICHIER_CSV_EN_COURS = "nomFichier";

    private FichierBatchServiceImpl fichierBatchService;

    private ExecutionContext executionContext;

    private UtilFile utilFile;

    @Autowired
    public JobCompletionNotificationCatcmsRdoListener(UtilFile utilFile) {
        this.utilFile = utilFile;
    }

    @Override
    public void afterJob(JobExecution jobExecution) {

        Collection<StepExecution> stepExecutions = jobExecution.getStepExecutions();

        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            Iterator it = stepExecutions.iterator();
            JobParameter idFichier = null;
            while (it.hasNext()) {
                StepExecution myStepExecution = (StepExecution) it.next();
                executionContext = myStepExecution.getExecutionContext();
                if (executionContext != null) {
                    JobParameter parameterNomFichierCSV = (JobParameter) executionContext.get(NOM_FICHIER_CSV_EN_COURS);
                    if (parameterNomFichierCSV != null) {
                        idFichier = (JobParameter)executionContext.get("idFichier");
                        fichierBatchService.updateFichierById((Long)idFichier.getValue());
                    }
                } else {
                    LOG.debug("Pas de excution context pour le step en cours : " + myStepExecution.getStepName());
                }

            }
        } else {
            Iterator it = stepExecutions.iterator();
            JobParameter idFichier = null;

            while (it.hasNext()) {
                StepExecution myStepExecution = (StepExecution) it.next();
                executionContext = myStepExecution.getExecutionContext();
                idFichier = (JobParameter)executionContext.get("idFichier");

                fichierBatchService.rejeterFichier((Long)idFichier.getValue(),   null);

            }
        }

        utilFile.deplacerFichierEtSuppressionFlag(jobExecution);


    }

    public FichierBatchServiceImpl getFichierBatchService() {
        return fichierBatchService;
    }

    public void setFichierBatchService(FichierBatchServiceImpl fichierBatchService) {
        this.fichierBatchService = fichierBatchService;
    }

    public UtilFile getUtilFile() {
        return utilFile;
    }

    public void setUtilFile(UtilFile utilFile) {
        this.utilFile = utilFile;
    }
}
