package fr.sacem.priam.batch.participants.req.writer;

import fr.sacem.priam.batch.participants.req.domain.CatalogueCms;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.core.io.Resource;

/**
 * Created by benmerzoukah on 14/06/2018.
 */
public class ParticipantsReqCsvFileItemWriter extends FlatFileItemWriter<CatalogueCms> {

    private Resource resource;

    @Override
    public void setResource(Resource resource) {
        this.resource = resource;
        super.setResource(resource);
    }

    @AfterStep
    public void afterStep(StepExecution stepExecution) {
        ExecutionContext stepContext = stepExecution.getExecutionContext();
        stepContext.put("PARTICIPATNS_FILE_REQ", this.resource.getFilename());
    }
}
