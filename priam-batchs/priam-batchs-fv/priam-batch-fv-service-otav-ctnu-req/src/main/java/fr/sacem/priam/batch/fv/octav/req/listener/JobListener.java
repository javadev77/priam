package fr.sacem.priam.batch.fv.octav.req.listener;

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

    @Override
    public void beforeJob(final JobExecution jobExecution) {
        Long idFichier = jobExecution.getJobParameters().getLong("idFichier");
        fichierJdbcDao.majStatutEnrichissement(idFichier, "TO_SRV_OCTAV_CTNU");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        Long idFichier = jobExecution.getJobParameters().getLong("idFichier");
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            fichierJdbcDao.majStatutEnrichissement(idFichier, "IN_SRV_OCTAV_CTNU");
        } else if (jobExecution.getStatus() == BatchStatus.FAILED) {
            fichierJdbcDao.majStatutEnrichissement(idFichier, null);
        }
    }
}
