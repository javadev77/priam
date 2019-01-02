package fr.sacem.priam.batch.fv.octav.rep.processor;

import fr.sacem.priam.batch.common.dao.LigneProgrammeFVDao;
import fr.sacem.priam.batch.common.domain.LigneProgrammeFV;
import fr.sacem.priam.batch.fv.octav.rep.reader.OeuvreCtnuRep;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created with IntelliJ IDEA.
 * @author Yegor Bugayenko (yegor@tpc2.com)
 * @version $Id$
 * @since 1.0
 */
public class OctavCtnuRepProcessor implements ItemProcessor<OeuvreCtnuRep, LigneProgrammeFV> {

    @Autowired
    LigneProgrammeFVDao ligneProgrammeFVDao;

    private ExecutionContext jobExecutionContext;

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        this.jobExecutionContext = stepExecution.getJobExecution().getExecutionContext();

    }

    @Override
    public LigneProgrammeFV process(OeuvreCtnuRep oeuvreCtnuRep) throws Exception {
        if(oeuvreCtnuRep != null) {
            if(oeuvreCtnuRep.getStatut() >= 0 && !oeuvreCtnuRep.getIde12().equals(oeuvreCtnuRep.getIde12cmplx())) {
                Long idFichier = jobExecutionContext.getLong("idFichier");
                LigneProgrammeFV oeuvreComplxContenant = ligneProgrammeFVDao.findOeuvreByIde12(Long.valueOf(oeuvreCtnuRep.getIde12cmplx()), idFichier);

                if(oeuvreComplxContenant == null) {
                    return null;
                }
                oeuvreComplxContenant.setIde12Complx(oeuvreCtnuRep.getIde12());
                oeuvreComplxContenant.setCdetypide12cmplx(oeuvreCtnuRep.getCdetypide12());

                return oeuvreComplxContenant;
            }
        }

        return null;
    }
}
