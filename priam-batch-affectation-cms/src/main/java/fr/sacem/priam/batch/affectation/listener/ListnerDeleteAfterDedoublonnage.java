package fr.sacem.priam.batch.affectation.listener;

import fr.sacem.dao.LigneProgrammeDao;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by fandis on 15/12/2017.
 */
public class ListnerDeleteAfterDedoublonnage extends StepExecutionListenerSupport {

    @Autowired
    LigneProgrammeDao ligneProgrammeDao;

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        String numProg = stepExecution.getJobParameters().getString("numProg");
        this.ligneProgrammeDao.deleteDedoublonnage(numProg);

        return stepExecution.getExitStatus();
    }
}
