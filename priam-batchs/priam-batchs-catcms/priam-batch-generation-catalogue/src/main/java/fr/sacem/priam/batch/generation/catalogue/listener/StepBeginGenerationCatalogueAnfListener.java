package fr.sacem.priam.batch.generation.catalogue.listener;

import fr.sacem.priam.batch.common.domain.Admap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by embouazzar on 02/08/2018.
 */
public class StepBeginGenerationCatalogueAnfListener extends StepExecutionListenerSupport {

    private static final Logger LOG = LoggerFactory.getLogger(StepBeginGenerationCatalogueAnfListener.class);

    @Autowired
    Admap admap;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        stepExecution.getJobExecution().getExecutionContext().put("TYPE_CMS", "ANF");
    }
}
