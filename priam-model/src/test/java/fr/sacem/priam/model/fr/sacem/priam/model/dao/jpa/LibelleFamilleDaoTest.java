package fr.sacem.priam.model.fr.sacem.priam.model.dao.jpa;

import fr.sacem.priam.model.JpaConfigurationTest;
import fr.sacem.priam.model.dao.jpa.LibelleFamilleDao;
import fr.sacem.priam.model.domain.LibelleFamille;
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
public class LibelleFamilleDaoTest {
    
    @Autowired
    private LibelleFamilleDao libelleFamilleDao;
    
    
    @Test
    public void should_return_all_famille_FR() {
    
        List<LibelleFamille> all = libelleFamilleDao.findByLang("FR");
    
        assertThat(all).isNotNull().isNotEmpty().hasSize(3);
        assertThat(all).extracting("code").containsExactly("COPIEPRIV", "FDSVAL", "CMS");
    }
    
    @Test
    public void should_return_empty_famille_when_null() {
        
        List<LibelleFamille> all = libelleFamilleDao.findByLang(null);
        
        assertThat(all).isNotNull().isEmpty();
    }

}
