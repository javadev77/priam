package fr.sacem.priam.batch.affectation.processor;

import fr.sacem.priam.batch.common.domain.LigneCatalogueOctav;
import fr.sacem.priam.common.TypeUtilisationEnum;
import fr.sacem.priam.common.util.FileUtils;
import org.springframework.batch.item.ItemProcessor;

public class LigneCatalogueOctavProcessor implements ItemProcessor<LigneCatalogueOctav, LigneCatalogueOctav> {

    @Override
    public LigneCatalogueOctav process(LigneCatalogueOctav ligneCatalogueOctav) throws Exception {

        if (ligneCatalogueOctav.getTypeCMS().equals(FileUtils.CATALOGUE_OCTAV_TYPE_CMS_ANF)) {
            ligneCatalogueOctav.setTypeCMS(TypeUtilisationEnum.CMS_ANT.getCode());
        }
        return ligneCatalogueOctav;
    }
}
