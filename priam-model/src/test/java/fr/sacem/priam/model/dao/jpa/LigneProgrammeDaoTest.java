package fr.sacem.priam.model.dao.jpa;

import fr.sacem.priam.model.dao.JpaConfigurationTest;
import fr.sacem.priam.model.domain.Fichier;
import fr.sacem.priam.model.domain.LigneProgramme;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

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
    
    @Autowired
    FichierDao fichierDao;
    
    @Test
    public void should_delete_all_data_ligne_programme() {
        List<Fichier> fichiers = fichierDao.findAll();
        long fileId = fichiers.get(0).getId();
        ligneProgrammeDao.deleteAllByFichierId(fileId);
        List<LigneProgramme> all = ligneProgrammeDao.findByFichierId(fileId);
        
        assertThat(all).isNotNull().isEmpty();
    }
    
}
