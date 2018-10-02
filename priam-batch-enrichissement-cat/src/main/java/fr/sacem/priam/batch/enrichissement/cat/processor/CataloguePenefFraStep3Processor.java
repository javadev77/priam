package fr.sacem.priam.batch.enrichissement.cat.processor;

import fr.sacem.priam.batch.common.domain.CatalogueFra;
import fr.sacem.priam.batch.enrichissement.cat.domain.CataloguePenefFra;
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
 * Created by benmerzoukah on 17/05/2018.
 */

public class CataloguePenefFraStep3Processor implements ItemProcessor<CataloguePenefFra, CatalogueFra> {

    private static final Logger log = LoggerFactory.getLogger(CataloguePenefFraStep3Processor.class);

    public static final String MESSAGE_FORMAT = "Ligne '%s': Le champ \"%s\" avec la valeur \"%s\" n'a pas le bon format attendu";
    public static final String LIGNE_PROGRAMME_ERRORS = "ligne-programme-errors";

    private ExecutionContext executionContext;

    private static int nbRenouvellement = 0;

    @Value("#{jobParameters['typeCMS']}")
    private String typeCMS;


    @Override
    public CatalogueFra process(CataloguePenefFra cataloguePenefFra) throws Exception {

        CatalogueFra catalogueFra = new CatalogueFra();

//        catalogueFra.setTypeCMS("FR");
        catalogueFra.setTypeCMS(typeCMS);
        catalogueFra.setIde12(cataloguePenefFra.getIde12());
        catalogueFra.setTypUtilGen(cataloguePenefFra.getCdeTypUtilOri());
        catalogueFra.setDateEntree(new Date());
        catalogueFra.setTypeInscription("Automatique");
        catalogueFra.setParticipant(cataloguePenefFra.getNomParticipant1());
        catalogueFra.setRole(cataloguePenefFra.getRoleParticipant1());
        catalogueFra.setDateRenouvellement(new Date());
        catalogueFra.setTitre(cataloguePenefFra.getTitreOeuvre());

        nbRenouvellement++;

        log.info("---- Current Line ---- " + catalogueFra);

        return catalogueFra;
    }

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {

        this.executionContext = stepExecution.getExecutionContext();
    }

    @AfterStep
    public void afterStep(StepExecution stepExecution) {
        log.info("nbRenouvellement : " + nbRenouvellement);
        stepExecution.getJobExecution().getExecutionContext().put("nbRenouvellement", String.valueOf(nbRenouvellement));
    }
}
