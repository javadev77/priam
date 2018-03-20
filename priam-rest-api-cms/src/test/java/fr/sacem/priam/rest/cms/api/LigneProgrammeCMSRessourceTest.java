package fr.sacem.priam.rest.cms.api;

import fr.sacem.priam.model.domain.CatalogueOctav;
import fr.sacem.priam.model.domain.cms.LigneProgrammeCMS;
import fr.sacem.priam.rest.cms.config.RestResourceTest;
import fr.sacem.priam.services.dto.ValdierSelectionProgrammeInput;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by benmerzoukah on 14/03/2018.
 */
public class LigneProgrammeCMSRessourceTest extends RestResourceTest {

    @Test
    public void getDurDifProgramme() throws Exception {

        mockMvc.perform(
                get("/app/rest/ligneProgramme/selection/compteurs?numProg=180001&statut=AFFECTE")
                        .contentType(contentType))
                .andExpect(status().isOk());
    }


    @Test
    public void getLastFinished() throws Exception {

        mockMvc.perform(
                get("/app/rest/programme/eligibilite/tmt/180001")
                        .contentType(contentType))
                .andExpect(status().isOk());
    }



    @Test
    public void ajouterOeuvreManuel() throws Exception {

        LigneProgrammeCMS input = new LigneProgrammeCMS();
        input.setIde12(14257554L);
        input.setTitreOeuvre("title");
        input.setNumProg("180001");

        mockMvc.perform(
                post("/app/rest/ligneProgramme/selection/ajoutOeuvre")
                     .content(json(input))
                    .contentType(contentType))
                .andExpect(status().isOk());
    }

    @Test
    public void isEligible() throws Exception {

        CatalogueOctav input = new CatalogueOctav();
        input.setIde12(4517897L);
        input.setTypeCMS("ANT");
        mockMvc.perform(
                post("/app/rest/ligneProgramme/selection/isEligible")
                        .content(json(input))
                        .contentType(contentType))
                .andExpect(status().isOk());
    }

    @Test
    public void modifierSelectionTemporaire() throws Exception {

        ValdierSelectionProgrammeInput input = new ValdierSelectionProgrammeInput();
        input.setNumProg("180001");
        mockMvc.perform(
                post("/app/rest/ligneProgramme/selection/temporaire/modifier")
                        .content(json(input))
                        .contentType(contentType))
                .andExpect(status().isOk());
    }

    @Test
    public void modifierSelectionTemporaire_unselected() throws Exception {

        ValdierSelectionProgrammeInput input = new ValdierSelectionProgrammeInput();
        input.setNumProg("180001");

        Set<Map<String, String>> inselectedSet = new HashSet<>();
        Map<String, String> selection = new HashMap<>();
        selection.put("ide12", 6829877211L+"");
        selection.put("durDif", "30");
        inselectedSet.add(selection);
        input.setUnselected(inselectedSet);

        mockMvc.perform(
                post("/app/rest/ligneProgramme/selection/temporaire/modifier")
                        .content(json(input))
                        .contentType(contentType))
                .andExpect(status().isOk());
    }


    @Test
    public void modifierSelectionTemporaire_selected() throws Exception {

        ValdierSelectionProgrammeInput input = new ValdierSelectionProgrammeInput();
        input.setNumProg("180001");

        Set<Map<String, String>> selected = new HashSet<>();
        Map<String, String> selection = new HashMap<>();
        selection.put("ide12", 6829877211L+"");
        selection.put("durDif", "30");
        selected.add(selection);
        input.setSelected(selected);

        mockMvc.perform(
                post("/app/rest/ligneProgramme/selection/temporaire/modifier")
                        .content(json(input))
                        .contentType(contentType))
                .andExpect(status().isOk());
    }

}