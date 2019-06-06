package fr.sacem.priam.batch.fv.repartition.listener;

import fr.sacem.priam.batch.common.dao.ProgrammeBatchDao;
import fr.sacem.priam.common.util.SftpUtil;
import static fr.sacem.priam.common.util.SftpUtil.SftpServer.FELIX;
import fr.sacem.priam.model.dao.jpa.FichierFelixDao;
import fr.sacem.priam.model.dao.jpa.FichierFelixLogDao;
import fr.sacem.priam.model.dao.jpa.fv.LignePreprepFVJdbcDao;
import fr.sacem.priam.model.domain.FichierFelix;
import fr.sacem.priam.model.domain.FichierFelixLog;
import fr.sacem.priam.model.domain.StatutFichierFelix;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JobCompletionRepartitionFVListener extends JobExecutionListenerSupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobCompletionRepartitionFVListener.class);
    public static final String MIS_EN_REPART = "MIS_EN_REPART";
    private static String NOM_PREPREP_CSV = "REQ_FILE_NAME";
    private static String NOM_STEP_GENERATION = "stepGeneration";
    public static final String LIGNE_PREPREP_ERRORS = "ligne-preprep-errors";
    public static final String MODE_REPART_BLANC = "REPART_BLANC";
    public static final String MODE_MISE_EN_REPART = "MISE_EN_REPART";


    private ExecutionContext executionContext;

    @Autowired
    ProgrammeBatchDao programmeBatchDao;

    @Autowired
    FichierFelixDao fichierFelixDao;

    @Autowired
    FichierFelixLogDao fichierFelixLogDao;

    @Autowired
    LignePreprepFVJdbcDao lignePreprepFVJdbcDao;

    @Override
    public void beforeJob(JobExecution jobExecution) {

        String numProg = jobExecution.getJobParameters().getString("numProg");
        String modeRepartition = jobExecution.getJobParameters().getString("modeRepartition");


        if(MODE_MISE_EN_REPART.equals(modeRepartition)) {
            lignePreprepFVJdbcDao.deleteByNumprog(numProg);
            FichierFelix ff = fichierFelixDao.findByNumprog(numProg);
            if(ff != null) {
                ff.setStatut(StatutFichierFelix.EN_COURS);
                fichierFelixDao.save(ff);
                fichierFelixDao.flush();
            }
        }

    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        Collection<StepExecution> stepExecutions = jobExecution.getStepExecutions();
        String numProg = jobExecution.getJobParameters().getString("numProg");
        String modeRepartition = jobExecution.getJobParameters().getString("modeRepartition");
        String path = jobExecution.getJobParameters().getString("priam.felix.preprep.dir");
        FichierFelix ff = fichierFelixDao.findByNumprog(numProg);


        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            Iterator it = stepExecutions.iterator();
            while (it.hasNext()) {
                StepExecution myStepExecution = (StepExecution) it.next();
                if(NOM_STEP_GENERATION.equals(myStepExecution.getStepName())){
                    executionContext = myStepExecution.getExecutionContext();
                    if (executionContext != null) {
                        ff.setNomFichier(executionContext.getString(NOM_PREPREP_CSV));
                        List<String> errors = (ArrayList<String>) executionContext.get(LIGNE_PREPREP_ERRORS);
                        if (errors != null && !errors.isEmpty()) {
                            for (String error : errors) {
                                saveFelixLog(ff, error);
                            }
                        }
                        ff.setStatut(errors == null || errors.isEmpty() ? StatutFichierFelix.GENERE : StatutFichierFelix.EN_ERREUR);
                        if(MODE_REPART_BLANC.equals(modeRepartition)){
                            lignePreprepFVJdbcDao.deleteByNumprog(numProg);
                        } else if(MODE_MISE_EN_REPART.equals(modeRepartition)) {
                            try {
                                File tempFile = new File(path + File.separator + ff.getNomFichier());
                                LOGGER.debug("==> Debut Envoi du fichier à FELIX = " + ff.getNomFichier());
                                ff.setStatut(StatutFichierFelix.EN_COURS_ENVOI);
                                fichierFelixDao.saveAndFlush(ff);

                                SftpUtil.uploadFile(FELIX, tempFile, ff.getNomFichier());
                                LOGGER.debug("<=== Fin Envoi du fichier à FELIX = " + ff.getNomFichier());

                                programmeBatchDao.majStattutProgramme(numProg, MIS_EN_REPART);
                                ff.setStatut(StatutFichierFelix.ENVOYE);
                                if (tempFile.exists()) {
                                    FileUtils.forceDelete(tempFile);
                                }

                            } catch (Exception e) {
                                LOGGER.error(String.format("Erreur lors de la mise en repartition du programme %s !!", numProg), e);
                                ff.setStatut(StatutFichierFelix.EN_ERREUR);
                                String message = String.format("Probleme lors de l'envoi du fichier PREPREP  %s à FELIX", ff.getNomFichier());
                                saveFelixLog(ff, message);
                            }
                        }
                    }
                }
            }
        } else {
            lignePreprepFVJdbcDao.deleteByNumprog(numProg);
            ff.setStatut(StatutFichierFelix.EN_ERREUR);
        }
        fichierFelixDao.saveAndFlush(ff);
    }

    private void saveFelixLog(FichierFelix ff, String error) {
        FichierFelixLog felixLog = new FichierFelixLog();
        felixLog.setLog(error);
        felixLog.setDateCreation(new Date());
        fichierFelixLogDao.save(felixLog);
        ff.getLogs().add(felixLog);
    }

}
