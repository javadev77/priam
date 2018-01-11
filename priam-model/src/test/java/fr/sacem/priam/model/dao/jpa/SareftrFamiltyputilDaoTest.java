package fr.sacem.priam.model.dao.jpa;

import fr.sacem.priam.model.dao.AbstractDaoTest;
import fr.sacem.priam.model.domain.saref.SareftrFamiltyputil;
import fr.sacem.priam.model.util.FamillePriam;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by benmerzoukah on 09/05/2017.
 */

public class SareftrFamiltyputilDaoTest extends AbstractDaoTest {
    
    @Autowired
    private SareftrFamiltyputilDao sareftrFamiltyputilDao;
    
    
    @Test
    public void should_return_all_famille() {
    
        List<SareftrFamiltyputil> all = sareftrFamiltyputilDao.findByFamilles(FamillePriam.getCodes());
    
        assertThat(all).isNotNull().isNotEmpty();
        assertThat(all).extracting("code").contains("COPIEPRIV", "UC");
    }

}
