package fr.sacem.priam.model.dao.jpa;

import fr.sacem.priam.model.dao.JpaConfigurationTest;
import fr.sacem.priam.model.domain.TypeUtilisation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by benmerzoukah on 09/05/2017.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={JpaConfigurationTest.class})
public class TypeUtilisationDaoTest {
    
    @Autowired
    private TypeUtilisationDao typeUtilisationDao;
    
    
    @Test
    public void should_return_all_type_utilisation() {
    
        List<TypeUtilisation> all = typeUtilisationDao.findAll();
    
        assertThat(all).isNotNull().isNotEmpty();
        assertThat(all).extracting("code").containsExactly("CPRIVSONPH", "CPRIVAUDV", "CPRIVSONRD", "COPRIVSON", "CPRIVAUDPL", "PRIME", "VALORIS", "ENCOURG", "cdeTyUt1");
    }
    
    @Test
    public void should_return_type_utilisation_by_code_famille_COPIPRIVEE() {
        
        List<TypeUtilisation> all = typeUtilisationDao.findByCodeFamille("COPIEPRIV");
        
        assertThat(all).isNotNull().isNotEmpty();
        assertThat(all).extracting("code").containsExactly("CPRIVSONPH", "CPRIVAUDV", "CPRIVSONRD", "CPRIVAUDPL");
    }
    
    @Test
    public void should_return_type_utilisation_by_code_famille_CMS() {
        
        List<TypeUtilisation> all = typeUtilisationDao.findByCodeFamille("CMS");
        
        assertThat(all).isNotNull().isNotEmpty();
        assertThat(all).extracting("code").containsExactly("ENCOURG");
    }

}
