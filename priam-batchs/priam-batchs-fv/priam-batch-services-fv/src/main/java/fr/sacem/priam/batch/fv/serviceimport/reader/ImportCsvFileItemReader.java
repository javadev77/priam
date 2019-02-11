package fr.sacem.priam.batch.fv.serviceimport.reader;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.core.io.Resource;

/**
 * Created with IntelliJ IDEA.
 * @author Yegor Bugayenko (yegor@tpc2.com)
 * @version $Id$
 * @since 1.0
 */
public class ImportCsvFileItemReader<T> extends FlatFileItemReader<T> implements StepExecutionListener {

    @Override
    public void beforeStep(final StepExecution stepExecution) {
        String fileUploaded = stepExecution.getJobExecution().getJobParameters().getString("fileUploaded");
    }

    @Override
    public ExitStatus afterStep(final StepExecution stepExecution) {
       return stepExecution.getExitStatus();
    }

    @Override
    public void setResource(Resource resource) {

     /*   // Create springbatch input stream resource
        InputStreamResource res = new InputStreamResource(is);

        // Set resource
        super.setResource(res);*/
    }
}
