package fr.sacem.priam.batch.enrichissement.cat.listener;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;

/**
 * Created by benmerzoukah on 25/05/2018.
 */
public class CataloguePenefFraReaderStepListner extends StepExecutionListenerSupport {

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        if(stepExecution.getExecutionContext().containsKey("idFichier")
                && stepExecution.getExecutionContext().get("idFichier") != null) {
            return stepExecution.getExitStatus();
        }

        return ExitStatus.NOOP;
    }
}
