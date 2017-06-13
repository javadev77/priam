package fr.sacem.priam.model.fr.sacem.priam.model.dao.jpa;

import fr.sacem.priam.model.JpaConfigurationTest;
import fr.sacem.priam.model.dao.jpa.RionDao;
import fr.sacem.priam.model.domain.Rion;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by fandis on 13/06/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={JpaConfigurationTest.class})
public class RionDaoTest {

    @Autowired
    private RionDao rionDao;
    @Test
    public void should_return_all_Rion() {
        List<Rion> allRions = rionDao.getAllRions();
        assertThat(allRions).isNotNull().isNotEmpty();
       // assertThat(allRions).extracting("rion").containsExactly("COPIEPRIV", "FDSVAL", "CMS");
    }
}
