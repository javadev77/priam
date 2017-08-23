package fr.sacem.priam.model.dao.jpa;

import fr.sacem.priam.model.dao.JpaConfigurationTest;
import fr.sacem.priam.model.domain.Fichier;
import fr.sacem.priam.model.domain.LigneProgramme;
import fr.sacem.priam.model.domain.dto.KeyValueDto;
import fr.sacem.priam.model.domain.dto.SelectionDto;
import fr.sacem.priam.model.services.LigneProgrammeServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by benmerzoukah on 29/05/2017.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={JpaConfigurationTest.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class LigneProgrammeDaoTest {
    
    @Autowired
    LigneProgrammeDao ligneProgrammeDao;
    LigneProgrammeServiceImpl ligneProgrammeServiceImpl = new LigneProgrammeServiceImpl();
    
    @Autowired
    FichierDao fichierDao;

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

    private static final String NUM_PROG = "170001";
    private static final Long INITIAL_IDE12 = 772L;
    private static final Long IDE12 = 6829877211L;
    private static final String INITIAL_TITRES = "Tes";


    @Before
    public void setUp() throws Exception{
        ligneProgrammeServiceImpl.setLigneProgrammeDao(ligneProgrammeDao);
        ligneProgrammeServiceImpl.setFichierDao(fichierDao);
    }
    
    @Test
    public void should_delete_all_data_ligne_programme() {
        List<Fichier> fichiers = fichierDao.findAll();
        long fileId = fichiers.get(0).getId();
        ligneProgrammeDao.deleteAllByFichierId(fileId);
        List<LigneProgramme> all = ligneProgrammeDao.findByFichierId(fileId);
        
        assertThat(all).isNotNull().isEmpty();
    }
    @Test
    public void get_one_ligne_programme(){
        LigneProgramme ligneProgramme = ligneProgrammeDao.findOne(1l);
        assertThat(ligneProgramme).isNotNull();
    }
    @Test
    public void get_ligne_programme_by_programme(){
        //List<LigneProgramme> ligneProgrammes = ligneProgrammeDao.findLigneProgrammeByProgrammeId("PR170001");
        //assertThat(ligneProgrammes.size()).isEqualTo(3);
    }

    @Test
    @Transactional
    public void testUpdateSelectionByNumProgramme() throws Exception{

        boolean success = true;
        try{
            ligneProgrammeDao.updateSelectionByNumProgramme(NUM_PROG, Boolean.TRUE);
        }catch (Exception e) {
            success = false;
        }

        assertThat(success).isFalse();
    }

    @Test
    public void findIDE12sByProgramme() throws Exception {
        List<KeyValueDto> listIDE12ByProgramme = ligneProgrammeDao.findIDE12sByProgramme(INITIAL_IDE12, NUM_PROG);
        assertThat(listIDE12ByProgramme).isNotNull().isNotEmpty();

        assertThat(listIDE12ByProgramme.stream().anyMatch(keyValue -> keyValue.getCode().toString().contains(INITIAL_IDE12.toString()))).isEqualTo(true);
    }

    @Test
    public void findLigneProgrammeByCriteria() throws Exception {
        Page<SelectionDto> ligneProgrammeByCriteria = ligneProgrammeDao.findLigneProgrammeByCriteria(NUM_PROG, null, IDE12, null, null, null, pageable);
        assertThat(ligneProgrammeByCriteria).isNotNull();
    }

    @Test
    public void getTitresByProgramme() throws Exception {
        List<KeyValueDto> titresByProgramme = ligneProgrammeDao.findTitresByProgramme(INITIAL_TITRES.toUpperCase(), NUM_PROG);
        assertThat(titresByProgramme).isNotNull().isNotEmpty();

        assertThat(titresByProgramme.stream().anyMatch(keyValue -> keyValue.getValue().toString().contains(INITIAL_TITRES))).isEqualTo(true);
    }

    @Test
    public void getUtilisateursByProgramme() throws Exception {
        List<String> utilisateursByProgramme = ligneProgrammeDao.findUtilisateursByProgramme(NUM_PROG);
        assertThat(utilisateursByProgramme).isNotNull().isNotEmpty();
    }

    /***
     * impossible d'executer cette requete sur la base de test (h2)
     * org.h2.jdbc.JdbcSQLException: Syntax error in SQL statement "UPDATE   PRIAM_LIGNE_PROGRAMME P INNER[*] JOIN   PRIAM_FICHIER F ON P.ID_FICHIER = F.ID SET   P.SELECTION=? WHERE   F.NUMPROG = ? ";
     * @throws Exception
     */
    @Test
    @Transactional
    public void updateSelectionByNumProgrammeExcept() throws Exception {
        boolean flag = true;
        try{
            Set<Long> listIDE12 = new HashSet<>();
            listIDE12.add(IDE12);
            ligneProgrammeDao.updateSelectionByNumProgrammeExcept(NUM_PROG, listIDE12);
        } catch (Exception e ) {
            flag = false;
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
    public void supprimerLigneProgramme() throws Exception {
        boolean flag = true;
        try{
            ligneProgrammeDao.deleteLigneProgrammeByIde12AndNumProg(NUM_PROG, IDE12);
        } catch (Exception e ) {
            flag = false;
        }

        assertThat(flag).isEqualTo(false);
    }





}
