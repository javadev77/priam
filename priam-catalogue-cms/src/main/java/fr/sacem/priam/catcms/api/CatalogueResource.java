package fr.sacem.priam.catcms.api;

import fr.sacem.priam.catcms.api.dto.CatalogueCritereRecherche;
import fr.sacem.priam.catcms.api.dto.KeyValueDtoCatcms;
import fr.sacem.priam.catcms.journal.annotation.LogCatalogueAjout;
import fr.sacem.priam.catcms.journal.annotation.LogCatalogueSuppression;
import fr.sacem.priam.catcms.journal.annotation.TypeLog;
import fr.sacem.priam.model.dao.jpa.catcms.CatalogueCmsDao;
import fr.sacem.priam.model.dao.jpa.catcms.ParticipantsCatcmsDao;
import fr.sacem.priam.model.domain.catcms.CatalogueCms;
import fr.sacem.priam.model.domain.catcms.ParticipantsCatcms;
import fr.sacem.priam.model.domain.dto.KeyValueDto;
import fr.sacem.priam.security.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.*;

import static fr.sacem.priam.common.util.FileUtils.CATALOGUE_TYPE_CMS_ANF;
import static fr.sacem.priam.common.util.FileUtils.CATALOGUE_TYPE_CMS_FR;

@RestController
@RequestMapping("/app/rest/")
public class CatalogueResource {

    public static final String TYPE_INSCRIPTION = "TYPE_INSCRIPTION";
    public static final String TYPE_UTILISATION = "TYPE_UTILISATION";
    public static final String PHONOFR = "PHONOFR";

    @Autowired
    CatalogueCmsDao catalogueRdoDao;

    @Autowired
    ParticipantsCatcmsDao  participantsCatcmsDao;

    @Autowired
    EntityManagerFactory emf;


    @RequestMapping(
            value = "catalogue/search",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<CatalogueCms> findByCriteria(@RequestBody CatalogueCritereRecherche catalogueCritereRecherche, Pageable pageable) {

        Page<CatalogueCms> datas;
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
                    catalogueCritereRecherche.getPeriodeSortieDateFin(),
                    catalogueCritereRecherche.getTypeInscription(),
                    catalogueCritereRecherche.getTypeUtilisation(), pageable);
        } else {


            if(catalogueCritereRecherche.getPeriodeEntreeDateFin()== null){
                catalogueCritereRecherche.setPeriodeSortieDateFin(new Date());
                catalogueCritereRecherche.setPeriodeEntreeDateFin(new Date());
            }

            datas = catalogueRdoDao.findByCriteriaWithoutNonEligible(catalogueCritereRecherche.getTypeCMS(),
                    catalogueCritereRecherche.getIde12(),
                    catalogueCritereRecherche.getTitre(),
                    catalogueCritereRecherche.getParticipant(),
                    catalogueCritereRecherche.getPeriodeEntreeDateDebut(),
                    catalogueCritereRecherche.getPeriodeEntreeDateFin(),
                    catalogueCritereRecherche.getPeriodeRenouvellementDateDebut(),
                    catalogueCritereRecherche.getPeriodeRenouvellementDateFin(),
                    catalogueCritereRecherche.getPeriodeSortieDateFin(),
                    catalogueCritereRecherche.getTypeInscription(),
                    catalogueCritereRecherche.getTypeUtilisation(), pageable);

        }

        /*EntityManager em = emf.createEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();

        CriteriaQuery<CatalogueCms> query = builder.createQuery(CatalogueCms.class);
        Root<CatalogueCms> root = query.from(CatalogueCms.class);


        Predicate typeCMSPredicate = builder.equal(root.get("typeCMS"), catalogueCritereRecherche.getTypeCMS());
        Predicate allCriteriaPredicate = builder.and(typeCMSPredicate);

        if(catalogueCritereRecherche.isDisplayOeuvreNonEligible()) {

            if(catalogueCritereRecherche.getPeriodeSortieDateDebut() != null) {
                Predicate periodeSortieDateDebutPredicate = builder.greaterThanOrEqualTo(root.get("dateSortie"), catalogueCritereRecherche.getPeriodeSortieDateDebut());
                allCriteriaPredicate = builder.and(periodeSortieDateDebutPredicate, allCriteriaPredicate);
            }

            if(catalogueCritereRecherche.getPeriodeSortieDateFin() != null) {
                Predicate periodeSortieDateFinPredicate = builder.lessThanOrEqualTo(root.get("dateSortie"), catalogueCritereRecherche.getPeriodeSortieDateFin());
                allCriteriaPredicate = builder.and(periodeSortieDateFinPredicate, allCriteriaPredicate);
            }
        } else {
            if(catalogueCritereRecherche.getPeriodeEntreeDateFin()== null ||
                    catalogueCritereRecherche.getPeriodeSortieDateFin() == null){
                catalogueCritereRecherche.setPeriodeEntreeDateFin(new Date());
                catalogueCritereRecherche.setPeriodeSortieDateFin(new Date());
            }

            Predicate periodeSortieDateFinPredicate = builder.or(builder.isNull(root.get("dateSortie")),
                    builder.greaterThanOrEqualTo(root.get("dateSortie"),
                            catalogueCritereRecherche.getPeriodeSortieDateFin()));
            allCriteriaPredicate = builder.and(periodeSortieDateFinPredicate, allCriteriaPredicate);
        }



        //Predicate titrePredicate = null;

        if(catalogueCritereRecherche.getIde12() != null) {
            Predicate ide12Predicate = builder.like(builder.function("CONCAT", String.class, root.get("ide12"), builder.literal("")),
                    "%" + catalogueCritereRecherche.getIde12() + "%");
            allCriteriaPredicate = builder.and(ide12Predicate, allCriteriaPredicate);
        }

        if(catalogueCritereRecherche.getTitre() != null) {
            Predicate titrePredicate = builder.like(root.get("titre"), "%"+catalogueCritereRecherche.getTitre() + "%");
            allCriteriaPredicate = builder.and(titrePredicate, allCriteriaPredicate);
        }


        if(catalogueCritereRecherche.getParticipant() != null){
            Predicate participantPredicate = builder.like(root.get("participants"),"%" + catalogueCritereRecherche.getParticipant() + "%" );
            allCriteriaPredicate = builder.and(participantPredicate, allCriteriaPredicate);
        }

        if(catalogueCritereRecherche.getPeriodeEntreeDateDebut() != null) {
            Predicate periodeEntreeDateDebutPredicate = builder.greaterThanOrEqualTo(root.get("dateEntree"), catalogueCritereRecherche.getPeriodeEntreeDateDebut());
            allCriteriaPredicate = builder.and(periodeEntreeDateDebutPredicate, allCriteriaPredicate);
        }

        if(catalogueCritereRecherche.getPeriodeEntreeDateFin() != null) {
            Predicate periodeEntreeDateFinPredicate = builder.lessThanOrEqualTo(root.get("dateEntree"), catalogueCritereRecherche.getPeriodeEntreeDateFin());
            allCriteriaPredicate = builder.and(periodeEntreeDateFinPredicate, allCriteriaPredicate);
        }

        if(catalogueCritereRecherche.getPeriodeRenouvellementDateDebut() != null){
            Predicate periodeRenouvellementDateDebutPredicate = builder.greaterThanOrEqualTo(root.get("dateRenouvellement"), catalogueCritereRecherche.getPeriodeRenouvellementDateDebut());
            allCriteriaPredicate = builder.and(periodeRenouvellementDateDebutPredicate, allCriteriaPredicate);
        }

        if(catalogueCritereRecherche.getPeriodeRenouvellementDateFin() != null) {
            Predicate periodeRenouvellementDateFinPredicate = builder.lessThanOrEqualTo(root.get("dateRenouvellement"), catalogueCritereRecherche.getPeriodeRenouvellementDateFin());
            allCriteriaPredicate = builder.and(periodeRenouvellementDateFinPredicate, allCriteriaPredicate);
        }

        if(catalogueCritereRecherche.getTypeInscription() !=null){
            Predicate typeInscriptionPredicate = builder.equal(root.get("typeInscription"), catalogueCritereRecherche.getTypeInscription());
            allCriteriaPredicate = builder.and(typeInscriptionPredicate, allCriteriaPredicate);
        }

        if(catalogueCritereRecherche.getTypeUtilisation() !=null){
            Predicate typeUtilisationPredicate = builder.equal(root.get("typUtilGen"), catalogueCritereRecherche.getTypeUtilisation());
            allCriteriaPredicate = builder.and(typeUtilisationPredicate, allCriteriaPredicate);
        }

        query.where(allCriteriaPredicate);
        TypedQuery<CatalogueCms> finalQuery = em.createQuery(query.select(root));

        finalQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        finalQuery.setMaxResults(pageable.getPageSize());


        List<CatalogueCms> resultList = finalQuery.getResultList();
        int totalRows = resultList.size();

        datas = new PageImpl<>(resultList, pageable, totalRows);*/

        return datas;
    }

    @RequestMapping(
            value = "catalogue/oeuvre/{id}",
            method = RequestMethod.DELETE,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @LogCatalogueSuppression(event = TypeLog.SUPPRESSION_OEUVRE)
    public CatalogueCms deleteOeuvre(@PathVariable(name = "id") Long id, @RequestBody CatalogueCms catalogueRdo, UserDTO userDTO){
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
    @Transactional(value="transactionManager")
    @LogCatalogueAjout(event = TypeLog.AJOUT_OEUVRE)
    public ResponseEntity ajouterOeuvreFromMipsa(@RequestBody CatalogueCms catalogueCms, UserDTO userDTO) {
        catalogueCms.setDateEntree(new Date());
        catalogueCms.setTypUtilGen(PHONOFR);

        catalogueRdoDao.save(catalogueCms);
        List<ParticipantsCatcms> participants = participantsFromCatalogueCms(catalogueCms);
        for (ParticipantsCatcms participant : participants) {
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
        CatalogueCms catalogueCmsFound;
        if(CATALOGUE_TYPE_CMS_ANF.equals(catalogueCms.getTypeCMS())){
            catalogueCmsFound = catalogueRdoDao.findOeuvreExistanteCatalogueByIde12AndTypeCMS(catalogueCms.getIde12(), CATALOGUE_TYPE_CMS_ANF);
        } else {
            catalogueCmsFound = catalogueRdoDao.findOeuvreExistanteCatalogueByIde12AndTypeCMS(catalogueCms.getIde12(), CATALOGUE_TYPE_CMS_FR);
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
    @LogCatalogueAjout(event = TypeLog.RENOUVELLEMENT_OEUVRE)
    public CatalogueCms renouvelerOeuvre(@PathVariable(name = "id") Long id, UserDTO userDTO){
        CatalogueCms oeuvreARenouvele = catalogueRdoDao.findOne(id);
        oeuvreARenouvele.setDateRenouvellement(new Date());

        return catalogueRdoDao.saveAndFlush(oeuvreARenouvele);
    }

    @RequestMapping(value = "catalogue/titre",
            method = RequestMethod.GET,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<KeyValueDto> getTitresByProgramme(@RequestParam(value = "q") String titre, @RequestParam(value = "typeCMS") String typeCMS) {
        return catalogueRdoDao.findTitresByTypeCMS(titre, typeCMS);
    }

    @RequestMapping(value = "catalogue/compteur",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, List<KeyValueDtoCatcms>> compteurByCriteria(@RequestBody CatalogueCritereRecherche catalogueCritereRecherche) {
        Map<String, List<KeyValueDtoCatcms>> result = new HashMap<>();


        result.put(TYPE_INSCRIPTION, getMetricTypeInscription(catalogueCritereRecherche));
        result.put(TYPE_UTILISATION, getMetricTypeUtilisation(catalogueCritereRecherche));

        return result;
    }

    private List<KeyValueDtoCatcms> getMetricTypeInscription(CatalogueCritereRecherche catalogueCritereRecherche){
        List<KeyValueDtoCatcms> resultTypeInscription = new ArrayList<>();

        List<Object> nombreTypeInscription;

        if(catalogueCritereRecherche.isDisplayOeuvreNonEligible()){
            nombreTypeInscription = catalogueRdoDao.compterNombreTypeInscriptionInclusNonEligible(catalogueCritereRecherche.getTypeCMS(),
                    catalogueCritereRecherche.getIde12(),
                    catalogueCritereRecherche.getTitre(),
                    catalogueCritereRecherche.getParticipant(),
                    catalogueCritereRecherche.getPeriodeEntreeDateDebut(),
                    catalogueCritereRecherche.getPeriodeEntreeDateFin(),
                    catalogueCritereRecherche.getPeriodeRenouvellementDateDebut(),
                    catalogueCritereRecherche.getPeriodeRenouvellementDateFin(),
                    catalogueCritereRecherche.getPeriodeSortieDateDebut(),
                    catalogueCritereRecherche.getPeriodeSortieDateFin());
        } else {

            if(!catalogueCritereRecherche.isDisplayOeuvreNonEligible() && catalogueCritereRecherche.getPeriodeEntreeDateFin()== null) {
                catalogueCritereRecherche.setPeriodeEntreeDateFin(new Date());
                catalogueCritereRecherche.setPeriodeSortieDateFin(new Date());
            }

            nombreTypeInscription = catalogueRdoDao.compterNombreTypeInscriptionExclusNonEligible(catalogueCritereRecherche.getTypeCMS(),
                    catalogueCritereRecherche.getIde12(),
                    catalogueCritereRecherche.getTitre(),
                    catalogueCritereRecherche.getParticipant(),
                    catalogueCritereRecherche.getPeriodeEntreeDateDebut(),
                    catalogueCritereRecherche.getPeriodeEntreeDateFin(),
                    catalogueCritereRecherche.getPeriodeRenouvellementDateDebut(),
                    catalogueCritereRecherche.getPeriodeRenouvellementDateFin(),
                    catalogueCritereRecherche.getPeriodeSortieDateFin());
        }
        for (Object typeInscription: nombreTypeInscription) {
            Object[] typeInscriptionObjects = (Object[]) typeInscription;
            KeyValueDtoCatcms keyValueDtoCatcms = new KeyValueDtoCatcms((String) typeInscriptionObjects[0],(Long) typeInscriptionObjects[1]);
            resultTypeInscription.add(keyValueDtoCatcms);
        }

        return resultTypeInscription;
    }


    private List<KeyValueDtoCatcms> getMetricTypeUtilisation(CatalogueCritereRecherche catalogueCritereRecherche) {

        List<KeyValueDtoCatcms> resultTypeUtilisation = new ArrayList<>();

        List<Object> nombreTypeInscription;
        if(catalogueCritereRecherche.isDisplayOeuvreNonEligible()){
            nombreTypeInscription = catalogueRdoDao.compterNombreTypeUtilisationInclusNonEligible(catalogueCritereRecherche.getTypeCMS(),
                    catalogueCritereRecherche.getIde12(),
                    catalogueCritereRecherche.getTitre(),
                    catalogueCritereRecherche.getParticipant(),
                    catalogueCritereRecherche.getPeriodeEntreeDateDebut(),
                    catalogueCritereRecherche.getPeriodeEntreeDateFin(),
                    catalogueCritereRecherche.getPeriodeRenouvellementDateDebut(),
                    catalogueCritereRecherche.getPeriodeRenouvellementDateFin(),
                    catalogueCritereRecherche.getPeriodeSortieDateDebut(),
                    catalogueCritereRecherche.getPeriodeSortieDateFin());
        } else {
            nombreTypeInscription = catalogueRdoDao.compterNombreTypeUtilisationExclusNonEligible(catalogueCritereRecherche.getTypeCMS(),
                    catalogueCritereRecherche.getIde12(),
                    catalogueCritereRecherche.getTitre(),
                    catalogueCritereRecherche.getParticipant(),
                    catalogueCritereRecherche.getPeriodeEntreeDateDebut(),
                    catalogueCritereRecherche.getPeriodeEntreeDateFin(),
                    catalogueCritereRecherche.getPeriodeRenouvellementDateDebut(),
                    catalogueCritereRecherche.getPeriodeRenouvellementDateFin(),
                    catalogueCritereRecherche.getPeriodeSortieDateFin());
        }


        for (Object typeInscription : nombreTypeInscription) {
            Object[] typeUtilisationObjects = (Object[]) typeInscription;
            KeyValueDtoCatcms keyValueDtoCatcms = new KeyValueDtoCatcms((String) typeUtilisationObjects[0], (Long) typeUtilisationObjects[1]);
            resultTypeUtilisation.add(keyValueDtoCatcms);
        }

        return resultTypeUtilisation;
    }


}
