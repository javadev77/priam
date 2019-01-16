package fr.sacem.priam.model.dao.jpa.fv;

import ch.vorburger.mariadb4j.springframework.MariaDB4jSpringService;
import fr.sacem.priam.model.dao.AbstractDaoTest;
import fr.sacem.priam.model.domain.dto.KeyValueDto;
import fr.sacem.priam.model.domain.dto.SelectionCMSDto;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@TestExecutionListeners(listeners = {
        DependencyInjectionTestExecutionListener.class,
        LigneProgrammeFVDaoTest.class})
@Transactional
public class LigneProgrammeFVDaoTest extends AbstractDaoTest {

    private static final String NUM_PROG = "190001";

    private static final Long IDE12 = 2240093411L;

    @Autowired
    LigneProgrammeFVDao ligneProgrammeFVDao;

    MariaDB4jSpringService mariaDB4jSpringService;

    private static final Pageable pageable = new Pageable() {

        @Override
        public int getPageNumber() {
            return 0;
        }

        @Override
        public int getPageSize() {
            return 5;
        }

        @Override
        public int getOffset() {
            return 0;
        }

        @Override
        public Sort getSort() {
            return null;
        }

        @Override
        public Pageable next() {
            return null;
        }

        @Override
        public Pageable previousOrFirst() {
            return null;
        }

        @Override
        public Pageable first() {
            return null;
        }

        @Override
        public boolean hasPrevious() {
            return false;
        }
    };

    @Override
    public void beforeTestClass(TestContext testContext) throws Exception {
        mariaDB4jSpringService = testContext.getApplicationContext().getBean("dbServiceBean", MariaDB4jSpringService.class);
        mariaDB4jSpringService.getDB().source("fr.sacem.priam.model.dao.jpa.fv/ligneProgrammeFVDao.sql");
    }

    @Test
    public void findTitresByProgramme(){
        List<KeyValueDto> titresByProgramme = ligneProgrammeFVDao.findTitresByProgramme("IMPRO JAZZ", NUM_PROG);
        assertThat(titresByProgramme).isNotNull();
    }

    @Test
    public void findIDE12sByProgramme(){
        List<KeyValueDto> ide12sByProgramme = ligneProgrammeFVDao.findIDE12sByProgramme(IDE12, NUM_PROG);
        assertThat(ide12sByProgramme).isNotNull();
    }

    @Test
    public void findLigneProgrammeByCriteria(){
        Page<SelectionCMSDto> ligneProgrammeByCriteria =
                ligneProgrammeFVDao.findLigneProgrammeByCriteria(NUM_PROG, IDE12, null, null,null, pageable);
        assertThat(ligneProgrammeByCriteria).isNotNull();
    }

    @Test
    public void compterOuvres(){
        List<Object> objects = ligneProgrammeFVDao.compterOuvres(NUM_PROG, 1);
        assertThat(objects).isNotNull();
    }
}
