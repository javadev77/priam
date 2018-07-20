package fr.sacem.priam.model.dao.jpa;

import fr.sacem.priam.model.dao.AbstractDaoTest;
import fr.sacem.priam.model.domain.saref.SareftrRion;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by benmerzoukah on 09/05/2017.
 */

public class SareftrRionDaoTest extends AbstractDaoTest {
    
    @Autowired
    private SareftrRionDao sareftrRionDao;
    
    
    @Test
    public void should_return_all_rions() {
        List<SareftrRion> all = sareftrRionDao.findAll();
    
        assertThat(all).isNotNull().isNotEmpty();
        assertThat(all).extracting("rion").contains(619, 600, 629, 630, 639);
    }
    
    @Test
    public void should_return_rions_after_639() {
        List<SareftrRion> all = sareftrRionDao.findAfterRion(639);
    
        assertThat(all).isNotNull().isNotEmpty();
        assertThat(all).extracting("rion").doesNotContain(637, 638);
    }
    
    @Test
    public void should_return_all_rions_after_current_date() {
        List<SareftrRion> all = sareftrRionDao.findAllByDateRglmtAfterCurrentDate();

        Date currentDate = new Date();

        assertThat(all).isNotNull().isNotEmpty();
        all.forEach(s -> assertThat(s.getDatrglmt()).isAfterOrEqualsTo(currentDate));
    }
    
    
}
