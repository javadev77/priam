package fr.sacem.priam.model.dao.jpa;

import fr.sacem.priam.model.dao.JpaConfigurationTest;
import fr.sacem.priam.model.domain.Rion;
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
public class RionDaoTest {
    
    @Autowired
    private RionDao rionDao;
    
    
    @Test
    public void should_return_all_rions() {
        List<Rion> all = rionDao.findAll();
    
        assertThat(all).isNotNull().isNotEmpty();
        assertThat(all).extracting("rion").contains(619, 600, 629, 630, 639);
    }
    
    @Test
    public void should_return_rions_after_639() {
        List<Rion> all = rionDao.findAfterRion(639);
    
        assertThat(all).isNotNull().isNotEmpty();
        assertThat(all).extracting("rion").doesNotContain(637, 638);
    }
    
    @Test
    public void should_return_all_rions_after_current_date() {
        List<Rion> all = rionDao.findAllByDateRglmtAfterCurrentDate();
        
        assertThat(all).isNotNull().isNotEmpty();
        assertThat(all).extracting("rion").contains(639);
    }
    
    
}
