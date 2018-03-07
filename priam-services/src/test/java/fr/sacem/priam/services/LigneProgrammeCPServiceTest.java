package fr.sacem.priam.services;

import com.google.common.collect.Iterables;
import fr.sacem.priam.model.dao.JpaConfigurationTest;
import fr.sacem.priam.model.dao.jpa.cp.LigneProgrammeCPDao;
import fr.sacem.priam.model.domain.cp.LigneProgrammeCP;
import fr.sacem.priam.model.domain.criteria.LigneProgrammeCriteria;
import fr.sacem.priam.model.domain.dto.KeyValueDto;
import fr.sacem.priam.model.domain.dto.SelectionDto;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by belwidanej on 10/08/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={JpaConfigurationTest.class, ConfigurationTest.class})
@ActiveProfiles("test")
public class LigneProgrammeCPServiceTest {


    public static final String CDE_UTIL = "";

    @Autowired
    LigneProgrammeCPServiceImpl ligneProgrammeCPService;

    @Autowired
    LigneProgrammeCPDao ligneProgrammeCPDao;

    private static final String NUM_PROG = "170001";
    private static final Long INITIAL_IDE12 = 772L;
    private static final String IDE12 = "6829877211";
    private static final String INITIAL_TITRES = "Tes";

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

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void getListIDE12ByProgramme() throws Exception {
        List<KeyValueDto> listIDE12ByProgramme = ligneProgrammeCPService.getListIDE12ByProgramme(INITIAL_IDE12, NUM_PROG);
        assertThat(listIDE12ByProgramme).isNotNull().isNotEmpty();

        assertThat(listIDE12ByProgramme.stream().anyMatch(keyValue -> keyValue.getCode().toString().contains(INITIAL_IDE12.toString()))).isEqualTo(true);
    }

    @Test
    public void getTitresByProgramme() throws Exception {
        List<KeyValueDto> titresByProgramme = ligneProgrammeCPService.getTitresByProgramme(INITIAL_TITRES, NUM_PROG);
        assertThat(titresByProgramme).isNotNull().isNotEmpty();

        assertThat(titresByProgramme.stream().anyMatch(keyValue -> keyValue.getValue().toString().contains(INITIAL_TITRES))).isEqualTo(true);
    }

    @Test
    public void getUtilisateursByProgramme() throws Exception {
        List<String> utilisateursByProgramme = ligneProgrammeCPService.getUtilisateursByProgramme(NUM_PROG);
        assertThat(utilisateursByProgramme).isNotNull().isNotEmpty();
    }

    @Test
    public void findLigneProgrammeByCriteria() throws Exception {
        LigneProgrammeCriteria criteria = new LigneProgrammeCriteria();
        criteria.setNumProg(NUM_PROG);
        criteria.setIde12(Long.valueOf(IDE12));
        Page<SelectionDto> ligneProgrammeByCriteria = ligneProgrammeCPService.findLigneProgrammeByCriteria(criteria, pageable);
        assertThat(ligneProgrammeByCriteria).isNotNull();
    }


    @Test
    @Transactional
    public void selectLigneProgramme() throws Exception {
        Set<Map<String, String>> listIDE12 = new HashSet<>();

        Map<String, String> list = new HashMap<>();
        list.put("ide12", IDE12);
        list.put("libAbrgUtil", CDE_UTIL);
        listIDE12.add(list);

        ligneProgrammeCPService.selectLigneProgramme(NUM_PROG, listIDE12);

        List<LigneProgrammeCP> ligneProgrammeCP = ligneProgrammeCPDao.findLigneProgrammeByNumProg(NUM_PROG);
        Iterable<LigneProgrammeCP> selectedLigneCP = Iterables.filter(ligneProgrammeCP, lp -> lp.isSelectionEnCours());

        assertThat(Iterables.size(selectedLigneCP)).isEqualTo(3);
    }




    @Test
    @Transactional
    @Ignore
    public void testSupprimerLigneProgramme() throws Exception {
        LigneProgrammeCP oeuvreToDelete = new LigneProgrammeCP();
        oeuvreToDelete.setNumProg(NUM_PROG);
        oeuvreToDelete.setIde12(6547891L);
        oeuvreToDelete.setCdeUtil("CDE-TEST");

        SelectionDto selectedLigneProgramme = new SelectionDto();
        selectedLigneProgramme.setCdeUtil(oeuvreToDelete.getCdeUtil());


        ligneProgrammeCPService.ajouterOeuvreManuel(oeuvreToDelete);

        ligneProgrammeCPService.supprimerLigneProgramme(NUM_PROG, oeuvreToDelete.getIde12(), selectedLigneProgramme);

        LigneProgrammeCP ligneProgrammeCP = ligneProgrammeCPDao.findByIde12AndCdeUtil(NUM_PROG, oeuvreToDelete.getIde12(), selectedLigneProgramme.getCdeUtil());

        assertThat(ligneProgrammeCP).isNull();
    }



    @Test
    @Transactional
    public void testEnregistrerEdition() throws Exception {
        ligneProgrammeCPDao.updateSelectionTemporaire(NUM_PROG, true);
        List<LigneProgrammeCP> before = ligneProgrammeCPDao.findLigneProgrammeByNumProg(NUM_PROG);
        Iterable<LigneProgrammeCP> filtredBefore = Iterables.filter(before, ligneProgrammeCP -> ligneProgrammeCP.isSelectionEnCours());

        ligneProgrammeCPService.enregistrerEdition(NUM_PROG);

        List<LigneProgrammeCP> after = ligneProgrammeCPDao.findLigneProgrammeByNumProg(NUM_PROG);
        Iterable<LigneProgrammeCP> filtredAfter = Iterables.filter(after, ligneProgrammeCP -> ligneProgrammeCP.isSelection());

        assertThat(Iterables.size(filtredAfter)).isEqualTo(Iterables.size(filtredBefore));
    }

    @Test
    @Transactional
    public void testAnnulerEdition() throws Exception {

        List<LigneProgrammeCP> before = ligneProgrammeCPDao.findLigneProgrammeByNumProg(NUM_PROG);
        Iterable<LigneProgrammeCP> filtredBefore = Iterables.filter(before, ligneProgrammeCP -> ligneProgrammeCP.isSelectionEnCours());

        ligneProgrammeCPDao.updateSelection(NUM_PROG, true);


      //  ligneProgrammeCPService.annulerEdition(NUM_PROG);

        List<LigneProgrammeCP> after = ligneProgrammeCPDao.findLigneProgrammeByNumProg(NUM_PROG);
        Iterable<LigneProgrammeCP> filtredAfter = Iterables.filter(after, ligneProgrammeCP -> ligneProgrammeCP.isSelectionEnCours());

        assertThat(Iterables.size(filtredAfter)).isEqualTo(Iterables.size(filtredBefore));
    }

    @Test
    @Transactional
    public void testDeselectLignesProgrammes() throws Exception {
        Set<Map<String, String>> listIDE12 = new HashSet<>();

        Map<String, String> list = new HashMap<>();
        list.put("ide12", IDE12);
        list.put("libAbrgUtil", "0002");
        list.put("durDif", "30");
        listIDE12.add(list);

        ligneProgrammeCPService.deselectLigneProgramme(NUM_PROG, listIDE12);

        List<LigneProgrammeCP> ligneProgrammeCP = ligneProgrammeCPDao.findLigneProgrammeByNumProg(NUM_PROG);
        Iterable<LigneProgrammeCP> unselectedLigneCP = Iterables.filter(ligneProgrammeCP, lp -> !lp.isSelectionEnCours());

        assertThat(Iterables.size(unselectedLigneCP)).isEqualTo(1);
        assertThat(unselectedLigneCP).extracting("ide12").contains(Long.valueOf(IDE12));
   }
}
