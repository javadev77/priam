package fr.sacem.priam.batch.fv.octav.info.oeuvre.req.listener;

import fr.sacem.priam.batch.common.dao.FichierJdbcDao;
import fr.sacem.priam.batch.common.fv.util.EtapeEnrichissementEnum;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;

public class JobListener extends JobExecutionListenerSupport {

    @Autowired
    FichierJdbcDao fichierJdbcDao;

    String etapeEnrichissement;

    @Override
    public void beforeJob(final JobExecution jobExecution) {
        Long idFichier = jobExecution.getJobParameters().getLong("idFichier");
        fichierJdbcDao.majStatutEnrichissement(idFichier, EtapeEnrichissementEnum.TO_SRV_INFO_OEUVRE.getCode());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        Long idFichier = jobExecution.getJobParameters().getLong("idFichier");
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            fichierJdbcDao.majStatutEnrichissement(idFichier, EtapeEnrichissementEnum.IN_SRV_INFO_OEUVRE.getCode());
        }
    }

    public String getEtapeEnrichissement() {
        return etapeEnrichissement;
    }

    public void setEtapeEnrichissement(String etapeEnrichissement) {
        this.etapeEnrichissement = etapeEnrichissement;
    }
}
