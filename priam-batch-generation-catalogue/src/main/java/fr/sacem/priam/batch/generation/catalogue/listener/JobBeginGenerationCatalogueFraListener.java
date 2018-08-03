package fr.sacem.priam.batch.generation.catalogue.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;

/**
 * Created by embouazzar on 02/08/2018.
 */
public class JobBeginGenerationCatalogueFraListener extends JobExecutionListenerSupport {

    private static final Logger LOG = LoggerFactory.getLogger(JobBeginGenerationCatalogueFraListener.class);

    @Override
    public void beforeJob(JobExecution jobExecution) {
        super.beforeJob(jobExecution);
    }
}
