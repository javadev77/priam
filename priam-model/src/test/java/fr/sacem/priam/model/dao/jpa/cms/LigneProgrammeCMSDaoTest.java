package fr.sacem.priam.model.dao.jpa.cms;

import ch.vorburger.mariadb4j.springframework.MariaDB4jSpringService;
import fr.sacem.priam.model.dao.AbstractDaoTest;
import fr.sacem.priam.model.dao.jpa.FichierDao;
import fr.sacem.priam.model.domain.Fichier;
import fr.sacem.priam.model.domain.LignePreprep;
import fr.sacem.priam.model.domain.Status;
import fr.sacem.priam.model.domain.cms.LigneProgrammeCMS;
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
        LigneProgrammeCMSDaoTest.class})
@Transactional
public class LigneProgrammeCMSDaoTest extends AbstractDaoTest {

    @Autowired
    LigneProgrammeCMSDao ligneProgrammeCMSDao;

    @Autowired
    FichierDao fichierDao;

    MariaDB4jSpringService mariaDB4jSpringService;

    private static final String NUM_PROG = "180007";
    private static final Long IDE12 = 2000163011L;
    private static final String TITRE = "Titre1";
    private static final boolean AJOUT = false;

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
        mariaDB4jSpringService.getDB().source("fr.sacem.priam.model.dao.jpa.cms/ligneProgrammeCMSDao.sql");
    }

    @Test
    @Transactional
    public void deleteAllByFichierId(){
        /*List<Fichier> fichiers = fichierDao.findAll();*/
        List<Fichier> fichiers = fichierDao.findFichiersByIdProgramme(NUM_PROG, Status.AFFECTE);
        long fileId = fichiers.get(0).getId();
        ligneProgrammeCMSDao.deleteAllByFichierId(fileId);
        List<LigneProgrammeCMS>  all = ligneProgrammeCMSDao.findAll();
        assertThat(all).isNotNull().isNotEmpty();
    }

    @Test
    public void findLigneProgrammeByCriteria(){
        Page<SelectionCMSDto> ligneProgrammeByCriteria =
                ligneProgrammeCMSDao.findLigneProgrammeByCriteria(NUM_PROG, IDE12, null, null,null, pageable);
        assertThat(ligneProgrammeByCriteria).isNotNull();
    }

    @Test
    public void findLigneProgrammeSelectionnesForFelix(){
        List<LignePreprep> lignePreprepList = ligneProgrammeCMSDao.findLigneProgrammeSelectionnesForFelix(NUM_PROG);
        assertThat(lignePreprepList.size()).isNotNull().isEqualTo(1);
    }

    @Test
    public void findOeuvreManuelByIde12() {
        Long IDE12_Manuel = 2018290111L;
        LigneProgrammeCMS ligneProgrammeCMSManuel = ligneProgrammeCMSDao.findOeuvreManuelByIde12(NUM_PROG, IDE12_Manuel);
        assertThat(ligneProgrammeCMSManuel).isNotNull();
    }

    @Test
    public void findOeuvreCorrigeByIde12() {
        Long IDE12_Corrige = 2018300111L;
        LigneProgrammeCMS ligneProgrammeCMSCorrige = ligneProgrammeCMSDao.findOeuvreACorrigeByIde12(NUM_PROG, IDE12_Corrige);
        assertThat(ligneProgrammeCMSCorrige).isNotNull();
    }

}
