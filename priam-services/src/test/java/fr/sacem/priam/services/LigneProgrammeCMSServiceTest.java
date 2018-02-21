package fr.sacem.priam.services;

import ch.vorburger.mariadb4j.springframework.MariaDB4jSpringService;
import com.google.common.collect.Iterables;
import fr.sacem.priam.model.dao.JpaConfigurationTest;
import fr.sacem.priam.model.dao.jpa.FichierDao;
import fr.sacem.priam.model.dao.jpa.LigneProgrammeCMSDaoForTest;
import fr.sacem.priam.model.dao.jpa.cms.LigneProgrammeCMSDao;

import fr.sacem.priam.model.dao.jpa.cp.ProgrammeDao;
import fr.sacem.priam.model.domain.Programme;
import fr.sacem.priam.model.domain.cms.LigneProgrammeCMS;
import fr.sacem.priam.model.domain.criteria.LigneProgrammeCriteria;
import fr.sacem.priam.model.domain.dto.KeyValueDto;
import fr.sacem.priam.model.domain.dto.SelectionCMSDto;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AbstractTestExecutionListener;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.hamcrest.CoreMatchers.is;
import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners(listeners = {
        DependencyInjectionTestExecutionListener.class,
        LigneProgrammeCMSServiceTest.class})
@ContextConfiguration(classes={JpaConfigurationTest.class, ConfigurationTest.class})
@ActiveProfiles("test")
@Transactional
public class LigneProgrammeCMSServiceTest extends AbstractTestExecutionListener {

    private static final String NUM_PROG = "180006";
    private static final Long IDE12 = 2000163011L;
    public static final String CDE_UTIL = "XXX";
    public static final String CDE_CISAC_058 = "058";
    private static final String STRING_IDE12 = "2000163011";
    private static final String TITRE = "Titre2";
    private static final String STATUS = "AFFECTE";
    public static final String MANUEL = "MANUEL";
    public static final String AUTOMATIQUE = "AUTOMATIQUE";
    public static final String CORRIGE = "CORRIGE";
    private static final String SOMME = "SOMME";
    private static final String TYPE_CMS_ANF = "ANF";


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

    @Autowired
    LigneProgrammeCMSServiceImpl ligneProgrammeCMSService;

    @Autowired
    LigneProgrammeCMSDaoForTest ligneProgrammeCMSDao;

    @Autowired
    FichierDao fichierDao;

    @Autowired
    ProgrammeDao programmeDao;

    MariaDB4jSpringService mariaDB4jSpringService;


    @Override
    public void afterTestClass(TestContext testContext) {
      // mariaDB4jSpringService = testContext.getApplicationContext().getBean("dbServiceBean", MariaDB4jSpringService.class);

    }

    @Override
    public void beforeTestClass(TestContext testContext) throws Exception {
        mariaDB4jSpringService = testContext.getApplicationContext().getBean("dbServiceBean", MariaDB4jSpringService.class);
        mariaDB4jSpringService.getDB().source("fr.sacem.priam.services/ligneProgrammeCMSServiceTest.sql");

    }

    @Test
    @Transactional
    public void getListIDE12ByProgramme() {
        List<KeyValueDto> listIDE12ByProgramme = ligneProgrammeCMSService.getListIDE12ByProgramme(IDE12, NUM_PROG);
        assertThat(listIDE12ByProgramme).isNotNull().isNotEmpty();
        assertThat(listIDE12ByProgramme.stream().anyMatch(keyValue -> keyValue.getCode().toString().contains(IDE12.toString()))).isEqualTo(true);
    }

    @Test
    @Transactional
    public void getTitresByProgramme() {
        List<KeyValueDto> titresByProgramme = ligneProgrammeCMSService.getTitresByProgramme(TITRE, NUM_PROG);
        assertThat(titresByProgramme).isNotNull().isNotNull();
        assertThat(titresByProgramme.stream().anyMatch(keyValueDto -> keyValueDto.getValue().toString().contains(TITRE))).isEqualTo(true);
    }

    @Test
    @Transactional
    public void findLigneProgrammeByCriteria() {
        LigneProgrammeCriteria criteria = new LigneProgrammeCriteria();
        criteria.setNumProg(NUM_PROG);
        criteria.setIde12(Long.valueOf(IDE12));
        Page<SelectionCMSDto> ligneProgrammeByCriteria = ligneProgrammeCMSService.findLigneProgrammeByCriteria(criteria, pageable);
        assertThat(ligneProgrammeByCriteria).isNotNull();
    }

    @Test
    @Transactional
    public void selectLigneProgramme() {
        Set<Map<String, String>> listIDE12 = new HashSet<>();
        Map<String, String> list = new HashMap<>();
        list.put("ide12", STRING_IDE12);
        listIDE12.add(list);

        ligneProgrammeCMSService.selectLigneProgramme(NUM_PROG, listIDE12);
        List<LigneProgrammeCMS> ligneProgrammeCMS = ligneProgrammeCMSDao.findLigneProgrammeByNumProg(NUM_PROG);
        Iterable<LigneProgrammeCMS> selectedLigneCMS = Iterables.filter(ligneProgrammeCMS, lp -> lp.isSelectionEnCours());
        assertThat(Iterables.size(selectedLigneCMS)).isEqualTo(5);
    }

    @Test
    @Transactional
    public void supprimerLigneProgramme() {
        LigneProgrammeCMS oeuvreToDelete = new LigneProgrammeCMS();
        oeuvreToDelete.setNumProg(NUM_PROG);
        oeuvreToDelete.setIde12(3000163011L);

        SelectionCMSDto selectionCMSDto = new SelectionCMSDto();
        selectionCMSDto.setAjout("MANUEL");

        ligneProgrammeCMSService.ajouterOeuvreManuel(oeuvreToDelete);
        ligneProgrammeCMSService.supprimerLigneProgramme(NUM_PROG, oeuvreToDelete.getIde12(), selectionCMSDto);

        LigneProgrammeCMS ligneProgrammeCMS = ligneProgrammeCMSDao.findOeuvreManuelByIde12(NUM_PROG, oeuvreToDelete.getIde12());
        assertThat(ligneProgrammeCMS).isNull();
    }

    @Test
    public void deselectLigneProgramme() {
        Set<Map<String, String>> listIDE12 = new HashSet<>();
        Map<String, String> list = new HashMap<>();
        list.put("ide12", STRING_IDE12);
        listIDE12.add(list);

        ligneProgrammeCMSService.deselectLigneProgramme(NUM_PROG, listIDE12);

        List<LigneProgrammeCMS> ligneProgrammeCMS = ligneProgrammeCMSDao.findLigneProgrammeByNumProg(NUM_PROG);
        Iterable<LigneProgrammeCMS> unselectedLigneProgrammeCMS = Iterables.filter(ligneProgrammeCMS, lp -> !lp.isSelectionEnCours());

        assertThat(Iterables.size(unselectedLigneProgrammeCMS)).isEqualTo(1);
        assertThat(unselectedLigneProgrammeCMS).extracting("ide12").contains(Long.valueOf(IDE12));
    }

    @Test
    @Transactional
    public void enregistrerEdition() {
        ligneProgrammeCMSDao.updateSelectionTemporaire(NUM_PROG, true);
        List<LigneProgrammeCMS> before = ligneProgrammeCMSDao.findLigneProgrammeByNumProg(NUM_PROG);
        Iterable<LigneProgrammeCMS> filtredBefore = Iterables.filter(before, ligneProgrammeCMS -> ligneProgrammeCMS.isSelectionEnCours());

        ligneProgrammeCMSService.enregistrerEdition(NUM_PROG);

        List<LigneProgrammeCMS> after = ligneProgrammeCMSDao.findLigneProgrammeByNumProg(NUM_PROG);
        Iterable<LigneProgrammeCMS> filtredAfter = Iterables.filter(after, ligneProgrammeCMS -> ligneProgrammeCMS.isSelection());

        assertThat(Iterables.size(filtredAfter)).isEqualTo(Iterables.size(filtredBefore));
    }

    @Test
    @Transactional
    public void annulerEdition() {
        List<LigneProgrammeCMS> before = ligneProgrammeCMSDao.findLigneProgrammeByNumProg(NUM_PROG);
        Iterable<LigneProgrammeCMS> filtredBefore = Iterables.filter(before, ligneProgrammeCMS -> ligneProgrammeCMS.isSelectionEnCours());

        ligneProgrammeCMSDao.updateSelection(NUM_PROG, true);


        ligneProgrammeCMSService.annulerEdition(NUM_PROG);

        List<LigneProgrammeCMS> after = ligneProgrammeCMSDao.findLigneProgrammeByNumProg(NUM_PROG);
        Iterable<LigneProgrammeCMS> filtredAfter = Iterables.filter(after, ligneProgrammeCMS -> ligneProgrammeCMS.isSelectionEnCours());

        assertThat(Iterables.size(filtredAfter)).isEqualTo(5);
    }

    @Test
    @Transactional
    public void annulerSelection() {
        List<LigneProgrammeCMS> before = ligneProgrammeCMSDao.findLigneProgrammeByNumProg(NUM_PROG);
        Iterable<LigneProgrammeCMS> filtredBefore = Iterables.filter(before, ligneProgrammeCMS -> ligneProgrammeCMS.isSelectionEnCours());
        ligneProgrammeCMSDao.updateSelection(NUM_PROG, false);
        ligneProgrammeCMSDao.updateSelectionTemporaire(NUM_PROG, false);
        ligneProgrammeCMSService.annulerSelection(NUM_PROG);
        List<LigneProgrammeCMS> after = ligneProgrammeCMSDao.findLigneProgrammeByNumProg(NUM_PROG);
        Iterable<LigneProgrammeCMS> filtredAfter = Iterables.filter(after, ligneProgrammeCMS -> ligneProgrammeCMS.isSelectionEnCours());
        assertThat(Iterables.size(filtredAfter)).isEqualTo(Iterables.size(filtredBefore));
    }

    @Test
    @Transactional
    public void calculerCompteurs() {
        List<LigneProgrammeCMS> lignesProgCMS = ligneProgrammeCMSDao.findLigneProgrammeByNumProg(NUM_PROG);

        Map<String, Object> listCheckCompteurs = new HashMap<>();

        listCheckCompteurs.put(AUTOMATIQUE, Long.valueOf(lignesProgCMS.size()));
        listCheckCompteurs.put(CORRIGE, 0L);
        listCheckCompteurs.put(MANUEL, 0L);
        listCheckCompteurs.put(SOMME, ligneProgrammeCMSDao.calculerPointsMontantOeuvres(NUM_PROG, 1));

        Map<String, Object> listCompteurs = ligneProgrammeCMSService.calculerCompteurs(NUM_PROG, STATUS);
        org.hamcrest.MatcherAssert.assertThat(listCompteurs, is(listCheckCompteurs));
    }

    @Test
    @Transactional
    @Ignore
    public void ajouterOeuvreManuel() {

        /* Scénario oeuvre manuel existante */
        LigneProgrammeCMS oeuvreManuelFound = new LigneProgrammeCMS();
        oeuvreManuelFound.setNumProg(NUM_PROG);
        oeuvreManuelFound.setIde12(2000163011L);
        oeuvreManuelFound.setAjout(MANUEL);

        ligneProgrammeCMSService.ajouterOeuvreManuel(oeuvreManuelFound);
        LigneProgrammeCMS oeuvreManuelChecked = ligneProgrammeCMSDao.findOeuvreManuelByIde12(NUM_PROG, oeuvreManuelFound.getIde12());

        assertThat(oeuvreManuelChecked).isNotNull();
        ligneProgrammeCMSService.deleteOeuvreManuel(oeuvreManuelFound);

        /* Scénario oeuvre automatique existante */
        LigneProgrammeCMS oeuvreAutoFound = new LigneProgrammeCMS();
        oeuvreAutoFound.setNumProg(NUM_PROG);
        oeuvreAutoFound.setIde12(2002037711L);
        oeuvreAutoFound.setAjout(AUTOMATIQUE);

        ligneProgrammeCMSService.ajouterOeuvreManuel(oeuvreAutoFound);
        LigneProgrammeCMS oeuvreAutoToChecked = ligneProgrammeCMSDao.findOeuvreManuelByIde12(NUM_PROG, oeuvreAutoFound.getIde12());

        assertThat(oeuvreAutoToChecked).isNotNull();
        ligneProgrammeCMSService.deleteOeuvreManuel(oeuvreAutoFound);
    }

    @Test
    public void createOeuvreManuel() {
        Programme programme = programmeDao.findByNumProg(NUM_PROG);
        LigneProgrammeCMS oeuvreNewManuel = new LigneProgrammeCMS();
        oeuvreNewManuel.setIde12(2018012611L);
        oeuvreNewManuel.setCdeCisac(CDE_CISAC_058);
        oeuvreNewManuel.setCdeFamilTypUtil(programme.getFamille().getCode());
        oeuvreNewManuel.setCdeUtil(CDE_UTIL);
        oeuvreNewManuel.setAjout(MANUEL);
        oeuvreNewManuel.setSelection(TRUE);
        oeuvreNewManuel.setDateInsertion(new Date());
        oeuvreNewManuel.setSelectionEnCours(TRUE);
        oeuvreNewManuel.setSelection(FALSE);

        ligneProgrammeCMSService.createOeuvreManuel(oeuvreNewManuel, programme);
        LigneProgrammeCMS oeuvreNewManuelToChecked = ligneProgrammeCMSDao.findOeuvreManuelByIde12(NUM_PROG, oeuvreNewManuel.getIde12());
        assertThat(oeuvreNewManuelToChecked).isNotNull();
        ligneProgrammeCMSService.deleteOeuvreManuel(oeuvreNewManuelToChecked);

    }

    @Test
    public void isEligible() {
        assertThat(ligneProgrammeCMSService.isEligible(IDE12, TYPE_CMS_ANF)).isEqualTo(true);
        assertThat(ligneProgrammeCMSService.isEligible(2003257111L, TYPE_CMS_ANF)).isEqualTo(false);

    }


}