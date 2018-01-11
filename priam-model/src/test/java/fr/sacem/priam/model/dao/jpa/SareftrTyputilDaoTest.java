package fr.sacem.priam.model.dao.jpa;

import fr.sacem.priam.model.dao.AbstractDaoTest;
import fr.sacem.priam.model.domain.saref.SareftrTyputil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by benmerzoukah on 09/05/2017.
 */

public class SareftrTyputilDaoTest extends AbstractDaoTest {
    
    @Autowired
    private SareftrTyputilDao sareftrTyputilDao;
    
    
    @Test
    public void should_return_all_type_utilisation() {
    
        List<SareftrTyputil> all = sareftrTyputilDao.findAll();
    
        assertThat(all).isNotNull().isNotEmpty();
        assertThat(all).extracting("code").contains("COPRIVSON", "CPRIVAUDPL", "CPRIVAUDV","CPRIVSONPH", "CPRIVSONRD", "ENCOURG", "PRIME", "VALORIS");
    }
    
    @Test
    public void should_return_type_utilisation_by_code_famille_COPIPRIVEE() {
        
        List<SareftrTyputil> all = sareftrTyputilDao.findByCodeFamille("COPIEPRIV");
        
        assertThat(all).isNotNull().isNotEmpty();
        assertThat(all).extracting("code").contains("CPRIVAUDPL","CPRIVAUDV", "CPRIVSONPH", "CPRIVSONRD");
    }
    
    @Test
    public void should_return_type_utilisation_by_code_famille_CMS() {
        
        List<SareftrTyputil> all = sareftrTyputilDao.findByCodeFamille("UC");
        
        assertThat(all).isNotNull().isNotEmpty();
        assertThat(all).extracting("code").contains("AUTREUC");
    }

}
