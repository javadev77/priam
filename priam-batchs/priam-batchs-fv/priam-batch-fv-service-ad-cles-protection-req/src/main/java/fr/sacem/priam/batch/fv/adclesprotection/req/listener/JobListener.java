package fr.sacem.priam.batch.fv.adclesprotection.req.listener;

import fr.sacem.priam.batch.common.dao.FichierJdbcDao;
import fr.sacem.priam.batch.common.fv.util.EtapeEnrichissementEnum;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;

public class JobListener extends JobExecutionListenerSupport {

    @Autowired
    FichierJdbcDao fichierJdbcDao;

    @Override
    public void beforeJob(final JobExecution jobExecution) {
        Long idFichier = jobExecution.getJobParameters().getLong("idFichier");
        fichierJdbcDao.majStatutEnrichissement(idFichier, EtapeEnrichissementEnum.TO_SRV_AD_CLES_PERS.getCode());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        Long idFichier = jobExecution.getJobParameters().getLong("idFichier");
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            fichierJdbcDao.majStatutEnrichissement(idFichier, EtapeEnrichissementEnum.IN_SRV_AD_CLES_PERS.getCode());
        }
    }
}
