package fr.sacem.priam.batch.affectation.processor;

import fr.sacem.domain.LigneProgrammeCMS;
import fr.sacem.priam.batch.affectation.domain.CatalogueOctavItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

/**
 * Created by benmerzoukah on 04/12/2017.
 */
public class EligibiliteProcessor implements ItemProcessor<LigneProgrammeCMS, LigneProgrammeCMS> {
    private static final Logger LOG = LoggerFactory.getLogger(EligibiliteProcessor.class);

    @Override
    public LigneProgrammeCMS process(LigneProgrammeCMS catalogueOctavItem) throws Exception {
        LOG.debug("ligne processing de l'éligibilité");
        return catalogueOctavItem;
    }
}
