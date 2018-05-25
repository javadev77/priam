package fr.sacem.priam.batch.affectation.listener;

import fr.sacem.priam.batch.common.dao.LigneProgrammeBatchDao;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by benmerzoukah on 08/01/2018.
 */
public class CalculPointsStepListener extends StepExecutionListenerSupport {

    @Autowired
    LigneProgrammeBatchDao ligneProgrammeBatchDao;

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {

        String numProg = stepExecution.getJobParameters().getString("numProg");
        this.ligneProgrammeBatchDao.deleteDoublonsMemeMontant(numProg);

        return stepExecution.getExitStatus();
    }
}
