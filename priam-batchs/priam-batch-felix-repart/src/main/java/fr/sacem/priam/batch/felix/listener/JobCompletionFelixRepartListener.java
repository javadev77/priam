package fr.sacem.priam.batch.felix.listener;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import fr.sacem.priam.batch.felix.journal.TypeLog;
import fr.sacem.priam.common.constants.EnvConstants;
import fr.sacem.priam.common.util.SftpUtil;
import static fr.sacem.priam.common.util.SftpUtil.SftpServer.FELIX;
import fr.sacem.priam.model.dao.jpa.FichierFelixDao;
import fr.sacem.priam.model.dao.jpa.FichierFelixLogDao;
import fr.sacem.priam.model.dao.jpa.JournalBatchDao;
import fr.sacem.priam.model.dao.jpa.fv.LignePreprepFVJdbcDao;
import fr.sacem.priam.model.domain.FichierFelix;
import fr.sacem.priam.model.domain.FichierFelixLog;
import fr.sacem.priam.model.domain.Journal;
import fr.sacem.priam.model.domain.SituationApres;
import fr.sacem.priam.model.domain.SituationAvant;
import fr.sacem.priam.model.domain.StatutFichierFelix;
import fr.sacem.priam.model.domain.StatutProgramme;
import fr.sacem.priam.model.domain.dto.ProgrammeDto;
import fr.sacem.priam.model.journal.JournalBuilder;
import fr.sacem.priam.model.util.FamillePriam;
import fr.sacem.priam.security.model.UserDTO;
import fr.sacem.priam.services.ProgrammeService;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import javax.sql.DataSource;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


@Component
public class JobCompletionFelixRepartListener extends JobExecutionListenerSupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobCompletionFelixRepartListener.class);

    @Autowired
    @Qualifier(value = "configAdmap")
    Map<String, String> configAdmap;

    @Autowired
    private ProgrammeService programmeService;

    @Autowired
    FichierFelixDao fichierFelixDao;

    @Autowired
    private FichierFelixLogDao fichierFelixLogDao;

    @Autowired
    LignePreprepFVJdbcDao lignePreprepFVJdbcDao;


    @Autowired
    DataSource dataSource;


    private static final String PRG_VALIDE = "Validé";
    private static final String PRG_MIS_EN_REPART = "Mis en répartition";


    private static final String MODE_REPART_BLANC = "REPART_BLANC";
    private static final String MODE_MISE_EN_REPART = "MISE_EN_REPART";

    @Autowired
    private JournalBatchDao journalBatchDao;

    @Override
    public void beforeJob(JobExecution jobExecution) {
        jobExecution.getExecutionContext().put("IsError", "true");
        String numProg = jobExecution.getJobParameters().getString("numProg");
        String modeRepartition = jobExecution.getJobParameters().getString("modeRepartition");

        jobExecution.getExecutionContext().put("FELIX_REPART_FILENAME", "");

        String famille = jobExecution.getJobParameters().getString("famille");
        if(FamillePriam.VALORISATION.getCode().equals(famille)) {
            lignePreprepFVJdbcDao.deleteByNumprog(numProg);
        } else {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            jdbcTemplate.update("DELETE FROM PRIAM_LIGNE_PREPREP WHERE numProg=?", numProg);
        }
        if(MODE_MISE_EN_REPART.equals(modeRepartition)) {
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
        String numProg = jobExecution.getJobParameters().getString("numProg");
        String modeRepartition = jobExecution.getJobParameters().getString("modeRepartition");
        FichierFelix ff = fichierFelixDao.findByNumprog(numProg);

        if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
            String felixRepartFilename = jobExecution.getExecutionContext().getString("FELIX_REPART_FILENAME");
            String isError = jobExecution.getExecutionContext().getString("IsError");
            ff.setNomFichier(felixRepartFilename);
            boolean isErrorValidation = Boolean.valueOf(isError);
            ff.setStatut(isErrorValidation ? StatutFichierFelix.EN_ERREUR : StatutFichierFelix.GENERE);
            fichierFelixDao.saveAndFlush(ff);

            if(MODE_REPART_BLANC.equals(modeRepartition)) {
                lignePreprepFVJdbcDao.deleteByNumprog(numProg);
            } else if(!isErrorValidation) {
                // Envoi du fichier via FTP
                File tempFile = new File(getPreprepDir() + File.separator + felixRepartFilename);
                ff.setStatut(StatutFichierFelix.EN_COURS_ENVOI);
                fichierFelixDao.saveAndFlush(ff);
                LOGGER.info("==> Debut Envoi du fichier à FELIX = " + felixRepartFilename);

                try {
                    SftpUtil.uploadFile(FELIX, tempFile, felixRepartFilename);
                    LOGGER.info("<=== Fin Envoi du fichier à FELIX = " + felixRepartFilename);
                } catch (JSchException | SftpException | IOException e) {
                    processErrorSendFile(numProg, e);
                }

                ProgrammeDto programmeDto = new ProgrammeDto();
                programmeDto.setNumProg(numProg);
                programmeDto.setStatut(StatutProgramme.MIS_EN_REPART);
                String userId = jobExecution.getJobParameters().getString("userId");
                programmeDto.setUsermaj(userId);
                UserDTO userDTO = new UserDTO();

                programmeService.majStatutToMisEnRepartition(programmeDto, userDTO);

                ff = fichierFelixDao.findByNumprog(numProg);
                ff.setNomFichier(felixRepartFilename);
                ff.setStatut(StatutFichierFelix.ENVOYE);
                fichierFelixDao.saveAndFlush(ff);

                if (tempFile.exists()) {
                    try {
                        FileUtils.forceDelete(tempFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                journaliserMiseEnRepartition(numProg, userId);

            }

        }

    }

    private void journaliserMiseEnRepartition(final String numProg, final String userId) {
        JournalBuilder journalBuilder = new JournalBuilder(numProg,null, userId);
        final Journal journal = journalBuilder.addEvenement(TypeLog.MISE_EN_REPART.getEvenement()).build();
        SituationAvant situationAvant = new SituationAvant();
        situationAvant.setSituation(PRG_VALIDE);
        journal.getListSituationAvant().add(situationAvant);
        final SituationApres situationApres = new SituationApres();
        situationApres.setSituation(PRG_MIS_EN_REPART);
        journal.getListSituationApres().add(situationApres);

        long idJournal = journalBatchDao.saveJournal(journal);
        journalBatchDao.saveSituationAvantJournal(journal.getListSituationAvant(), idJournal);
        journalBatchDao.saveSituationApresJournal(journal.getListSituationApres(), idJournal);
    }

    private void processErrorSendFile(String numProg, Exception e) {
        FichierFelix ff = fichierFelixDao.findByNumprog(numProg);
        String message = String.format("Probleme lors de l'envoi du fichier PREPREP  %s à FELIX", ff.getNomFichier());
        LOGGER.error(message, e);
        createErrorMessage(ff, message);
    }

    private void createErrorMessage(FichierFelix ff, String message) {
        if(ff != null) {
            ff.setStatut(StatutFichierFelix.EN_ERREUR);
            FichierFelixLog felixLog = new FichierFelixLog();
            felixLog.setLog(message);
            felixLog.setDateCreation(new Date());
            fichierFelixLogDao.save(felixLog);
            ff.getLogs().add(felixLog);

            fichierFelixDao.saveAndFlush(ff);
        }
    }

    private String getPreprepDir() {
        return configAdmap.get(EnvConstants.FELIX_PREPREP_DIR.property());
    }

}

