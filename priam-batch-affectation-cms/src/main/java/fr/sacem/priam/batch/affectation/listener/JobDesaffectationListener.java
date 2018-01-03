package fr.sacem.priam.batch.affectation.listener;

import fr.sacem.dao.ProgrammeBatchDao;
import fr.sacem.service.importPenef.FichierBatchService;
import fr.sacem.service.importPenef.FichierBatchServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
/**
 * Created by benmerzoukah on 02/01/2018.
 */
public class JobDesaffectationListener extends JobExecutionListenerSupport {


    private static final Logger LOG = LoggerFactory.getLogger(JobDesaffectationListener.class);

    @Autowired
    ProgrammeBatchDao programmeBatchDao;

    @Autowired
    private FichierBatchService fichierBatchService;


    @Override
    public void beforeJob(JobExecution jobExecution) {
        String numProg = jobExecution.getJobParameters().getString("numProg");
        programmeBatchDao.majStattutEligibilite(numProg, "EN_COURS_DESAFFECTATION");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            String numProg = jobExecution.getJobParameters().getString("numProg");

            programmeBatchDao.majStattutEligibilite(numProg, "FIN_DESAFFECTATION");

            fichierBatchService.clearSelectedFichiers(numProg, "CHARGEMENT_OK");

            String user = jobExecution.getJobParameters().getString("username");
            programmeBatchDao.updateProgramme(numProg, user);



        }
    }


    public void setFichierBatchService(FichierBatchService fichierBatchService) {
        this.fichierBatchService = fichierBatchService;
    }

    public FichierBatchService getFichierBatchService() {
        return fichierBatchService;
    }
}
