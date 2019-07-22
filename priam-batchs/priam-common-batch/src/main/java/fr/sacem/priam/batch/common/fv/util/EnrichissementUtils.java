package fr.sacem.priam.batch.common.fv.util;

import fr.sacem.priam.batch.common.dao.FichierFVEnrichissementEnvoyeDao;
import fr.sacem.priam.batch.common.dao.FichierFVEnrichissementLogDao;
import fr.sacem.priam.batch.common.dao.FichierJdbcDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import static fr.sacem.priam.batch.common.fv.util.EtapeEnrichissementEnum.ERROR_SRV_ENRICHISSEMENT;

@Component
public class EnrichissementUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(EnrichissementUtils.class);

    public final static String NB_INFOS_RECUS = "NB_INFOS_RECUS";
    private static String ID_FICHIER = "idFichier";
    private static String SERVICE = "SRV";
    private static String PATTERN_LOG_ERROR = "LOG_ERROR_";
    private static String PATTERN_LOG_DONE = "LOG_DONE_";
    private static String PATTERN_DONE = "DONE_";

    @Autowired
    FichierFVEnrichissementEnvoyeDao fichierFVEnrichissementEnvoyeDao;

    @Autowired
    FichierJdbcDao fichierJdbcDao;

    @Autowired
    FichierFVEnrichissementLogDao fichierFVEnrichissementLogDao;

    private JobExecution jobExecution;

    private Long idFichier;
    private String service;


    public void checkAllInfosReceived() {
        Long nbInfosRecus = jobExecution.getExecutionContext().getLong(NB_INFOS_RECUS);
        try {
            Long nbInfosEnvoye = fichierFVEnrichissementEnvoyeDao.getNbInfosEnvoye(idFichier, service);
            if ((nbInfosEnvoye != null && nbInfosRecus != null) && nbInfosEnvoye.equals(nbInfosRecus)) {
                majStatutEnrichissementFichier(false);
            } else {
                majStatutEnrichissementFichier(true);
                LOGGER.info("Le nombre d'information envoyé ne correspond pas au nombre d'information reçu par le service " + service);
            }
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error("Erreur : la REQ du service " + service + " n'a pas été envoyée.");
            majStatutEnrichissementFichier(true);
        } finally {
            fichierFVEnrichissementEnvoyeDao.supprimerNbInfoEnvoye(idFichier, service);
        }
    }

    public void majStatutEnrichissementFichier(boolean isEnrichissementError) {
        if (isEnrichissementError) {
            fichierJdbcDao.majStatutEnrichissement(idFichier, ERROR_SRV_ENRICHISSEMENT.getCode());
            fichierFVEnrichissementLogDao.enregistrerLog(idFichier, EtapeEnrichissementLogEnum.getValue(PATTERN_LOG_ERROR + service).getLibelle());
            fichierFVEnrichissementEnvoyeDao.supprimerNbInfoEnvoye(idFichier, service);
        } else {
            fichierJdbcDao.majStatutEnrichissement(idFichier, PATTERN_DONE + service);
            fichierFVEnrichissementLogDao.enregistrerLog(idFichier, EtapeEnrichissementLogEnum.getValue(PATTERN_LOG_DONE + service).getLibelle());
        }
    }

    public void setJobExecution(JobExecution jobExecution) {
        this.jobExecution = jobExecution;
        this.idFichier = jobExecution.getExecutionContext().getLong(ID_FICHIER);
        this.service = jobExecution.getExecutionContext().getString(SERVICE);
    }

}
