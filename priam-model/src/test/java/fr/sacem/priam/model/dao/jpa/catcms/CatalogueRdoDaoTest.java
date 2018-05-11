package fr.sacem.priam.model.dao.jpa.catcms;

import fr.sacem.priam.model.dao.AbstractDaoTest;
import fr.sacem.priam.model.domain.catcms.CatalogueRdo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

/**
 * Created by benmerzoukah on 04/05/2018.
 */
public class CatalogueRdoDaoTest extends AbstractDaoTest {

    @Autowired
    CatalogueRdoDao catalogueRdoDao;


    @Test
    public void findByCriteria() throws Exception {

        int nbOfElements = catalogueRdoDao.findAll().size();

        Page<CatalogueRdo> result = catalogueRdoDao.findByCriteria(new PageRequest(0, 5));

        assertThat(result).isNotNull().isNotEmpty();
        assertThat(result.getTotalElements()).isEqualTo(nbOfElements);
    }

}