package fr.sacem.priam.batch.generation.catalogue.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;

/**
 * Created by embouazzar on 02/08/2018.
 */
public class StepBeginGenerationCatalogueFraListener extends StepExecutionListenerSupport {

    private static final Logger LOG = LoggerFactory.getLogger(StepBeginGenerationCatalogueFraListener.class);

    @Override
    public void beforeStep(StepExecution stepExecution) {
        stepExecution.getJobExecution().getExecutionContext().put("TYPE_CMS", "FR");
    }
}
