package fr.sacem.priam.catcms.api;

import fr.sacem.priam.catcms.api.dto.CatalogueCritereRecherche;
import fr.sacem.priam.model.dao.jpa.catcms.CatalogueCmsDao;
import fr.sacem.priam.model.dao.jpa.catcms.ParticipantsCatcmsDao;
import fr.sacem.priam.model.domain.catcms.CatalogueCms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/app/rest/")
public class CatalogueResource {

    @Autowired
    CatalogueCmsDao catalogueRdoDao;

    @Autowired
    ParticipantsCatcmsDao  participantsCatcmsDao;


    @RequestMapping(
            value = "catalogue/search",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<CatalogueCms> findByCriteria(@RequestBody CatalogueCritereRecherche catalogueCritereRecherche, Pageable pageable) {

        Page<CatalogueCms> datas = null;
        if(catalogueCritereRecherche.isDisplayOeuvreNonEligible()) {
            datas = catalogueRdoDao.findByCriteriaWithNonEligible(catalogueCritereRecherche.getTypeCMS(),
                    catalogueCritereRecherche.getIde12(),
                    catalogueCritereRecherche.getTitre(),
                    catalogueCritereRecherche.getParticipant(),
                    catalogueCritereRecherche.getPeriodeEntreeDateDebut(),
                    catalogueCritereRecherche.getPeriodeEntreeDateFin(),
                    catalogueCritereRecherche.getPeriodeRenouvellementDateDebut(),
                    catalogueCritereRecherche.getPeriodeRenouvellementDateFin(),
                    catalogueCritereRecherche.getPeriodeSortieDateDebut(),
                    catalogueCritereRecherche.getPeriodeSortieDateFin(), pageable);

        } else {
            if(catalogueCritereRecherche.getPeriodeEntreeDateFin()== null ||
                    catalogueCritereRecherche.getPeriodeSortieDateFin() == null){
                catalogueCritereRecherche.setPeriodeEntreeDateFin(new Date());
                catalogueCritereRecherche.setPeriodeSortieDateFin(new Date());
            }

            datas = catalogueRdoDao.findByCriteriaWithoutNonEligible(catalogueCritereRecherche.getTypeCMS(),
                    catalogueCritereRecherche.getIde12(),
                    catalogueCritereRecherche.getTitre(),
                    catalogueCritereRecherche.getParticipant(),
                    catalogueCritereRecherche.getPeriodeEntreeDateDebut(),
                    catalogueCritereRecherche.getPeriodeEntreeDateFin(),
                    catalogueCritereRecherche.getPeriodeRenouvellementDateDebut(),
                    catalogueCritereRecherche.getPeriodeRenouvellementDateFin(),
                    catalogueCritereRecherche.getPeriodeSortieDateFin(), pageable);

        }



        return datas;
    }

    @RequestMapping(
            value = "catalogue/oeuvre/{id}",
            method = RequestMethod.DELETE,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public CatalogueCms deleteOeuvre(@PathVariable(name = "id") Long id, @RequestBody CatalogueCms catalogueRdo){
        CatalogueCms deletedOeuvre = catalogueRdoDao.findOne(id);
        deletedOeuvre.setDateSortie(new Date());
        deletedOeuvre.setTypeSortie("Manuelle");
        deletedOeuvre.  setRaisonSortie(catalogueRdo.getRaisonSortie());

        return catalogueRdoDao.saveAndFlush(deletedOeuvre);
    }

    @RequestMapping(
            value = "catalogue/oeuvre",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity ajouterOeuvreFromMipsa(@RequestBody CatalogueCms catalogueCms) {

        try {

        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }


}
