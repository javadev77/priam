package fr.sacem.service.importPenef;

import fr.sacem.domain.LigneProgramme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

/**
 * Created by fandis on 16/11/2017.
 */
public class LigneProgrammeDedoublonnageStepTwoProcessor implements ItemProcessor<LigneProgramme, LigneProgramme> {
    private static final Logger log = LoggerFactory.getLogger(LigneProgrammeDedoublonnageStepTwoProcessor.class);

    @Override
    public LigneProgramme process(final LigneProgramme ligneProgramme) throws Exception {

        return ligneProgramme;
    }
}