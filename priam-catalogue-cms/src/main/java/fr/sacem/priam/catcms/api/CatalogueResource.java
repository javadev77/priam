package fr.sacem.priam.catcms.api;

import fr.sacem.priam.model.dao.jpa.catcms.CatalogueRdoDao;
import fr.sacem.priam.model.domain.catcms.CatalogueRdo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    public Page<CatalogueRdo> findByCriteria(Pageable pageable) {

        return catalogueRdoDao.findByCriteria(pageable);

    }
}
