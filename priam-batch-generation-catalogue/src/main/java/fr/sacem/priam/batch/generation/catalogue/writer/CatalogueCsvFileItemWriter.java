package fr.sacem.priam.batch.generation.catalogue.writer;

import fr.sacem.priam.batch.generation.catalogue.domain.CatalogueCmsGenerated;
import org.aspectj.lang.annotation.Before;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.core.io.Resource;

/**
 * Created by embouazzar on 01/08/2018.
 */
public class CatalogueCsvFileItemWriter extends FlatFileItemWriter<CatalogueCmsGenerated> {

    private Resource resource;

    @Override
    public void setResource(Resource resource) {
        this.resource = resource;
        super.setResource(resource);
    }


    @AfterStep
    public void afterStep(StepExecution stepExecution) {
        ExecutionContext stepContext = stepExecution.getExecutionContext();
        stepContext.put("CATALOGUE_FILE", this.resource.getFilename());
    }

}
