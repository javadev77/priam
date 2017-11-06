package fr.sacem.priam.services;

import fr.sacem.priam.model.dao.JpaConfigurationTest;
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
public class LigneProgrammeCPServiceTest {


    public static final String CDE_UTIL = "";
    @Autowired
    LigneProgrammeService ligneProgrammeService;

    private static final String NUM_PROG = "170001";
    private static final Long INITIAL_IDE12 = 772L;
    private static final Long IDE12 = 6829877211L;
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
        List<KeyValueDto> listIDE12ByProgramme = ligneProgrammeService.getListIDE12ByProgramme(INITIAL_IDE12, NUM_PROG);
        assertThat(listIDE12ByProgramme).isNotNull().isNotEmpty();

        assertThat(listIDE12ByProgramme.stream().anyMatch(keyValue -> keyValue.getCode().toString().contains(INITIAL_IDE12.toString()))).isEqualTo(true);
    }

    @Test
    public void getTitresByProgramme() throws Exception {
        List<KeyValueDto> titresByProgramme = ligneProgrammeService.getTitresByProgramme(INITIAL_TITRES, NUM_PROG);
        assertThat(titresByProgramme).isNotNull().isNotEmpty();

        assertThat(titresByProgramme.stream().anyMatch(keyValue -> keyValue.getValue().toString().contains(INITIAL_TITRES))).isEqualTo(true);
    }

    /**
     *
     * @throws Exception
     */
    @Test()
    public void getUtilisateursByProgramme() throws Exception {
        List<String> utilisateursByProgramme = ligneProgrammeService.getUtilisateursByProgramme(NUM_PROG);
        assertThat(utilisateursByProgramme).isNotNull().isNotEmpty();
    }

    @Test
    public void findLigneProgrammeByCriteria() throws Exception {
        LigneProgrammeCriteria criteria = new LigneProgrammeCriteria();
        criteria.setNumProg(NUM_PROG);
        criteria.setIde12(IDE12);
        Page<SelectionDto> ligneProgrammeByCriteria = ligneProgrammeService.findLigneProgrammeByCriteria(criteria, pageable);
        assertThat(ligneProgrammeByCriteria).isNotNull();
    }

    /***
     * impossible d'executer cette requete sur la base de test (h2)
     * org.h2.jdbc.JdbcSQLException: Syntax error in SQL statement "UPDATE   PRIAM_LIGNE_PROGRAMME P INNER[*] JOIN   PRIAM_FICHIER F ON P.ID_FICHIER = F.ID SET   P.SELECTION=? WHERE   F.NUMPROG = ? ";
     * @throws Exception
     */
    @Ignore
    @Test
    @Transactional
    public void selectAll() throws Exception {

            ligneProgrammeService.selectAll(NUM_PROG);
    }

    /***
     *
     * org.h2.jdbc.JdbcSQLException: Syntax error in SQL statement "UPDATE   PRIAM_LIGNE_PROGRAMME P INNER[*] JOIN   PRIAM_FICHIER F ON P.ID_FICHIER = F.ID SET   P.SELECTION=? WHERE   F.NUMPROG = ? ";
     * @throws Exception
     */
    @Test
    @Transactional
    public void selectLigneProgramme() throws Exception {
        boolean flag = true;
        try{
            Set<Map<String, String>> listIDE12 = new HashSet<>();

            Map<String, String> list = new HashMap<>();
            list.put("ide12", IDE12+"");
            list.put("cdeUtil", CDE_UTIL);

            ligneProgrammeService.selectLigneProgramme(NUM_PROG, listIDE12);
        } catch (Exception e ) {
            flag = false;
        }

        assertThat(flag).isEqualTo(true);
    }

    /***
     * impossible d'executer cette requete sur la base de test (h2)
     * org.h2.jdbc.JdbcSQLException: Syntax error in SQL statement "UPDATE   PRIAM_LIGNE_PROGRAMME P INNER[*] JOIN   PRIAM_FICHIER F ON P.ID_FICHIER = F.ID SET   P.SELECTION=? WHERE   F.NUMPROG = ? ";
     * @throws Exception
     */
    @Ignore
    @Test
    @Transactional
    public void deselectAll() throws Exception {
        boolean flag = true;
        try{
            ligneProgrammeService.deselectAll(NUM_PROG);
        } catch (Exception e ) {
            e.printStackTrace();

        }

        assertThat(flag).isEqualTo(false);
    }

    /***
     * impossible d'executer cette requete sur la base de test (h2)
     * org.h2.jdbc.JdbcSQLException: Syntax error in SQL statement "UPDATE   PRIAM_LIGNE_PROGRAMME P INNER[*] JOIN   PRIAM_FICHIER F ON P.ID_FICHIER = F.ID SET   P.SELECTION=? WHERE   F.NUMPROG = ? ";
     * @throws Exception
     */
    @Test
    @Transactional
    @Ignore
    public void supprimerLigneProgramme() throws Exception {
        boolean flag = true;
        try{
            ligneProgrammeService.supprimerLigneProgramme(NUM_PROG, IDE12, new SelectionDto());
        } catch (Exception e ) {
            flag = false;
        }

        assertThat(flag).isEqualTo(false);
    }

}