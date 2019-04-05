package fr.sacem.priam.batch.fv.repartition.listener;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;

import java.util.ArrayList;

public class GenerationListener extends StepExecutionListenerSupport {
    @Override
    public void beforeStep(StepExecution stepExecution) {
        /*stepExecution.getExecutionContext().put("ligne-programme-errors", new HashSet<>());*/
        stepExecution.getExecutionContext().put("ligne-preprep-errors", new ArrayList<>());
    }
}
