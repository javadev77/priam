package fr.sacem.priam.batch.generation.catalogue.listener;

import fr.sacem.priam.batch.common.domain.Admap;
import fr.sacem.priam.batch.common.util.UtilFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created by embouazzar on 11/08/2018.
 */
public class JobCompletionGenerationCatalogueListener extends JobExecutionListenerSupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobCompletionGenerationCatalogueListener.class);
    private UtilFile utilFile;
    private ExecutionContext executionContext;

    @Autowired
    Admap admap;

    public JobCompletionGenerationCatalogueListener() {
        utilFile = new UtilFile();
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        Collection<StepExecution> stepExecutions = jobExecution.getStepExecutions();
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            Iterator it = stepExecutions.iterator();
            while (it.hasNext()) {
                StepExecution myStepExecution = (StepExecution) it.next();
                executionContext = myStepExecution.getExecutionContext();
                if (executionContext != null) {
                    String reqFileName = executionContext.getString("CATALOGUE_FILE");
                    try {
                        new UtilFile().addToZipFileAndFlag(reqFileName, admap.getOutputFile());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
