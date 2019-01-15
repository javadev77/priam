package fr.sacem.priam.rest.valorisation.api;

import com.fasterxml.jackson.core.type.TypeReference;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.sacem.priam.model.dao.jpa.fv.LigneProgrammeFVDao;

import fr.sacem.priam.model.domain.fv.LigneProgrammeFV;
import fr.sacem.priam.rest.valorisation.config.RestResourceTest;
import fr.sacem.priam.services.dto.ValdierSelectionProgrammeInput;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.*;

import static fr.sacem.priam.model.util.EtatOeuvreEnum.AUTOMATIQUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LigneProgrammeFVResourceTest extends RestResourceTest {

    public static final int SELECTION = 1;

    @Autowired
    private LigneProgrammeFVDao ligneProgrammeFVDao;

    @Test
    public void getDurDifProgramme() throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        ResultActions resultActions = mockMvc.perform(
                get("/app/rest/ligneProgramme/selection/compteurs?numProg=190001&statut=AFFECTE")
                        .contentType(contentType))
                .andExpect(status().isOk());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();

        TypeReference<HashMap<String, Object>> typeRef
                = new TypeReference<HashMap<String, Object>>() {};

        Map<String, Object> map = mapper.readValue(contentAsString, typeRef);
        Assert.assertEquals(map.get(AUTOMATIQUE.getCode()),2);

    }


    @Test
    public void modifierSelectionTemporaire() throws Exception {

        ValdierSelectionProgrammeInput input = new ValdierSelectionProgrammeInput();
        input.setNumProg("190002");
        input.setAll(false);
        input.setDeselectAll(false);
        Set<Map<String, String>> mapSelected = new HashSet<>();
        Map<String, String> m1 = new HashMap<>();
        m1.put("ide12","2739083111");
        m1.put("ajout","AUTOMATIQUE");
        m1.put("selection","true");
        m1.put("pointsMontant","50");
        mapSelected.add(m1);
        input.setSelected(mapSelected);

        mockMvc.perform(
                post("/app/rest/ligneProgramme/selection/temporaire/modifier")
                        .content(json(input))
                        .contentType(contentType))
                .andExpect(status().isOk());

        LigneProgrammeFV om = ligneProgrammeFVDao.findOeuvreCorrigeByIde12("190002", 2739083111L);

        Assert.assertEquals(om.getMtEdit(), new Double(50));
    }

}
