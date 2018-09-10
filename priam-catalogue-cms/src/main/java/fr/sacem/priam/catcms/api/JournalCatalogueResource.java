package fr.sacem.priam.catcms.api;

import fr.sacem.priam.catcms.api.dto.JournalCatcmsCritereRecherche;
import fr.sacem.priam.model.dao.jpa.catcms.JournalCatcmsDao;
import fr.sacem.priam.model.domain.catcms.JournalCatcms;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by embouazzar on 28/08/2018.
 */
@RestController
@RequestMapping("/app/rest/")
public class JournalCatalogueResource {

    private static Logger logger = LoggerFactory.getLogger(JournalCatalogueResource.class);

    @Autowired
    private JournalCatcmsDao journalCatcmsDao;

    @RequestMapping(value = "journalCatcms/search",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<JournalCatcms> rechercheEvenements(Pageable pageable) {
        return journalCatcmsDao.findAll(pageable);
    }

    @RequestMapping(value = "journalCatcms/search",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<JournalCatcms> rechercheEvenements(@RequestBody JournalCatcmsCritereRecherche critereRecherche, Pageable pageable) {
        return journalCatcmsDao.findByCriteria(critereRecherche.getTypeCMS(), critereRecherche.getIde12(),
                critereRecherche.getTypeEvenement(), critereRecherche.getPeriodeDebutEvenement(), critereRecherche.getPeriodeFinEvenement(), pageable);
    }


}
