package fr.sacem.priam.model.dao.jpa.cp;

import fr.sacem.priam.model.dao.AbstractDaoTest;
import fr.sacem.priam.model.dao.jpa.FichierDao;
import fr.sacem.priam.model.domain.Fichier;
import fr.sacem.priam.model.domain.LignePreprep;
import fr.sacem.priam.model.domain.cp.LigneProgrammeCP;
import fr.sacem.priam.model.domain.dto.KeyValueDto;
import fr.sacem.priam.model.domain.dto.SelectionDto;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by benmerzoukah on 29/05/2017.
 */


@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@Transactional(value="transactionManager")
public class LigneProgrammeCPDaoTest extends AbstractDaoTest {

    private static final String CDE_UTIL = "";

    @Autowired
    LigneProgrammeCPDao ligneProgrammeCPDao;

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
    private static final Long IDE12 = 2198031311L;
    private static final String INITIAL_TITRES = "Tes";



    @Test
    public void should_delete_all_data_ligne_programme() {
        List<Fichier> fichiers = fichierDao.findAll();
        long fileId = fichiers.get(0).getId();
        ligneProgrammeCPDao.deleteAllByFichierId(fileId);
        List<LigneProgrammeCP> all = ligneProgrammeCPDao.findByFichierId(fileId);
        
        assertThat(all).isNotNull().isEmpty();
    }


    @Ignore
    @Transactional(value="transactionManager")
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
