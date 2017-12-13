package fr.sacem.priam.rest.cms.batch.processor;

import fr.sacem.priam.rest.cms.batch.domain.CatalogueOctavItem;
import org.springframework.batch.item.ItemProcessor;

/**
 * Created by benmerzoukah on 04/12/2017.
 */
public class EligibiliteProcessor implements ItemProcessor<CatalogueOctavItem, CatalogueOctavItem> {

    @Override
    public CatalogueOctavItem process(CatalogueOctavItem catalogueOctavItem) throws Exception {
        return catalogueOctavItem;
    }
}
