package fr.sacem.priam.batch.affectation.listener;

import fr.sacem.dao.LigneProgrammeBatchDao;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;

public class ListenerDeleteAfterCalculPointsAnt extends StepExecutionListenerSupport {

    @Autowired
    LigneProgrammeBatchDao ligneProgrammeBatchDao;

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {

        System.out.println("ListenerDeleteAfterCalculPointsAnt = " + stepExecution.getStepName());
        String numProg = stepExecution.getJobParameters().getString("numProg");
        this.ligneProgrammeBatchDao.deleteAfterCalculPointsAnt(numProg);

        return stepExecution.getExitStatus();
    }

}
