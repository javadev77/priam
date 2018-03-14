package fr.sacem.priam.rest.cms.api;

import fr.sacem.priam.model.domain.dto.AffectationDto;
import fr.sacem.priam.rest.cms.config.RestResourceTest;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by benmerzoukah on 17/01/2018.
 */
public class AffectationCMSResourceTest extends RestResourceTest {

    @Test
    public void affecterFichiers() throws Exception {
        mockMvc.perform(
                put("/app/rest/programme/affectation")
                        .content(this.json(createAffectationDto("170001")))
                        .contentType(contentType))
                .andExpect(status().isOk());
    }

    private AffectationDto createAffectationDto(String numProg) {
        AffectationDto affectationDto = new AffectationDto();

        affectationDto.setNumProg(numProg);

        return  affectationDto;
    }

    @Test
    public void desaffecterFichiers() throws Exception {

        mockMvc.perform(
                put("/app/rest/programme/toutDesaffecter")
                        .content("170001")
                        .contentType(contentType))
                .andExpect(status().isOk());
    }

}