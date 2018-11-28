package fr.sacem.priam.batch.participants.req.listener;

import fr.sacem.priam.batch.common.domain.Admap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by benmerzoukah on 14/06/2018.
 */
public class FlagDemiInterfaceStepListener extends StepListenerSupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(FlagDemiInterfaceStepListener.class);

    @Autowired
    Admap admap;

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        String reqFileName = stepExecution.getExecutionContext().getString("PARTICIPATNS_FILE_REQ");
        try(OutputStream out = new FileOutputStream(new File(admap.getOutputFile() + File.separator + "Flag_" + reqFileName))){
            out.write((reqFileName + "\n").getBytes());
        } catch (IOException e) {
            LOGGER.error("Erreur lors de la generation du fichier Flag !!! ", e);
        }
        return stepExecution.getExitStatus();
    }
}
