package fr.sacem.priam.batch.fv.adclesprotection.rep.processor;

import fr.sacem.priam.batch.common.dao.AyanrtDroitPersDao;
import fr.sacem.priam.batch.common.dao.LigneProgrammeFVDao;
import fr.sacem.priam.batch.common.domain.LigneProgrammeFV;
import fr.sacem.priam.batch.common.fv.util.OctavDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class AdclesProtectionRepProcessor implements ItemProcessor<OctavDTO, OctavDTO> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdclesProtectionRepProcessor.class);

    @Autowired
    LigneProgrammeFVDao ligneProgrammeFVDao;

    @Autowired
    AyanrtDroitPersDao ayanrtDroitPersDao;

    private ExecutionContext jobExecutionContext;

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        this.jobExecutionContext = stepExecution.getJobExecution().getExecutionContext();

    }

    @Override
    public OctavDTO process(OctavDTO octavDTO) throws Exception {
        if(octavDTO != null) {
            if(octavDTO.getStatut() >= 0 ) {
                Long idFichier = jobExecutionContext.getLong("idFichier");
                LigneProgrammeFV oeuvreByIde12 = ligneProgrammeFVDao.findOeuvreByIde12(Long.valueOf(octavDTO.getIde12()), idFichier);

                if(oeuvreByIde12 == null || octavDTO.getCoad() == null) {
                    return null;
                }

                octavDTO.setIdOeuvreFv(oeuvreByIde12.getId());
                octavDTO.setNumpersExist(ayanrtDroitPersDao.isNumpersExist(octavDTO.getNumPers()));

                return octavDTO;

            }
        }

        return null;
    }
}
