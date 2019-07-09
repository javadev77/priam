package fr.sacem.priam.batch.felix.writer;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.core.io.Resource;

/**
 * Created by benmerzoukah on 14/06/2018.
 */
public class FelixCsvFileItemWriter<T> extends FlatFileItemWriter<T> {

    private Resource resource;

    @Override
    public void setResource(Resource resource) {
        this.resource = resource;
        super.setResource(resource);
    }

    @AfterStep
    public ExitStatus afterStep(StepExecution stepExecution) {
        stepExecution.getJobExecution().getExecutionContext().put("FELIX_REPART_FILENAME", this.resource.getFilename());
        return stepExecution.getExitStatus();
    }
}
