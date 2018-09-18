package fr.sacem.priam.batch.felix.listener;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepListenerSupport;

/**
 * Created by benmerzoukah on 23/07/2018.
 */
public class GenerateFelixFileStepListener extends StepListenerSupport {

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        System.out.println("GenerateFelixFileStepListener");
        return stepExecution.getExitStatus();
    }
}
