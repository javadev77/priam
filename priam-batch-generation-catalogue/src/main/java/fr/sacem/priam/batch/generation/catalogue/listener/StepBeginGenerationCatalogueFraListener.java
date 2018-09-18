package fr.sacem.priam.batch.generation.catalogue.listener;

import fr.sacem.priam.batch.common.domain.Admap;
import fr.sacem.priam.batch.common.util.UtilFile;
import org.apache.commons.io.FileDeleteStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.zip.ZipOutputStream;

/**
 * Created by embouazzar on 02/08/2018.
 */
public class StepBeginGenerationCatalogueFraListener extends StepExecutionListenerSupport {

    private static final Logger LOG = LoggerFactory.getLogger(StepBeginGenerationCatalogueFraListener.class);

    @Autowired
    Admap admap;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        stepExecution.getJobExecution().getExecutionContext().put("TYPE_CMS", "FR");
    }

}
