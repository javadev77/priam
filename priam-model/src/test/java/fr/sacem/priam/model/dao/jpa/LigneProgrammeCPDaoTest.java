package fr.sacem.priam.model.dao.jpa;

import fr.sacem.priam.model.dao.JpaConfigurationTest;
import fr.sacem.priam.model.dao.jpa.cp.FichierCPDao;
import fr.sacem.priam.model.dao.jpa.cp.LigneProgrammeCPDao;
import fr.sacem.priam.model.domain.cp.FichierCP;
import fr.sacem.priam.model.domain.LignePreprep;
import fr.sacem.priam.model.domain.cp.LigneProgrammeCP;
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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by benmerzoukah on 29/05/2017.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={JpaConfigurationTest.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@Transactional
public class LigneProgrammeCPDaoTest {

    private static final String CDE_UTIL = "";

    @Autowired
    LigneProgrammeCPDao ligneProgrammeCPDao;

    @Autowired
    FichierCPDao fichierCPDao;

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



    @Test
    public void should_delete_all_data_ligne_programme() {
        List<FichierCP> fichiers = fichierCPDao.findAll();
        long fileId = fichiers.get(0).getId();
        ligneProgrammeCPDao.deleteAllByFichierId(fileId);
        List<LigneProgrammeCP> all = ligneProgrammeCPDao.findByFichierId(fileId);
        
        assertThat(all).isNotNull().isEmpty();
    }


    @Ignore
    @Test
    @Transactional
    public void testUpdateSelectionByNumProgramme() throws Exception{

        boolean success = true;
        try{
            ligneProgrammeCPDao.updateSelectionTemporaireByNumProgramme(NUM_PROG, Boolean.TRUE);
        }catch (Exception e) {
            success = false;
        }

        assertThat(success).isFalse();
    }

    @Test
    public void findIDE12sByProgramme() throws Exception {
        List<KeyValueDto> listIDE12ByProgramme = ligneProgrammeCPDao.findIDE12sByProgramme(INITIAL_IDE12, NUM_PROG);
        assertThat(listIDE12ByProgramme).isNotNull().isNotEmpty();

        assertThat(listIDE12ByProgramme.stream().anyMatch(keyValue -> keyValue.getCode().toString().contains(INITIAL_IDE12.toString()))).isEqualTo(true);
    }

    @Test
    public void findLigneProgrammeByCriteria() throws Exception {
        Page<SelectionDto> ligneProgrammeByCriteria = ligneProgrammeCPDao.findLigneProgrammeByCriteria(NUM_PROG, null, IDE12, null, null, null, pageable);
        assertThat(ligneProgrammeByCriteria).isNotNull();
    }

    @Test
    public void getTitresByProgramme() throws Exception {
        List<KeyValueDto> titresByProgramme = ligneProgrammeCPDao.findTitresByProgramme(INITIAL_TITRES.toUpperCase(), NUM_PROG);
        assertThat(titresByProgramme).isNotNull().isNotEmpty();

        assertThat(titresByProgramme.stream().anyMatch(keyValue -> keyValue.getValue().toString().contains(INITIAL_TITRES))).isEqualTo(true);
    }

    /**
     * La base de donnée de test ne supporte pas la fonction COALESCE et CASE WHEN
     * @throws Exception
     */
    @Test
    public void getUtilisateursByProgramme() throws Exception {
        List<String> utilisateursByProgramme = ligneProgrammeCPDao.findUtilisateursByProgramme(NUM_PROG);
        assertThat(utilisateursByProgramme).isNotNull().isNotEmpty();
    }


    @Test
    public void findLigneProgrammeSelectionnes() throws Exception {
        List<LignePreprep> ligneProgrammeSelectionnesForFelix = ligneProgrammeCPDao.findLigneProgrammeSelectionnesForFelix(NUM_PROG);
        assertThat(ligneProgrammeSelectionnesForFelix).isNotNull();
    }


}
