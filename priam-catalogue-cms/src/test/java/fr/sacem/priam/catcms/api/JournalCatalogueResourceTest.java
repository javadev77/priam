package fr.sacem.priam.catcms.api;

import fr.sacem.priam.catcms.api.dto.JournalCatcmsCritereRecherche;
import fr.sacem.priam.catcms.config.RestResourceTest;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by embouazzar on 28/08/2018.
 */
public class JournalCatalogueResourceTest extends RestResourceTest {

    public static final String APP_REST_JOURNAL_CATCMS_SEARCH = "/app/rest/journalCatcms/search";

    JournalCatcmsCritereRecherche critereRecherche = new JournalCatcmsCritereRecherche();

    public static final String TYPE_CMS_FR = "FR";
    public static final String TYPE_CMS_ANF = "ANF";

    @Test
    public void rechercheEvenements() throws Exception {
        mockMvc.perform(
                get(APP_REST_JOURNAL_CATCMS_SEARCH).contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numberOfElements", is(13)));
    }

    @Test
    public void rechercheEvenementsPost() throws Exception {
        critereRecherche.setTypeCMS(TYPE_CMS_FR);
        critereRecherche.setTypeEvenement("Ajout manuel oeuvre");
        mockMvc.perform(
                post(APP_REST_JOURNAL_CATCMS_SEARCH)
                        .contentType(contentType)
                        .content(this.json(critereRecherche)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numberOfElements", is(1)));
    }

}
