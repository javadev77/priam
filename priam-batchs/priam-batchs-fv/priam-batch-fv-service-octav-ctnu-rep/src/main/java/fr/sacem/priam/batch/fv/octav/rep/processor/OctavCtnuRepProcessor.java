package fr.sacem.priam.batch.fv.octav.rep.processor;

import fr.sacem.priam.batch.common.dao.LigneProgrammeFVDao;
import fr.sacem.priam.batch.common.domain.LigneProgrammeFV;
import fr.sacem.priam.batch.common.domain.OctavCtnu;
import java.util.Optional;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ExecutionContext;
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
public class OctavCtnuRepProcessor implements ItemProcessor<OctavCtnu, LigneProgrammeFV> {

    @Autowired
    LigneProgrammeFVDao ligneProgrammeFVDao;

    @Autowired
    OctavCtnuRepValidator validator;

    private ExecutionContext jobExecutionContext;
    private StepExecution stepExecution;

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        this.jobExecutionContext = stepExecution.getJobExecution().getExecutionContext();
        this.stepExecution = stepExecution;
    }

    @Override
    public LigneProgrammeFV process(OctavCtnu octavCtnu) throws Exception {
        if(octavCtnu != null) {
            BindingResult errors = new BeanPropertyBindingResult(octavCtnu, "octavCtnu-"+ octavCtnu.getLineNumber());
            validator.validate(octavCtnu, errors);

            if (errors.hasErrors()) {
                stepExecution.getJobExecution().stop();
                return null;
            }

            Optional<String> opt = Optional.ofNullable(octavCtnu.getIde12Ctnu()).filter(s -> !s.isEmpty());
            if(opt.isPresent()) {
                if((Integer.valueOf(octavCtnu.getStatut()) >= 0) && !octavCtnu.getIde12().equals(octavCtnu.getIde12Ctnu())) {
                    Long idFichier = jobExecutionContext.getLong("idFichier");

                    LigneProgrammeFV oeuvreComplxContenant =
                            ligneProgrammeFVDao.findOeuvreByIde12(
                                    Long.valueOf(octavCtnu.getIde12()), idFichier);
                    if (oeuvreComplxContenant == null) {
                        return null;
                    }
                    oeuvreComplxContenant.setIde12Complx(octavCtnu.getIde12Ctnu());
                    oeuvreComplxContenant.setCdetypide12cmplx(octavCtnu.getCdeTypIde12Ctnu());

                    return oeuvreComplxContenant;

                }
            }
        }

        return null;
    }
}
