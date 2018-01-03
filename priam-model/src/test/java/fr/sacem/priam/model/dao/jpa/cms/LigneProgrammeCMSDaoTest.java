package fr.sacem.priam.model.dao.jpa.cms;

import fr.sacem.priam.model.dao.JpaConfigurationTest;
import fr.sacem.priam.model.dao.jpa.FichierDao;
import fr.sacem.priam.model.domain.Fichier;
import fr.sacem.priam.model.domain.cms.LigneProgrammeCMS;
import fr.sacem.priam.model.domain.dto.SelectionCMSDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={JpaConfigurationTest.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@Transactional
public class LigneProgrammeCMSDaoTest {

    @Autowired
    LigneProgrammeCMSDao ligneProgrammeCMSDao;

    @Autowired
    FichierDao fichierDao;

    private static final String numProg = "200001";
    private static final Long ide12 = 2258526811L;
    private static final String titre = "KISS";
    private static final String ajout = "Automatique";
    private static final Boolean selectionEnCours = false;

    public static final Pageable PAGEABLE = new Pageable() {
        @Override
        public int getPageNumber() {
            return 0;
        }

        @Override
        public int getPageSize() {
            return 3;
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

    @Test
    public void deleteAllByFichierId(){
        List<Fichier> fichiers = fichierDao.findAll();
        long fileId = fichiers.get(0).getId();
        ligneProgrammeCMSDao.deleteAllByFichierId(fileId);
        List<LigneProgrammeCMS>  all = ligneProgrammeCMSDao.findAll();
        assertThat(all).isNotNull().isEmpty();
    }

    /*@Test
    public void findLigneProgrammeByCriteriaTest(){
        Page<SelectionCMSDto> selectionCMSDtos = ligneProgrammeCMSDao.findLigneProgrammeByCriteria(numProg,ide12,null,null,null,PAGEABLE);
    }*/

}
