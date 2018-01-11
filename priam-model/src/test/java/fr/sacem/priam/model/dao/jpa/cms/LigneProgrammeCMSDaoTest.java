package fr.sacem.priam.model.dao.jpa.cms;

import fr.sacem.priam.model.dao.AbstractDaoTest;
import fr.sacem.priam.model.dao.jpa.FichierDao;
import fr.sacem.priam.model.domain.Fichier;
import fr.sacem.priam.model.domain.cms.LigneProgrammeCMS;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@Transactional
public class LigneProgrammeCMSDaoTest extends AbstractDaoTest {

    @Autowired
    LigneProgrammeCMSDao ligneProgrammeCMSDao;

    @Autowired
    FichierDao fichierDao;

    @Test
    public void deleteAllByFichierId(){
        List<Fichier> fichiers = fichierDao.findAll();
        long fileId = fichiers.get(0).getId();
        ligneProgrammeCMSDao.deleteAllByFichierId(fileId);
        List<LigneProgrammeCMS>  all = ligneProgrammeCMSDao.findAll();
        assertThat(all).isNotNull().isEmpty();
    }


}
