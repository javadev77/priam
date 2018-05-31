package fr.sacem.priam.batch.enrichissement.cat.fra.listener;

import fr.sacem.priam.batch.common.service.importPenef.FichierBatchServiceImpl;
import fr.sacem.priam.batch.common.util.UtilFile;
import fr.sacem.priam.batch.enrichissement.cat.fra.dao.CatalogueCmsPenefRepository;
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

public class JobCompletionNotificationCatPenefFraListener extends JobExecutionListenerSupport {

    private static final Logger LOG = LoggerFactory.getLogger(JobCompletionNotificationCatPenefFraListener.class);
    private static String NOM_FICHIER_CSV_EN_COURS = "nomFichier";

    private FichierBatchServiceImpl fichierBatchService;

    private ExecutionContext executionContext;

    private UtilFile utilFile;

    @Autowired
    CatalogueCmsPenefRepository catalogueCmsPenefRepository;

    @Autowired
    public JobCompletionNotificationCatPenefFraListener(UtilFile utilFile) {
        this.utilFile = utilFile;
    }

    @Override
    public void afterJob(JobExecution jobExecution) {

        Collection<StepExecution> stepExecutions = jobExecution.getStepExecutions();
        Long fileID = null;
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            Iterator it = stepExecutions.iterator();
            JobParameter idFichier = null;
            while (it.hasNext()) {
                StepExecution myStepExecution = (StepExecution) it.next();
                executionContext = myStepExecution.getExecutionContext();
                if (executionContext != null) {
                    JobParameter parameterNomFichierCSV = (JobParameter) executionContext.get(NOM_FICHIER_CSV_EN_COURS);
                    if (parameterNomFichierCSV != null) {

                        fileID = executionContext.getLong("idFichier");
                        fichierBatchService.updateFichierById(fileID);
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
                fileID = executionContext.getLong("idFichier", 0L);

                fichierBatchService.rejeterFichier(fileID,   null);

            }
        }

        utilFile.deplacerFichierEtSuppressionFlag(jobExecution);
        catalogueCmsPenefRepository.supprimerDonneesCatPenefParIdFichier(fileID);

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
