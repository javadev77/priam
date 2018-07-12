package fr.sacem.priam.catcms.api;

import fr.sacem.priam.catcms.api.dto.CatalogueCritereRecherche;
import fr.sacem.priam.model.dao.jpa.catcms.CatalogueCmsDao;
import fr.sacem.priam.model.dao.jpa.catcms.ParticipantsCatcmsDao;
import fr.sacem.priam.model.domain.catcms.CatalogueCms;
import fr.sacem.priam.model.domain.catcms.ParticipantsCatcms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static fr.sacem.priam.common.util.FileUtils.CATALOGUE_OCTAV_TYPE_CMS_ANF;
import static fr.sacem.priam.common.util.FileUtils.CATALOGUE_OCTAV_TYPE_CMS_FR;

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
        deletedOeuvre.setRaisonSortie(catalogueRdo.getRaisonSortie());
        return catalogueRdoDao.saveAndFlush(deletedOeuvre);
    }

    @RequestMapping(
            value = "catalogue/oeuvre",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity ajouterOeuvreFromMipsa(@RequestBody CatalogueCms catalogueCms) {

        catalogueCms.setDateEntree(new Date());
        catalogueCms.setTypUtilGen("PHONOFR");
        catalogueRdoDao.save(catalogueCms);

        //Vasy je te laisse faire ça
        List<ParticipantsCatcms> participants = participantsFromCatalogueCms(catalogueCms);
        for (ParticipantsCatcms participant: participants) {
            participant.setIde12(catalogueCms.getIde12());
            participant.setTypeCMS(catalogueCms.getTypeCMS());
            participantsCatcmsDao.save(participant);
        }
        return ResponseEntity.ok().build();
    }

    private List<ParticipantsCatcms> participantsFromCatalogueCms(CatalogueCms catalogueCms) {
        List<ParticipantsCatcms> result = new ArrayList<>();
        String[] tabParticipants = catalogueCms.getParticipants().split(",");
        String[] tabRoles = catalogueCms.getRoles().split(",");
        if(tabParticipants.length == tabRoles.length){
            for (int i = 0; i < tabParticipants.length; i++) {
                ParticipantsCatcms participant = new ParticipantsCatcms();
                participant.setNomParticpant(tabParticipants[i]);
                participant.setRole(tabRoles[i]);
                result.add(participant);
            }
        }
        return result;
    }

    @RequestMapping(value = "catalogue/oeuvre/search",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public CatalogueCms findOeuvreExistanteCatalogue(@RequestBody CatalogueCms catalogueCms) {
        CatalogueCms catalogueCmsFound = new CatalogueCms();
        if(CATALOGUE_OCTAV_TYPE_CMS_ANF.equals(catalogueCms.getTypeCMS())){
            catalogueCmsFound = catalogueRdoDao.findOeuvreExistanteCatalogueByIde12AndTypeCMS(catalogueCms.getIde12(), CATALOGUE_OCTAV_TYPE_CMS_ANF);
        } else {
            catalogueCmsFound = catalogueRdoDao.findOeuvreExistanteCatalogueByIde12AndTypeCMS(catalogueCms.getIde12(), CATALOGUE_OCTAV_TYPE_CMS_FR);
        }
        if (catalogueCmsFound == null){
            return new CatalogueCms();
        }
        return catalogueCmsFound;
    }

    @RequestMapping(
            value = "catalogue/oeuvre/{id}",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public CatalogueCms renouvelerOeuvre(@PathVariable(name = "id") Long id){
        CatalogueCms oeuvreARenouvele = catalogueRdoDao.findOne(id);
        oeuvreARenouvele.setDateRenouvellement(new Date());
        return catalogueRdoDao.saveAndFlush(oeuvreARenouvele);
    }




}
