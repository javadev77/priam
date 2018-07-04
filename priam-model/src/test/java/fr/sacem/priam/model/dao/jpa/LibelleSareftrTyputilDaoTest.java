package fr.sacem.priam.model.dao.jpa;

import fr.sacem.priam.model.dao.AbstractDaoTest;
import fr.sacem.priam.model.domain.saref.SareftjLibtyputil;
import fr.sacem.priam.model.util.TypeUtilisationPriam;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by benmerzoukah on 09/05/2017.
 */

public class LibelleSareftrTyputilDaoTest  extends AbstractDaoTest {
    
    @Autowired
    private SareftjLibtyputilDao sareftjLibtyputilDao;
    
    
    @Test
    public void should_return_all_type_utilisation_FR() {
        List<SareftjLibtyputil> all = sareftjLibtyputilDao.findByLang("FR", TypeUtilisationPriam.getCodes());
    
        assertThat(all).isNotNull().isNotEmpty();
        assertThat(all).extracting("code").containsAll(TypeUtilisationPriam.getCodes());
    }
    
    @Test
    public void should_return_empty_when_null() {
        List<SareftjLibtyputil> all = sareftjLibtyputilDao.findByLang(null, TypeUtilisationPriam.getCodes());
        
        assertThat(all).isNotNull().isEmpty();
    }

}
