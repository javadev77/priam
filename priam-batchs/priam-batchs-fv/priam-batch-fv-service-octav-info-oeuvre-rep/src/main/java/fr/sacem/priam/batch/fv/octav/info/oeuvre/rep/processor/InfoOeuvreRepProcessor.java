package fr.sacem.priam.batch.fv.octav.info.oeuvre.rep.processor;

import fr.sacem.priam.batch.common.domain.LigneProgrammeFV;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

/**
 * Created with IntelliJ IDEA.
 * @author Yegor Bugayenko (yegor@tpc2.com)
 * @version $Id$
 * @since 1.0
 */
public class InfoOeuvreRepProcessor implements ItemProcessor<LigneProgrammeFV , LigneProgrammeFV> {

    private StepExecution stepExecution;

    @Autowired
    private InfoOeuvreRepValidator validator;

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        this.stepExecution = stepExecution;
    }

    @Override
    public LigneProgrammeFV process(final LigneProgrammeFV infoOeuvre) throws Exception {

        BindingResult errors = new BeanPropertyBindingResult(infoOeuvre, "infoOeuvre-"+ infoOeuvre.getLineNumber());
        validator.validate(infoOeuvre, errors);

        if (errors.hasErrors()) {
            stepExecution.getJobExecution().stop();
            return null;
        }

        if(Integer.valueOf(infoOeuvre.getStatut()) >= 0){
            return infoOeuvre;
        }
        return null;
    }
}
