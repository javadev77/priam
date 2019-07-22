package fr.sacem.priam.batch.fv.ad.info.req.listener;

import fr.sacem.priam.batch.common.dao.FichierFVEnrichissementEnvoyeDao;
import fr.sacem.priam.batch.common.dao.FichierFVEnrichissementLogDao;
import fr.sacem.priam.batch.common.dao.FichierJdbcDao;
import fr.sacem.priam.batch.common.dao.LigneProgrammeFVDao;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;

import static fr.sacem.priam.batch.common.fv.util.EtapeEnrichissementEnum.*;
import static fr.sacem.priam.batch.common.fv.util.EtapeEnrichissementLogEnum.LOG_ERROR_SRV_AD_INFO;
import static fr.sacem.priam.batch.common.fv.util.EtapeEnrichissementLogEnum.LOG_IN_SRV_AD_INFO;

public class JobListener extends JobExecutionListenerSupport {

    private static String SRV_AD_INFO = "SRV_AD_INFO";

    @Autowired
    FichierJdbcDao fichierJdbcDao;

    @Autowired
    FichierFVEnrichissementLogDao fichierFVEnrichissementLogDao;

    @Autowired
    LigneProgrammeFVDao ligneProgrammeFVDao;

    @Autowired
    FichierFVEnrichissementEnvoyeDao fichierFVEnrichissementEnvoyeDao;

    @Override
    public void beforeJob(final JobExecution jobExecution) {
        Long idFichier = jobExecution.getJobParameters().getLong("idFichier");
        fichierJdbcDao.majStatutEnrichissement(idFichier, TO_SRV_AD_INFO.getCode());
        fichierFVEnrichissementEnvoyeDao.supprimerNbInfoEnvoye(idFichier, SRV_AD_INFO);
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        Long idFichier = jobExecution.getJobParameters().getLong("idFichier");
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            fichierJdbcDao.majStatutEnrichissement(idFichier, IN_SRV_AD_INFO.getCode());
            fichierFVEnrichissementLogDao.enregistrerLog(idFichier, LOG_IN_SRV_AD_INFO.getLibelle());

            Long nbNumpersEnvoye = ligneProgrammeFVDao.countNbLignesInfosADByIdFichier(idFichier);
            fichierFVEnrichissementEnvoyeDao.enregistrerNbInfoEnvoye(idFichier, SRV_AD_INFO, nbNumpersEnvoye);
        } else if (jobExecution.getStatus() == BatchStatus.FAILED) {
            fichierJdbcDao.majStatutEnrichissement(idFichier, ERROR_SRV_ENRICHISSEMENT.getCode());
            fichierFVEnrichissementLogDao.enregistrerLog(idFichier, LOG_ERROR_SRV_AD_INFO.getLibelle());
        }
    }

}
