package fr.sacem.priam.model.dao.jpa;

import ch.vorburger.mariadb4j.springframework.MariaDB4jSpringService;
import fr.sacem.priam.model.dao.AbstractDaoTest;
import fr.sacem.priam.model.domain.saref.SareftrRion;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by benmerzoukah on 09/05/2017.
 */
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@TestExecutionListeners(listeners = {
        DependencyInjectionTestExecutionListener.class,
        SareftrRionDaoTest.class})
@Transactional
public class SareftrRionDaoTest extends AbstractDaoTest {

    @Autowired
    private SareftrRionDao sareftrRionDao;

    MariaDB4jSpringService mariaDB4jSpringService;


//    @Before
//    public void setup() {
//        SareftrRion rion = new SareftrRion();
//        SareftrRion lastRion = sareftrRionDao.findAll(new Sort(Sort.Direction.DESC, "rion")).get(0);
//        rion.setRion(lastRion.getRion() + 1);
//        rion.setDatrglmt(new Date());
//        rion.setFiltre(new BigDecimal(0));
//
//        sareftrRionDao.saveAndFlush(rion);
//    }

    @Override
    public void beforeTestClass(TestContext testContext) throws Exception {
//        mariaDB4jSpringService = testContext.getApplicationContext().getBean("dbServiceBean", MariaDB4jSpringService.class);
//        mariaDB4jSpringService.getDB().source("sareftrRionDao.sql");
    }


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

        LocalDate localDateCurrent = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        assertThat(all).isNotNull().isNotEmpty();
        all.forEach(s -> {Date datrglmt = s.getDatrglmt();
        LocalDate datrglmtLD = datrglmt.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        assertThat(datrglmtLD.isEqual(localDateCurrent) || datrglmtLD.isAfter(localDateCurrent)).isTrue();
        });
    }


}
