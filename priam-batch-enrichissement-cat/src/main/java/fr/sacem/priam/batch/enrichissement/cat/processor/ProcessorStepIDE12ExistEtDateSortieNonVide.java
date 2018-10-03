package fr.sacem.priam.batch.enrichissement.cat.processor;

import fr.sacem.priam.batch.common.catalogue.mapper.processor.AbstractCatalogueProcessor;
import fr.sacem.priam.batch.common.domain.CatalogueFra;

import fr.sacem.priam.batch.common.domain.CataloguePenefFra;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

/**
 * Created by embouazzar on 28/09/2018.
 */
public class ProcessorStepIDE12ExistEtDateSortieNonVide extends AbstractCatalogueProcessor {

    private static final Logger log = LoggerFactory.getLogger(ProcessorStepIDE12ExistEtDateSortieNonVide.class);

    private ExecutionContext executionContext;

    private static int nbCreationStepIDE12ExistEtDateSortieNonVide = 0;

    @Value("#{jobParameters['typeCMS']}")
    private String typeCMS;

    public CatalogueFra process(CataloguePenefFra cataloguePenefFra) throws Exception {

        CatalogueFra catalogueFra = super.process(cataloguePenefFra);

        catalogueFra.setTypeCMS(typeCMS);

        nbCreationStepIDE12ExistEtDateSortieNonVide++;

        return catalogueFra;
    }

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        this.executionContext = stepExecution.getExecutionContext();
    }

    @AfterStep
    public void afterStep(StepExecution stepExecution) {
        log.info("nbCreationStepIDE12ExistEtDateSortieNonVide : " + nbCreationStepIDE12ExistEtDateSortieNonVide);
        stepExecution.getJobExecution().getExecutionContext().put("nbCreationStepIDE12ExistEtDateSortieNonVide", String.valueOf(nbCreationStepIDE12ExistEtDateSortieNonVide));
    }
}
