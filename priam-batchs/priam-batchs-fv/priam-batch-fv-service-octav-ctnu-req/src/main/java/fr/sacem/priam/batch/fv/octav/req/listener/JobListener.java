package fr.sacem.priam.batch.fv.octav.req.listener;

import fr.sacem.priam.batch.common.dao.FichierFVEnrichissementEnvoyeDao;
import fr.sacem.priam.batch.common.dao.FichierFVEnrichissementLogDao;
import fr.sacem.priam.batch.common.dao.FichierJdbcDao;
import fr.sacem.priam.batch.common.dao.LigneProgrammeFVDao;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;

import static fr.sacem.priam.batch.common.fv.util.EtapeEnrichissementEnum.*;
import static fr.sacem.priam.batch.common.fv.util.EtapeEnrichissementLogEnum.LOG_ERROR_SRV_CTNU;
import static fr.sacem.priam.batch.common.fv.util.EtapeEnrichissementLogEnum.LOG_IN_SRV_CTNU;


/**
 * Created with IntelliJ IDEA.
 * @author Yegor Bugayenko (yegor@tpc2.com)
 * @version $Id$
 * @since 1.0
 */
public class JobListener extends JobExecutionListenerSupport {

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
        fichierJdbcDao.majStatutEnrichissement(idFichier, TO_SRV_OCTAV_CTNU.getCode());
        fichierFVEnrichissementEnvoyeDao.supprimerNbIde12Envoye(idFichier, "SRV_OCTAV_CTNU");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        Long idFichier = jobExecution.getJobParameters().getLong("idFichier");
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            fichierJdbcDao.majStatutEnrichissement(idFichier, IN_SRV_OCTAV_CTNU.getCode());
            ligneProgrammeFVDao.majDateConsultSitu(idFichier);
            fichierFVEnrichissementLogDao.enregistrerLog(idFichier, LOG_IN_SRV_CTNU.getLibelle());

            Long nbIde12Envoye = ligneProgrammeFVDao.countNbLignesOeuvreCtnuByIdFichier(idFichier);
            fichierFVEnrichissementEnvoyeDao.enregistrerNbIde12Envoye(idFichier, "SRV_OCTAV_CTNU", nbIde12Envoye);

        } else if (jobExecution.getStatus() == BatchStatus.FAILED) {
            fichierJdbcDao.majStatutEnrichissement(idFichier, ERROR_SRV_ENRICHISSEMENT.getCode());
            fichierFVEnrichissementLogDao.enregistrerLog(idFichier, LOG_ERROR_SRV_CTNU.getLibelle());
        }
    }
}
