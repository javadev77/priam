package fr.sacem.priam.batch.felix.listener;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import fr.sacem.priam.common.constants.EnvConstants;
import fr.sacem.priam.common.util.SftpUtil;
import fr.sacem.priam.model.dao.jpa.FichierFelixDao;
import fr.sacem.priam.model.dao.jpa.FichierFelixLogDao;
import fr.sacem.priam.model.domain.FichierFelix;
import fr.sacem.priam.model.domain.FichierFelixLog;
import fr.sacem.priam.model.domain.StatutFichierFelix;
import fr.sacem.priam.model.domain.StatutProgramme;
import fr.sacem.priam.model.domain.dto.ProgrammeDto;
import fr.sacem.priam.security.model.UserDTO;
import fr.sacem.priam.services.ProgrammeService;
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

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

import static fr.sacem.priam.common.util.SftpUtil.SftpServer.FELIX;


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
//
//    @Autowired
//    private LignePreprepDao lignePreprepDao;

//    @Autowired
//    private FelixDataCPService felixDataCPService;
//
//
//    @Autowired
//    private FelixDataCMSService felixDataCMSService;
//
//    @Autowired
//    private ProgrammeDao programmeDao;

    @Autowired
    DataSource dataSource;

    @Override
    public void beforeJob(JobExecution jobExecution) {
        String numProg = jobExecution.getJobParameters().getString("numProg");
        System.out.println("numProg = " + numProg);

        jobExecution.getExecutionContext().put("FELIX_REPART_FILENAME", "");

        LOGGER.info(">>>>>> prepare FelixData <<<<<<");

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update("DELETE FROM PRIAM_LIGNE_PREPREP WHERE numProg=?", numProg);
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        System.out.println("jobExecution status " + jobExecution.getExitStatus());

        String numProg = jobExecution.getJobParameters().getString("numProg");
        if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
            String felixRepartFilename = jobExecution.getExecutionContext().getString("FELIX_REPART_FILENAME");

            // Envoi du fichier via FTP
            File tempFile = new File(getPreprepDir() + File.separator + felixRepartFilename);
            LOGGER.debug("==> Debut Envoi du fichier à FELIX = " + felixRepartFilename);

            try {
                SftpUtil.uploadFile(FELIX, tempFile, felixRepartFilename);
            } catch (JSchException e) {
                //processErrorSendFile(numProg, e);
            } catch (SftpException e) {
                //processErrorSendFile(numProg, e);
            } catch (IOException e) {
                //processErrorSendFile(numProg, e);
            }

            LOGGER.debug("<=== Fin Envoi du fichier à FELIX = " + felixRepartFilename);
            ProgrammeDto programmeDto = new ProgrammeDto();
            programmeDto.setNumProg(numProg);
            programmeDto.setStatut(StatutProgramme.MIS_EN_REPART);
            String userId = jobExecution.getJobParameters().getString("userId");
            programmeDto.setUsermaj(userId);
            UserDTO userDTO = new UserDTO();

            programmeService.majStatut(programmeDto, userDTO);

            FichierFelix ff = fichierFelixDao.findByNumprog(numProg);
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

        }

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

