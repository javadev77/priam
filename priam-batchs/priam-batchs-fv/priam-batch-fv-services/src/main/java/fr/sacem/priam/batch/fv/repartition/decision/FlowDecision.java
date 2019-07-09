//package fr.sacem.priam.batch.fv.repartition.decision;
//
//import org.springframework.batch.core.JobExecution;
//import org.springframework.batch.core.StepExecution;
//import org.springframework.batch.core.job.flow.FlowExecutionStatus;
//import org.springframework.batch.core.job.flow.JobExecutionDecider;
//
//public class FlowDecision implements JobExecutionDecider {
//
//    public static final String TYPE_REPART_FV_OEUVRE = "OEUVRE";
//    public static final String TYPE_REPART_FV_AYANT_DROIT = "AYANT_DROIT";
//    public static final String TYPE_REPART_FV_OEUVRE_AD = "OEUVRE_AD";
//
//    @Override
//    public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
//        String typeRepartFV = jobExecution.getJobParameters().getString("typeRepartFV");
//        if(TYPE_REPART_FV_OEUVRE.equals(typeRepartFV)){
//            return new FlowExecutionStatus(TYPE_REPART_FV_OEUVRE);
//        } else if(TYPE_REPART_FV_AYANT_DROIT.equals(typeRepartFV)) {
//            return new FlowExecutionStatus(TYPE_REPART_FV_AYANT_DROIT);
//        } else if(TYPE_REPART_FV_OEUVRE_AD.equals(typeRepartFV)){
//            return new FlowExecutionStatus(TYPE_REPART_FV_OEUVRE_AD);
//        }
//        return FlowExecutionStatus.FAILED;
//    }
//}
