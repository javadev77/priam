package fr.sacem.priam.model.dao.jpa;

import fr.sacem.priam.model.dao.JpaConfigurationTest;
import fr.sacem.priam.model.domain.saref.SareftjLibtyputil;
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
public class LibelleSareftrTyputilDaoTest {
    
    @Autowired
    private SareftjLibtyputilDao sareftjLibtyputilDao;
    
    
    @Test
    public void should_return_all_type_utilisation_FR() {
        List<SareftjLibtyputil> all = sareftjLibtyputilDao.findByLang("FR");
    
        assertThat(all).isNotNull().isNotEmpty();
        assertThat(all).extracting("code").containsExactly("CPRIVSONPH", "CPRIVAUDV", "CPRIVSONRD", "CPRIVAUDPL", "PRIME", "VALORIS", "ENCOURG");
    }
    
    @Test
    public void should_return_empty_when_null() {
        List<SareftjLibtyputil> all = sareftjLibtyputilDao.findByLang(null);
        
        assertThat(all).isNotNull().isEmpty();
    }

}
