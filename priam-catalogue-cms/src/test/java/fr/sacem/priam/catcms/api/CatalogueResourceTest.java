package fr.sacem.priam.catcms.api;

import fr.sacem.priam.catcms.config.RestResourceTest;
import fr.sacem.priam.model.dao.jpa.catcms.CatalogueRdoDao;
import fr.sacem.priam.model.domain.catcms.CatalogueRdo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by benmerzoukah on 04/05/2018.
 */
public class CatalogueResourceTest extends RestResourceTest {

    @Autowired
    CatalogueRdoDao catalogueRdoDao;

//    private MariaDB4jSpringService mariaDB4jSpringService;


//    @Override
//    public void beforeTestClass(TestContext testContext) throws Exception {
//       mariaDB4jSpringService = testContext.getApplicationContext().getBean("dbServiceBean", MariaDB4jSpringService.class);
//       mariaDB4jSpringService.getDB().source("priam_app_PRIAM_CATCMS_RDO.sql");
//
//    }


    @Test
    public void findAllByCriteria() throws Exception {

        List<CatalogueRdo> all = catalogueRdoDao.findAll();

        mockMvc.perform(
                post("/app/rest/catalogue/search")
                        .contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numberOfElements", is(all.size())))
                .andExpect(jsonPath("$.content[0].ide12", is(all.get(0).getIde12().intValue())));

    }

}