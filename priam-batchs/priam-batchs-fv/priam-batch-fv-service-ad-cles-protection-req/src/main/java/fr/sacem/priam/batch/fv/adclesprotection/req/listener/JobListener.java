package fr.sacem.priam.batch.fv.adclesprotection.req.listener;

import fr.sacem.priam.batch.common.dao.FichierJdbcDao;
import static fr.sacem.priam.batch.common.fv.util.EtapeEnrichissementEnum.DONE_SRV_INFO_OEUVRE;
import static fr.sacem.priam.batch.common.fv.util.EtapeEnrichissementEnum.IN_SRV_AD_CLES_PROTECTION;
import static fr.sacem.priam.batch.common.fv.util.EtapeEnrichissementEnum.TO_SRV_AD_CLES_PROTECTION;

import fr.sacem.priam.batch.common.dao.LigneProgrammeFVDao;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;

public class JobListener extends JobExecutionListenerSupport {

    @Autowired
    FichierJdbcDao fichierJdbcDao;

    @Autowired
    LigneProgrammeFVDao ligneProgrammeFVDao;

    @Override
    public void beforeJob(final JobExecution jobExecution) {
        Long idFichier = jobExecution.getJobParameters().getLong("idFichier");
        fichierJdbcDao.majStatutEnrichissement(idFichier, TO_SRV_AD_CLES_PROTECTION.getCode());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        Long idFichier = jobExecution.getJobParameters().getLong("idFichier");
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            fichierJdbcDao.majStatutEnrichissement(idFichier, IN_SRV_AD_CLES_PROTECTION.getCode());
            ligneProgrammeFVDao.majDateConsultSitu(idFichier);
        } else {
            fichierJdbcDao.majStatutEnrichissement(idFichier, DONE_SRV_INFO_OEUVRE.getCode());
        }
    }
}
