package fr.sacem.priam.batch.common.catalogue.mapper.processor;

import fr.sacem.priam.batch.common.domain.CatalogueFra;
import fr.sacem.priam.batch.common.domain.CataloguePenefFra;
import org.springframework.batch.item.ItemProcessor;

import java.util.Date;

/**
 * Created by embouazzar on 28/09/2018.
 */
public class AbstractCatalogueProcessor implements ItemProcessor<CataloguePenefFra, CatalogueFra> {
    @Override
    public CatalogueFra process(CataloguePenefFra cataloguePenefFra) throws Exception {

        CatalogueFra catalogueFra = new CatalogueFra();

        catalogueFra.setIde12(cataloguePenefFra.getIde12());
        catalogueFra.setTypUtilGen(cataloguePenefFra.getCdeTypUtilOri());
        catalogueFra.setDateEntree(new Date());
        catalogueFra.setTypeInscription("Automatique");
        catalogueFra.setParticipant(cataloguePenefFra.getNomParticipant1());
        catalogueFra.setRole(cataloguePenefFra.getRoleParticipant1());
        catalogueFra.setDateRenouvellement(new Date());
        catalogueFra.setTitre(cataloguePenefFra.getTitreOeuvre());

        return catalogueFra;
    }
}
