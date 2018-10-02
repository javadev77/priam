package fr.sacem.priam.batch.enrichissement.cat.processor;

import fr.sacem.priam.batch.enrichissement.cat.domain.CataloguePenefFra;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;

/**
 * Created by benmerzoukah on 17/05/2018.
 */

public class ProcessorCataloguePenef implements ItemProcessor<CataloguePenefFra, CataloguePenefFra> {

    private static final Logger log = LoggerFactory.getLogger(ProcessorCataloguePenef.class);

    public static final String MESSAGE_FORMAT = "Ligne '%s': Le champ \"%s\" avec la valeur \"%s\" n'a pas le bon format attendu";
    public static final String LIGNE_PROGRAMME_ERRORS = "ligne-programme-errors";

    private ExecutionContext executionContext;


    @Override
    public CataloguePenefFra process(CataloguePenefFra cataloguePenefFra) throws Exception {

        Long idFichier = executionContext.getLong("idFichier");
        cataloguePenefFra.setIdFichier(idFichier);

        log.info("---- IDE12 ---- " + cataloguePenefFra.getIde12());
        log.info("---- Current Line ---- " + cataloguePenefFra);

        return cataloguePenefFra;
    }

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {

        this.executionContext = stepExecution.getExecutionContext();
    }
}
