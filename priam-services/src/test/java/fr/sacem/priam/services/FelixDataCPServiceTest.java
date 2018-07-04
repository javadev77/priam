package fr.sacem.priam.services;

import fr.sacem.priam.model.dao.JpaConfigurationTest;
import fr.sacem.priam.model.domain.LignePreprep;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by benmerzoukah on 20/03/2018.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={JpaConfigurationTest.class, ConfigurationTest.class})
@ActiveProfiles("test")
public class FelixDataCPServiceTest {

    @Autowired
    FelixDataCPService felixDataCPService;

    @Test
    public void getListLignesSelectionnees() throws Exception {

        String numProg = "170001";
        List<LignePreprep> listLignesSelectionnees = felixDataCPService.getListLignesSelectionnees(numProg);

        assertThat(listLignesSelectionnees).isNotNull();

    }

}