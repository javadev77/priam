package fr.sacem.priam.batch.common.fv.listener;

import fr.sacem.priam.batch.common.dao.FichierJdbcDao;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created with IntelliJ IDEA.
 * @author Yegor Bugayenko (yegor@tpc2.com)
 * @version $Id$
 * @since 1.0
 */
public class JobListener extends JobExecutionListenerSupport {

    @Autowired
    FichierJdbcDao fichierJdbcDao;

    String etapeEnrichissement;

    @Override
    public void beforeJob(final JobExecution jobExecution) {
        Long idFichier = jobExecution.getJobParameters().getLong("idFichier");
        fichierJdbcDao.majStatutEnrichissement(idFichier, "TO_SRV_" + etapeEnrichissement);
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        Long idFichier = jobExecution.getJobParameters().getLong("idFichier");
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            fichierJdbcDao.majStatutEnrichissement(idFichier, "IN_SRV_" + etapeEnrichissement);
        }
    }

    public String getEtapeEnrichissement() {
        return etapeEnrichissement;
    }

    public void setEtapeEnrichissement(String etapeEnrichissement) {
        this.etapeEnrichissement = etapeEnrichissement;
    }
}
