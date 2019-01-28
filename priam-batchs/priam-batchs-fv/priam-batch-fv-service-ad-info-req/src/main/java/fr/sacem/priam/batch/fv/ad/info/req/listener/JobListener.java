package fr.sacem.priam.batch.fv.ad.info.req.listener;

import fr.sacem.priam.batch.common.dao.FichierJdbcDao;
import fr.sacem.priam.batch.common.fv.util.EtapeEnrichissementEnum;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;

import static fr.sacem.priam.batch.common.fv.util.EtapeEnrichissementEnum.*;

public class JobListener extends JobExecutionListenerSupport {

    @Autowired
    FichierJdbcDao fichierJdbcDao;

    @Override
    public void beforeJob(final JobExecution jobExecution) {
        Long idFichier = jobExecution.getJobParameters().getLong("idFichier");
        fichierJdbcDao.majStatutEnrichissement(idFichier, TO_SRV_AD_INFO.getCode());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        Long idFichier = jobExecution.getJobParameters().getLong("idFichier");
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            fichierJdbcDao.majStatutEnrichissement(idFichier, IN_SRV_AD_INFO.getCode());
        } else if (jobExecution.getStatus() == BatchStatus.FAILED) {
            fichierJdbcDao.majStatutEnrichissement(idFichier, DONE_SRV_AD_CLES_PERS.getCode());
        }
    }

}
