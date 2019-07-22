package fr.sacem.priam.batch.fv.ad.info.rep.processor;

import fr.sacem.priam.batch.common.dao.AyanrtDroitPersDao;
import fr.sacem.priam.batch.fv.ad.info.rep.domain.AyantDroitPers;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import java.util.LinkedHashSet;
import java.util.Set;

import static fr.sacem.priam.batch.common.fv.util.EnrichissementUtils.NB_INFOS_RECUS;

public class AdInfoProcessor implements ItemProcessor<AyantDroitPers, AyantDroitPers> {

    @Autowired
    AyanrtDroitPersDao ayanrtDroitPersDao;

    @Autowired
    AdInfoRepValidator validator;

    private ExecutionContext jobExecutionContext;
    private StepExecution stepExecution;

    private static Set<Long> numpersRecus = new LinkedHashSet<>();

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        this.jobExecutionContext = stepExecution.getJobExecution().getExecutionContext();
        this.stepExecution = stepExecution;
        this.jobExecutionContext.putLong(NB_INFOS_RECUS, 0);
    }

    @Override
    public AyantDroitPers process(AyantDroitPers ayantDroitPers) throws Exception {
        Long idFichier = jobExecutionContext.getLong("idFichier");

        numpersRecus.add(Long.valueOf(ayantDroitPers.getNumPers()));
        jobExecutionContext.putLong(NB_INFOS_RECUS, numpersRecus.size());

        BindingResult errors = new BeanPropertyBindingResult(ayantDroitPers, "ayantDroitPers-"+ ayantDroitPers.getLineNumber());
        validator.validate(ayantDroitPers, errors);

        if (errors.hasErrors()) {
            stepExecution.getJobExecution().stop();
            return null;
        }


        if("OK".equals(ayantDroitPers.getStatut())){
            if(ayanrtDroitPersDao.isNumpersExistByIdFichier(ayantDroitPers.getNumPers(), idFichier)){
                return ayantDroitPers;
            }
        }
        return null;
    }
}
