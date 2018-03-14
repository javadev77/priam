package fr.sacem.priam.rest.cms.api;

import fr.sacem.priam.rest.cms.config.RestResourceTest;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by benmerzoukah on 14/03/2018.
 */
public class LigneProgrammeCMSRessourceTest extends RestResourceTest {

    @Test
    public void getDurDifProgramme() throws Exception {

        mockMvc.perform(
                get("/app/rest/ligneProgramme/selection/compteurs?numProg=170001&statut=AFFECTE")
                        .contentType(contentType))
                .andExpect(status().isOk());
    }

    @Test
    public void lancerTraitementAffectationCMS() throws Exception {
    }

    @Test
    public void getLastFinished() throws Exception {
    }

    @Test
    public void ajouterOeuvreManuel() throws Exception {
    }

    @Test
    public void isEligible() throws Exception {
    }

    @Test
    public void getLastCatalogueOctav() throws Exception {
    }

    @Test
    public void modifierSelectionTemporaire() throws Exception {
    }

}