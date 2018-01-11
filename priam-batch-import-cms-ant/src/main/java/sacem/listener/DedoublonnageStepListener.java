package sacem.listener;

import fr.sacem.dao.FichierRepository;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by benmerzoukah on 27/12/2017.
 */
public class DedoublonnageStepListener extends StepExecutionListenerSupport {

    @Autowired
    FichierRepository fichierRepository;

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return stepExecution.getExitStatus();
    }
}
