package fr.sacem.priam.batch.fv.octav.writer;

import fr.sacem.priam.batch.common.domain.LigneProgrammeFV;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.core.io.Resource;

/**
 * Created by benmerzoukah on 14/06/2018.
 */
public class OctavCtnuReqCsvFileItemWriter extends FlatFileItemWriter<LigneProgrammeFV> {

    private Resource resource;

    @Override
    public void setResource(Resource resource) {
        this.resource = resource;
        super.setResource(resource);
    }

    @AfterStep
    public void afterStep(StepExecution stepExecution) {
        ExecutionContext stepContext = stepExecution.getExecutionContext();
        stepContext.put("REQ_FILE_NAME", this.resource.getFilename());
    }
}
