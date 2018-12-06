package fr.sacem.priam.batch.common.listener.importPenef;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;
import org.springframework.batch.item.ExecutionContext;

import java.util.Set;

/**
 * Created by embouazzar on 29/11/2018.
 */
public class StepArchiveFlatFileListener extends StepExecutionListenerSupport {

    public static final String LIGNE_PROGRAMME_ERRORS = "ligne-programme-errors";

    private ExecutionContext executionContext;

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        executionContext = stepExecution.getExecutionContext();
        Set<String> errors = (Set<String>) executionContext.get(LIGNE_PROGRAMME_ERRORS);
        ExitStatus exitStatus = null;
        if(errors == null || errors.isEmpty()) {
            exitStatus = new ExitStatus("COMPLETED");
        } else {
            exitStatus = new ExitStatus("COMPLETED WITH SKIPS");
        }
        return exitStatus;
    }

}
