package fr.sacem.priam.batch.felix.decision;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import static org.springframework.batch.core.job.flow.FlowExecutionStatus.FAILED;
import org.springframework.batch.core.job.flow.JobExecutionDecider;

/**
 * Created with IntelliJ IDEA.
 * @author Habib Benmerzouka
 * @version $Id$
 * @since 1.0
 */
public class FlowDecision implements JobExecutionDecider {


    @Override
    public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
        String famille = jobExecution.getJobParameters().getString("famille");
        if("UC".equals(famille)){
            return new FlowExecutionStatus("REPART_CMS");
        } else if("COPIEPRIV".equals(famille)) {
            return new FlowExecutionStatus("REPART_CP");
        }

        return FAILED;
    }
}

