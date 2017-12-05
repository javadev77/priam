package fr.sacem.priam.batch.affectation.processor;

import fr.sacem.priam.batch.affectation.domain.CatalogueOctavItem;
import org.springframework.batch.item.ItemProcessor;

/**
 * Created by benmerzoukah on 04/12/2017.
 */
public class CatalogueOctavProcessor implements ItemProcessor<CatalogueOctavItem, CatalogueOctavItem> {

    @Override
    public CatalogueOctavItem process(CatalogueOctavItem catalogueOctavItem) throws Exception {
        return catalogueOctavItem;
    }
}
