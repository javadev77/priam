package fr.sacem.priam.batch.fv.ad.info.rep.processor;

import fr.sacem.priam.batch.common.dao.AyanrtDroitPersDao;
import fr.sacem.priam.batch.fv.ad.info.rep.domain.AyantDroitPers;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

public class AdInfoProcessor implements ItemProcessor<AyantDroitPers, AyantDroitPers> {

    @Autowired
    AyanrtDroitPersDao ayanrtDroitPersDao;

    private ExecutionContext jobExecutionContext;

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        this.jobExecutionContext = stepExecution.getJobExecution().getExecutionContext();

    }

    @Override
    public AyantDroitPers process(AyantDroitPers ayantDroitPers) throws Exception {
        Long idFichier = jobExecutionContext.getLong("idFichier");
        if(ayantDroitPers!=null && "OK".equals(ayantDroitPers.getStatut())){
            if(ayanrtDroitPersDao.isNumpersExistByIdFichier(ayantDroitPers.getNumPers(), idFichier)){
                return ayantDroitPers;
            }
        }
        return null;
    }
}
