package fr.sacem.priam.batch.common.service.importPenef;

import fr.sacem.priam.batch.common.domain.LigneCatalogueOctav;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;

public class CatalogueOctavAntProcessor implements ItemProcessor<LigneCatalogueOctav,LigneCatalogueOctav> {

    private static final int pointsANT = 6;
    private StepExecution stepExecution;

    @Override
    public LigneCatalogueOctav process(LigneCatalogueOctav ligneCatalogueOctav) throws Exception {
        JobParameters jobParameters = stepExecution.getJobParameters();
        JobParameter jobParameterNumProg =jobParameters.getParameters().get("numProg");
        String numProgValue = jobParameterNumProg.getValue().toString();
        ligneCatalogueOctav.setNumProg(numProgValue);

        ligneCatalogueOctav.setNbrDif(pointsANT);

        return ligneCatalogueOctav;
    }

    @BeforeStep
    public void saveStepExecution(StepExecution stepExecution) {
        this.stepExecution = stepExecution;
    }

}
