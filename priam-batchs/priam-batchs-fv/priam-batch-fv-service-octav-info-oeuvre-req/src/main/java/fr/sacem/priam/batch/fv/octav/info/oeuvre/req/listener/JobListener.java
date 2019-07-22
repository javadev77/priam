package fr.sacem.priam.batch.fv.octav.info.oeuvre.req.listener;

import fr.sacem.priam.batch.common.dao.FichierFVEnrichissementEnvoyeDao;
import fr.sacem.priam.batch.common.dao.FichierFVEnrichissementLogDao;
import fr.sacem.priam.batch.common.dao.FichierJdbcDao;
import fr.sacem.priam.batch.common.dao.LigneProgrammeFVDao;
import fr.sacem.priam.batch.common.fv.util.EtapeEnrichissementEnum;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;

import static fr.sacem.priam.batch.common.fv.util.EtapeEnrichissementEnum.ERROR_SRV_ENRICHISSEMENT;
import static fr.sacem.priam.batch.common.fv.util.EtapeEnrichissementEnum.IN_SRV_INFO_OEUVRE;
import static fr.sacem.priam.batch.common.fv.util.EtapeEnrichissementLogEnum.LOG_ERROR_SRV_INFO_OEUVRE;
import static fr.sacem.priam.batch.common.fv.util.EtapeEnrichissementLogEnum.LOG_IN_SRV_INFO_OEUVRE;

public class JobListener extends JobExecutionListenerSupport {

    private static String SRV_INFO_OEUVRE = "SRV_INFO_OEUVRE";

    @Autowired
    FichierJdbcDao fichierJdbcDao;

    @Autowired
    FichierFVEnrichissementLogDao fichierFVEnrichissementLogDao;

    @Autowired
    LigneProgrammeFVDao ligneProgrammeFVDao;

    @Autowired
    FichierFVEnrichissementEnvoyeDao fichierFVEnrichissementEnvoyeDao;

    /*String etapeEnrichissement;*/

    @Override
    public void beforeJob(final JobExecution jobExecution) {
        Long idFichier = jobExecution.getJobParameters().getLong("idFichier");
        fichierJdbcDao.majStatutEnrichissement(idFichier, EtapeEnrichissementEnum.TO_SRV_INFO_OEUVRE.getCode());
        fichierFVEnrichissementEnvoyeDao.supprimerNbInfoEnvoye(idFichier, SRV_INFO_OEUVRE);
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        Long idFichier = jobExecution.getJobParameters().getLong("idFichier");
        if (idFichier !=null && jobExecution.getStatus() == BatchStatus.COMPLETED) {
            fichierJdbcDao.majStatutEnrichissement(idFichier, IN_SRV_INFO_OEUVRE.getCode());
            fichierFVEnrichissementLogDao.enregistrerLog(idFichier, LOG_IN_SRV_INFO_OEUVRE.getLibelle());

            Long nbIde12Envoye = ligneProgrammeFVDao.countNbLignesInfoOeuvreReqByIdFichier(idFichier);
            fichierFVEnrichissementEnvoyeDao.enregistrerNbInfoEnvoye(idFichier, SRV_INFO_OEUVRE, nbIde12Envoye);
        } else {
            fichierJdbcDao.majStatutEnrichissement(idFichier, ERROR_SRV_ENRICHISSEMENT.getCode());
            fichierFVEnrichissementLogDao.enregistrerLog(idFichier, LOG_ERROR_SRV_INFO_OEUVRE.getLibelle());
        }
    }

    /*public String getEtapeEnrichissement() {
        return etapeEnrichissement;
    }

    public void setEtapeEnrichissement(String etapeEnrichissement) {
        this.etapeEnrichissement = etapeEnrichissement;
    }*/
}
