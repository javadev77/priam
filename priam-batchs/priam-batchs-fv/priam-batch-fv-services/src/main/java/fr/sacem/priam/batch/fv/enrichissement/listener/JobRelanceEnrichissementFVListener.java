package fr.sacem.priam.batch.fv.enrichissement.listener;

import fr.sacem.priam.batch.common.dao.LigneProgrammeBatchDao;
import fr.sacem.priam.services.FichierService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;

public class JobRelanceEnrichissementFVListener extends JobExecutionListenerSupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobRelanceEnrichissementFVListener.class);

    @Autowired
    LigneProgrammeBatchDao ligneProgrammeBatchDao;

    @Autowired
    FichierService fichierService;

    @Override
    public void beforeJob(JobExecution jobExecution) {
        String idFichier = jobExecution.getJobParameters().getString("idFichier");
        ligneProgrammeBatchDao.deleteDonneesLigneFV(Long.valueOf(idFichier));
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        String idFichier = jobExecution.getJobParameters().getString("idFichier");
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            fichierService.relancerEnrichissement(Long.valueOf(idFichier));
        }
    }
}
