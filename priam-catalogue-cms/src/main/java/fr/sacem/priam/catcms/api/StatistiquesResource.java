package fr.sacem.priam.catcms.api;

import fr.sacem.priam.model.dao.jpa.catcms.StatistiquesCatcmsDao;
import fr.sacem.priam.model.domain.catcms.StatistiqueCatcms;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by embouazzar on 01/10/2018.
 */
@RestController
@RequestMapping("/app/rest/statistiques")
public class StatistiquesResource {

    @Autowired
    StatistiquesCatcmsDao statistiquesCatcmsDao;

    @RequestMapping(
            value = "/search",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<StatistiqueCatcms> findAll( Pageable pageable) {
        return statistiquesCatcmsDao.findAll(pageable);
    }
}
