package fr.sacem.priam.model.dao.jpa;

import fr.sacem.priam.model.dao.JpaConfigurationTest;
import fr.sacem.priam.model.domain.saref.SareftrTyputil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by benmerzoukah on 09/05/2017.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={JpaConfigurationTest.class})
public class SareftrTyputilDaoTest {
    
    @Autowired
    private SareftrTyputilDao sareftrTyputilDao;
    
    
    @Test
    public void should_return_all_type_utilisation() {
    
        List<SareftrTyputil> all = sareftrTyputilDao.findAll();
    
        assertThat(all).isNotNull().isNotEmpty();
        assertThat(all).extracting("code").containsExactly("COPRIVSON", "CPRIVAUDPL", "CPRIVAUDV","CPRIVSONPH", "CPRIVSONRD", "ENCOURG", "PRIME", "VALORIS");
    }
    
    @Test
    public void should_return_type_utilisation_by_code_famille_COPIPRIVEE() {
        
        List<SareftrTyputil> all = sareftrTyputilDao.findByCodeFamille("COPIEPRIV");
        
        assertThat(all).isNotNull().isNotEmpty();
        List list = new ArrayList();
        list.add("CPRIVAUDPL");
        list.add("CPRIVAUDV");
        list.add("CPRIVSONPH");
        list.add("CPRIVSONRD");


        assertThat(all).extracting("code").containsExactlyElementsOf(list);
    }
    
    @Test
    public void should_return_type_utilisation_by_code_famille_CMS() {
        
        List<SareftrTyputil> all = sareftrTyputilDao.findByCodeFamille("CMS");
        
        assertThat(all).isNotNull().isNotEmpty();
        assertThat(all).extracting("code").containsExactly("ENCOURG");
    }

}
