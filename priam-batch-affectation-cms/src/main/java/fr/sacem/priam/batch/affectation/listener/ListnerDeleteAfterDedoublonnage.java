package fr.sacem.priam.batch.affectation.listener;

import fr.sacem.dao.LigneProgrammeBatchDao;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by fandis on 15/12/2017.
 */
public class ListnerDeleteAfterDedoublonnage extends StepExecutionListenerSupport {

    @Autowired
    LigneProgrammeBatchDao ligneProgrammeBatchDao;

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {

        System.out.println("ListnerDeleteAfterDedoublonnage = " + stepExecution.getStepName());
        String numProg = stepExecution.getJobParameters().getString("numProg");
        this.ligneProgrammeBatchDao.deleteDedoublonnage(numProg);

        return stepExecution.getExitStatus();
    }
}
