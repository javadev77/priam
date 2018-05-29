package fr.sacem.priam.services;

import fr.sacem.priam.model.dao.JpaConfigurationTest;
import fr.sacem.priam.model.domain.Parametrage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by benmerzoukah on 18/01/2018.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={JpaConfigurationTest.class, ConfigurationTest.class})
@ActiveProfiles("test")
public class ParametrageServiceTest {

    public static final String USER_ID_TEST = "userid";

    @Autowired
    ParametrageService parametrageService;

    private Parametrage guestUserPageSizeParametrage;

    private Parametrage guestFamilleParametrage;

    @Before
    public void setUp() throws Exception {
        guestUserPageSizeParametrage = createParametrage(USER_ID_TEST, "USER_PAGE_SIZE", "100");
        guestFamilleParametrage = createParametrage(USER_ID_TEST, "USER_FAMILLE", "COPIEPRIV,Copie Privée");

        parametrageService.save(guestUserPageSizeParametrage);
        parametrageService.save(guestFamilleParametrage);
    }

    private Parametrage createParametrage(String userId, String key, String value) {
        Parametrage parametrage = new Parametrage();
        parametrage.setKey(key);
        parametrage.setValue(value);
        parametrage.setUserId(userId);

        return parametrage;
    }

    @Test
    public void findByUserId() throws Exception {

        Map<String, String> byUserId = parametrageService.findByUserId(USER_ID_TEST);

        assertThat(byUserId.get("USER_PAGE_SIZE")).isEqualTo(guestUserPageSizeParametrage.getValue());
        assertThat(byUserId.get("USER_FAMILLE")).isEqualTo(guestFamilleParametrage.getValue());

    }

    @Test
    public void save() throws Exception {

        Parametrage param = createParametrage("toto", "USER_PAGE_SIZE", "25");

        parametrageService.save(param);

        Map<String, String> totoParam = parametrageService.findByUserId("toto");

        assertThat(totoParam.get("USER_PAGE_SIZE")).isEqualTo("25");

    }

}