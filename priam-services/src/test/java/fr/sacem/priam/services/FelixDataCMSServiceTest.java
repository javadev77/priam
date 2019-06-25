package fr.sacem.priam.services;

import fr.sacem.priam.model.dao.JpaConfigurationTest;
import fr.sacem.priam.model.domain.LignePreprep;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by benmerzoukah on 20/03/2018.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={JpaConfigurationTest.class, ConfigurationTest.class})
@ActiveProfiles("test")
public class FelixDataCMSServiceTest {
    @Autowired
    FelixDataCMSService felixDataCMSService;

    @Test
    public void getListLignesSelectionnees() throws Exception {
        String numProg = "170001";
        List<LignePreprep> listLignesSelectionnees = felixDataCMSService.getListLignesSelectionnees(numProg);

        assertThat(listLignesSelectionnees).isNotNull();
    }

}