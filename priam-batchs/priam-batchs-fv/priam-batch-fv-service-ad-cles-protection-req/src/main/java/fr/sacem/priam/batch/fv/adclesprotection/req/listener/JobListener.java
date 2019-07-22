package fr.sacem.priam.batch.fv.adclesprotection.req.listener;

import fr.sacem.priam.batch.common.dao.FichierFVEnrichissementEnvoyeDao;
import fr.sacem.priam.batch.common.dao.FichierFVEnrichissementLogDao;
import fr.sacem.priam.batch.common.dao.FichierJdbcDao;

import static fr.sacem.priam.batch.common.fv.util.EtapeEnrichissementEnum.*;
import static fr.sacem.priam.batch.common.fv.util.EtapeEnrichissementLogEnum.*;

import fr.sacem.priam.batch.common.dao.LigneProgrammeFVDao;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;

public class JobListener extends JobExecutionListenerSupport {

    private static String SRV_AD_CLES_PROTECTION = "SRV_AD_CLES_PROTECTION";

    @Autowired
    FichierJdbcDao fichierJdbcDao;

    @Autowired
    LigneProgrammeFVDao ligneProgrammeFVDao;

    @Autowired
    FichierFVEnrichissementLogDao fichierFVEnrichissementLogDao;

    @Autowired
    FichierFVEnrichissementEnvoyeDao fichierFVEnrichissementEnvoyeDao;

    @Override
    public void beforeJob(final JobExecution jobExecution) {
        Long idFichier = jobExecution.getJobParameters().getLong("idFichier");
        fichierJdbcDao.majStatutEnrichissement(idFichier, TO_SRV_AD_CLES_PROTECTION.getCode());
        fichierFVEnrichissementEnvoyeDao.supprimerNbInfoEnvoye(idFichier, SRV_AD_CLES_PROTECTION);
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        Long idFichier = jobExecution.getJobParameters().getLong("idFichier");
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            fichierJdbcDao.majStatutEnrichissement(idFichier, IN_SRV_AD_CLES_PROTECTION.getCode());
            ligneProgrammeFVDao.majDateConsultSitu(idFichier);
            fichierFVEnrichissementLogDao.enregistrerLog(idFichier, LOG_IN_SRV_AD_CLES_PROTECTION.getLibelle());

            Long nbIde12Envoye = ligneProgrammeFVDao.countNbLignesAdclesByIdFichier(idFichier);

            fichierFVEnrichissementEnvoyeDao.enregistrerNbInfoEnvoye(idFichier, SRV_AD_CLES_PROTECTION, nbIde12Envoye);
        } else {
            fichierJdbcDao.majStatutEnrichissement(idFichier, ERROR_SRV_ENRICHISSEMENT.getCode());
            fichierFVEnrichissementLogDao.enregistrerLog(idFichier, LOG_ERROR_SRV_AD_CLES_PROTECTION.getLibelle());
        }
    }
}
