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
    private static final String TYPE_REPART_FV_OEUVRE = "OEUVRE";
    private static final String TYPE_REPART_FV_AYANT_DROIT = "AYANT_DROIT";
    private static final String TYPE_REPART_FV_OEUVRE_AD = "OEUVRE_AD";



    @Override
    public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
        String famille = jobExecution.getJobParameters().getString("famille");
        String typeRepartFV = jobExecution.getJobParameters().getString("typeRepart");

        if("UC".equals(famille)){
            return new FlowExecutionStatus("REPART_CMS_OEUVRE");
        } else if("COPIEPRIV".equals(famille)) {
            return new FlowExecutionStatus("REPART_CP_OEUVRE");
        } else if("FDSVAL".equals(famille)) {
            if(TYPE_REPART_FV_OEUVRE.equals(typeRepartFV)){
                return new FlowExecutionStatus("REPART_FV_OEUVRE");
            } else if(TYPE_REPART_FV_AYANT_DROIT.equals(typeRepartFV)) {
                return new FlowExecutionStatus("REPART_FV_AYANT_DROIT");
            } else if(TYPE_REPART_FV_OEUVRE_AD.equals(typeRepartFV)){
                return new FlowExecutionStatus("REPART_FV_OEUVRE_AD");
            }
        }

        return FAILED;
    }
}

