package fr.sacem.priam.batch.repartition.fv.decision;

import fr.sacem.priam.batch.common.dao.ProgrammeBatchDao;
import fr.sacem.priam.batch.common.domain.Programme;
import fr.sacem.priam.common.TypeUtilisationEnum;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class FlowDecision implements JobExecutionDecider {

    public static final String FAMILLE_VALORISATION = "FDSVAL";
    public static final String TYPE_REPART_AYANT_DROIT = "AYANT_DROIT";
    public static final String TYPE_REPART_OEUVRE_AD = "OEUVRE_AD";

    @Autowired
    ProgrammeBatchDao programmeBatchDao;

    @Override
    public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
        String numeroProgramme = stepExecution.getJobExecution().getExecutionContext().getString("numeroProgramme");
        Optional<String> optionalNumProg = Optional.ofNullable(numeroProgramme);
        if(optionalNumProg.isPresent()){
            Programme programme = programmeBatchDao.findByNumProg(numeroProgramme);
            if(FAMILLE_VALORISATION.equals(TypeUtilisationEnum.getValue(programme.getTypeUtilisation()).getCodeFamille())
                    && TYPE_REPART_OEUVRE_AD.equals(programme.getTypeRepart())){
                return new FlowExecutionStatus("MAJ_REFERENTIEL");
            }
        }
        return FlowExecutionStatus.COMPLETED;
    }
}
