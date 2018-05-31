package fr.sacem.priam.catcms.api;

import fr.sacem.priam.catcms.api.dto.CatalogueCritereRecherche;
import fr.sacem.priam.model.dao.jpa.catcms.CatalogueRdoDao;
import fr.sacem.priam.model.domain.catcms.CatalogueRdo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

@RestController
@RequestMapping("/app/rest/")
public class CatalogueResource {

    @Autowired
    CatalogueRdoDao catalogueRdoDao;

    @RequestMapping(
            value = "catalogue/search",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<CatalogueRdo> findByCriteria(@RequestBody CatalogueCritereRecherche catalogueCritereRecherche, Pageable pageable) {


        if(catalogueCritereRecherche.isDisplayOeuvreNonEligible()){
            return catalogueRdoDao.findByCriteriaWithNonEligible(catalogueCritereRecherche.getTypeCMS(),
                    catalogueCritereRecherche.getIde12(),
                    catalogueCritereRecherche.getTitre(),
                    catalogueCritereRecherche.getParticipant(),
                    catalogueCritereRecherche.getPeriodeEntreeDateDebut(),
                    catalogueCritereRecherche.getPeriodeEntreeDateFin(),
                    catalogueCritereRecherche.getPeriodeRenouvellementDateDebut(),
                    catalogueCritereRecherche.getPeriodeRenouvellementDateFin(),
                    catalogueCritereRecherche.getPeriodeSortieDateDebut(),
                    catalogueCritereRecherche.getPeriodeSortieDateFin(), pageable);
        }

        if(catalogueCritereRecherche.getPeriodeEntreeDateFin()== null ||
                catalogueCritereRecherche.getPeriodeSortieDateFin() == null){
            catalogueCritereRecherche.setPeriodeEntreeDateFin(new Date());
            catalogueCritereRecherche.setPeriodeSortieDateFin(new Date());
        }

        return catalogueRdoDao.findByCriteriaWithoutNonEligible(catalogueCritereRecherche.getTypeCMS(),
                catalogueCritereRecherche.getIde12(),
                catalogueCritereRecherche.getTitre(),
                catalogueCritereRecherche.getParticipant(),
                catalogueCritereRecherche.getPeriodeEntreeDateDebut(),
                catalogueCritereRecherche.getPeriodeEntreeDateFin(),
                catalogueCritereRecherche.getPeriodeRenouvellementDateDebut(),
                catalogueCritereRecherche.getPeriodeRenouvellementDateFin(),
                catalogueCritereRecherche.getPeriodeSortieDateFin(), pageable);
    }


}
