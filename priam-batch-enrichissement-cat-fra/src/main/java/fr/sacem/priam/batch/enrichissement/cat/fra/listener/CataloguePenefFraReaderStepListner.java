package fr.sacem.priam.batch.enrichissement.cat.fra.listener;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;

/**
 * Created by benmerzoukah on 25/05/2018.
 */
public class CataloguePenefFraReaderStepListner extends StepExecutionListenerSupport {

    private static final long SLEEP_TIME = 30 * 1000;

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        if(stepExecution.getExecutionContext().containsKey("idFichier")
                && stepExecution.getExecutionContext().get("idFichier") != null) {
            return stepExecution.getExitStatus();
        }

        // Pas de fichier on peut attendre unpeu
//        try {
//            Thread.sleep(SLEEP_TIME);
//        } catch (InterruptedException e) {
//            return ExitStatus.FAILED;
//        }

        return ExitStatus.NOOP;
    }
}
