package fr.sacem.priam.model.dao.jpa;

import fr.sacem.priam.model.dao.AbstractDaoTest;
import fr.sacem.priam.model.domain.CatalogueOctav;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;


public class CatalogueOctavDaoTest extends AbstractDaoTest {

    @Autowired
    private CatalogueOctavDao catalogueOctavDao;

    @Test
    public void findByIde12(){
        Long ide12 = 6286965011L;
        String typeCMS = "FR";
        CatalogueOctav oeuvre = catalogueOctavDao.findByIde12(ide12, typeCMS);
        assertThat(oeuvre).isNotNull();
    }
}
