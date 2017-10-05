package fr.sacem.priam.model.dao.jpa;

import fr.sacem.priam.model.dao.JpaConfigurationTest;
import fr.sacem.priam.model.domain.saref.SareftrFamiltyputil;
import fr.sacem.priam.model.util.FamillePriam;
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
public class SareftrFamiltyputilDaoTest {
    
    @Autowired
    private SareftrFamiltyputilDao sareftrFamiltyputilDao;
    
    
    @Test
    public void should_return_all_famille() {
    
        List<SareftrFamiltyputil> all = sareftrFamiltyputilDao.findByFamilles(FamillePriam.getCodes());
    
        assertThat(all).isNotNull().isNotEmpty();
        assertThat(all).extracting("code").containsExactly("COPIEPRIV");
    }

}
