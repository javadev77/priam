package fr.sacem.priam.model.dao.jpa;

import fr.sacem.priam.model.dao.JpaConfigurationTest;
import fr.sacem.priam.model.domain.CatalogueOctav;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={JpaConfigurationTest.class})
public class CatalogueOctavDaoTest {

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
