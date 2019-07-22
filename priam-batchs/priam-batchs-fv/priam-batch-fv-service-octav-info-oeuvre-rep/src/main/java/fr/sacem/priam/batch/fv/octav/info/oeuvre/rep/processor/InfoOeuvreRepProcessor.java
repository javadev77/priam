package fr.sacem.priam.batch.fv.octav.info.oeuvre.rep.processor;

import fr.sacem.priam.batch.common.domain.LigneProgrammeFV;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import java.util.LinkedHashSet;
import java.util.Set;

import static fr.sacem.priam.batch.common.fv.util.EnrichissementUtils.NB_INFOS_RECUS;

/**
 * Created with IntelliJ IDEA.
 * @author Yegor Bugayenko (yegor@tpc2.com)
 * @version $Id$
 * @since 1.0
 */
public class InfoOeuvreRepProcessor implements ItemProcessor<LigneProgrammeFV , LigneProgrammeFV> {

//    private static String NB_IDE12_RECUS = "NB_IDE12_RECUS";
    private StepExecution stepExecution;

    @Autowired
    private InfoOeuvreRepValidator validator;

    private static Set<Long> ide12Recus = new LinkedHashSet<>();

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {

        this.stepExecution = stepExecution;
        this.stepExecution.getJobExecution().getExecutionContext().putLong(NB_INFOS_RECUS, 0);
    }

    @Override
    public LigneProgrammeFV process(final LigneProgrammeFV infoOeuvre) throws Exception {
        if (infoOeuvre != null) {
            ide12Recus.add(Long.valueOf(infoOeuvre.getIde12()));
            this.stepExecution.getJobExecution().getExecutionContext().putLong(NB_INFOS_RECUS, ide12Recus.size());

            BindingResult errors = new BeanPropertyBindingResult(infoOeuvre, "infoOeuvre-" + infoOeuvre.getLineNumber());
            validator.validate(infoOeuvre, errors);

            if (errors.hasErrors()) {
                stepExecution.getJobExecution().stop();
                return null;
            }

            if (Integer.valueOf(infoOeuvre.getStatut()) >= 0) {
                return infoOeuvre;
            }
        }
        return null;
    }
}
