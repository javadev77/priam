package fr.sacem.priam.catcms.api;

import fr.sacem.priam.catcms.config.RestResourceTest;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by embouazzar on 03/10/2018.
 */
public class StatistiquesResourcesTest extends RestResourceTest {

    public static final String APP_REST_STATISTIQUES_CATCMS_SEARCH = "/app/rest/statistiques/search";

    @Test
    public void findAll() throws Exception {
        mockMvc.perform(
                get(APP_REST_STATISTIQUES_CATCMS_SEARCH).contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numberOfElements", is(4)));
    }

}
