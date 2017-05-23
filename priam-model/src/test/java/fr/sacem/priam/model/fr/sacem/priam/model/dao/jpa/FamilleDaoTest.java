package fr.sacem.priam.model.fr.sacem.priam.model.dao.jpa;

import fr.sacem.priam.model.JpaConfigurationTest;
import fr.sacem.priam.model.dao.jpa.FamilleDao;
import fr.sacem.priam.model.domain.Famille;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by benmerzoukah on 09/05/2017.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={JpaConfigurationTest.class})
public class FamilleDaoTest {
    
    @Autowired
    private FamilleDao familleDao;
    
    
    @Test
    public void should_return_all_famille() {
    
        List<Famille> all = familleDao.findAll();
    
        assertThat(all).isNotNull().isNotEmpty();
        assertThat(all).extracting("code").containsExactly("COPIEPRIV", "FDSVAL", "CMS");
    }

}
