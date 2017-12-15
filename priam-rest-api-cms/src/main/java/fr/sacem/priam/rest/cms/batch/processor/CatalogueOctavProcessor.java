package fr.sacem.priam.rest.cms.batch.processor;

import fr.sacem.priam.rest.cms.batch.domain.CatalogueOctavItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

/**
 * Created by benmerzoukah on 04/12/2017.
 */
public class CatalogueOctavProcessor implements ItemProcessor<CatalogueOctavItem, CatalogueOctavItem> {
    private static final Logger LOG = LoggerFactory.getLogger(CatalogueOctavProcessor.class);

    @Override
    public CatalogueOctavItem process(CatalogueOctavItem catalogueOctavItem) throws Exception {
        LOG.debug("ligne processing d'intégration du catalogue");

        return catalogueOctavItem;
    }
}
