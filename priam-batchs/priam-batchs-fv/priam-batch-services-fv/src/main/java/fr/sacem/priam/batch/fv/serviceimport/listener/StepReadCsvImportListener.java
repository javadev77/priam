package fr.sacem.priam.batch.fv.serviceimport.listener;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;

public class StepReadCsvImportListener extends StepExecutionListenerSupport {

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        String isError = stepExecution.getJobExecution().getExecutionContext().getString("IsError");
        if(Boolean.valueOf(isError).booleanValue()){
            return ExitStatus.NOOP;
        } else {
            return stepExecution.getExitStatus();
        }
    }
}
