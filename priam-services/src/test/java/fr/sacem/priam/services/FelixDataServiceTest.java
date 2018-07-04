package fr.sacem.priam.services;

import fr.sacem.priam.model.dao.JpaConfigurationTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by benmerzoukah on 13/10/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={JpaConfigurationTest.class, ConfigurationTest.class})
@ActiveProfiles("test")
public class FelixDataServiceTest {
    
    @Autowired
    FelixDataCPService felixDataService;
    
    @Test
    public void runAsyncCreateFichierFelix() throws Exception {
    }
    
    @Test
    public void createFichierFelixWithErrors() throws Exception {
        //FichierFelixError pr170001 = felixDataService.createFichierFelixWithErrors("170001", null);
    }
    
    @Test
    public void asyncSendFichierFelix() throws Exception {
    }
    
}