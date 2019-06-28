package fr.sacem.priam.batch.fv.serviceimport.listener;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;

/**
 * Created with IntelliJ IDEA.
 * @author Yegor Bugayenko (yegor@tpc2.com)
 * @version $Id$
 * @since 1.0
 */
public class StepReadCsvImportListener extends StepExecutionListenerSupport {

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        String isError = stepExecution.getJobExecution().getExecutionContext().getString("IsError");
        if(Boolean.valueOf(isError)) {
            return ExitStatus.NOOP;
        }
        return stepExecution.getExitStatus();
    }
}
