package fr.sacem.priam.catcms.api;

import fr.sacem.priam.catcms.api.dto.CatalogueCritereRecherche;
import fr.sacem.priam.model.dao.jpa.catcms.CatalogueCmsDao;
import fr.sacem.priam.model.domain.catcms.CatalogueCms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/app/rest/")
public class CatalogueResource {

    @Autowired
    CatalogueCmsDao catalogueRdoDao;

    @RequestMapping(
            value = "catalogue/search",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<CatalogueCms> findByCriteria(@RequestBody CatalogueCritereRecherche catalogueCritereRecherche, Pageable pageable) {


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

    /*@RequestMapping(
            value = "catalogue/oeuvre/delete/{ide12}",
            method = RequestMethod.DELETE,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public CatalogueCms deleteOeuvre(@PathVariable(name = "ide12") Long ide12, @RequestBody CatalogueCms catalogueRdo){
//        CatalogueCms deletedOeuvre = catalogueRdoDao.findByIde12AndTypeCMS(ide12, catalogueRdo.getTypeCMS());
        CatalogueCms deletedOeuvre = catalogueRdoDao.findOne(catalogueRdo.getId());

        deletedOeuvre.setDateSortie(new Date());
        deletedOeuvre.setTypeSortie("Manuelle");
        *//*deletedOeuvre.setDateSortie(catalogueRdo.getDateSortie());
        deletedOeuvre.setTypeSortie(catalogueRdo.getTypeSortie());*//*
        deletedOeuvre.setRaisonSortie(catalogueRdo.getRaisonSortie());
        return catalogueRdoDao.saveAndFlush(deletedOeuvre);
    }*/

    @RequestMapping(
            value = "catalogue/oeuvre/delete/{id}",
            method = RequestMethod.DELETE,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public CatalogueCms deleteOeuvre(@PathVariable(name = "id") Long id, @RequestBody CatalogueCms catalogueRdo){
        CatalogueCms deletedOeuvre = catalogueRdoDao.findOne(id);
        deletedOeuvre.setDateSortie(new Date());
        deletedOeuvre.setTypeSortie("Manuelle");
        deletedOeuvre.setRaisonSortie(catalogueRdo.getRaisonSortie());
        return catalogueRdoDao.saveAndFlush(deletedOeuvre);
    }


}
