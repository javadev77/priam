package fr.sacem.priam.batch.catcms.rdo.processor;

import fr.sacem.priam.batch.catcms.rdo.domain.CatalogueCms;
import fr.sacem.priam.batch.catcms.rdo.domain.CatalogueRdoCsv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import java.util.Date;

/**
 * Created by benmerzoukah on 04/12/2017.
 */
public class CatalogueRdoProcessor implements ItemProcessor<CatalogueRdoCsv, CatalogueCms> {

    private static final Logger log = LoggerFactory.getLogger(CatalogueRdoProcessor.class);
    public static final String AUTOMATIQUE = "Automatique";

    @Override
    public CatalogueCms process(CatalogueRdoCsv catalogueRdoCsv) throws Exception {
        CatalogueCms catalogueCms = new CatalogueCms();

        catalogueCms.setTypeCMS(catalogueRdoCsv.getTypeCMS());
        catalogueCms.setIde12(catalogueRdoCsv.getIde12());
        catalogueCms.setTitre(catalogueRdoCsv.getTitre());
        catalogueCms.setTypUtilGen(catalogueRdoCsv.getTypUtilGen());
        catalogueCms.setRole(catalogueRdoCsv.getRole());
        catalogueCms.setParticipant(catalogueRdoCsv.getParticipant());
        catalogueCms.setDateEntree(catalogueRdoCsv.getDateCatal());
        catalogueCms.setTypeInscription(AUTOMATIQUE);

        if(catalogueRdoCsv.getPourcentageDP() == 1) {
            catalogueCms.setDateSortie(new Date());
            catalogueCms.setTypeSortie(AUTOMATIQUE);
        }

        return catalogueCms;
    }


}
