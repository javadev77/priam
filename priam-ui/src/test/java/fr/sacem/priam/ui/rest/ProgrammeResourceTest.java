package fr.sacem.priam.ui.rest;

import fr.sacem.priam.model.dao.jpa.ProgrammeDao;
import fr.sacem.priam.model.dao.jpa.ProgrammeViewDao;
import fr.sacem.priam.model.domain.dto.ProgrammeDto;
import fr.sacem.priam.ui.rest.dto.ProgrammeCritereRecherche;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
/**
 * Created by benmerzoukah on 14/06/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = PriamWebAppTest.class)
@WebAppConfiguration
public class ProgrammeResourceTest {
    public static final String APP_REST_PROGRAMME_SEARCH = "/app/rest/programme/search";
    private MockMvc mockMvc;
    private HttpMessageConverter mappingJackson2HttpMessageConverter;
    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
                                                  MediaType.APPLICATION_JSON.getSubtype(),
                                                  Charset.forName("utf8"));
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ProgrammeViewDao programmeViewDao;
  
    @Autowired
    private ProgrammeDao programmeDao;


    @Autowired
    public void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
                    .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                    .findAny()
                    .orElse(null);

        assertNotNull("the JSON message converter must not be null", this.mappingJackson2HttpMessageConverter);
    }

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }
  
    @Test
    public void search_programmes() throws Exception {
        mockMvc.perform(post(APP_REST_PROGRAMME_SEARCH)
               .content(this.json(new ProgrammeCritereRecherche()))
               .contentType(contentType))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.content", hasSize(programmeViewDao.findAll().size())));
    }

    @Test
    public void search_programmes_by_numProg() throws Exception {
        ProgrammeCritereRecherche critereRecherche = new ProgrammeCritereRecherche();
        String pr170001 = "PR170001";
        critereRecherche.setNumProg(pr170001);
        mockMvc.perform(post(APP_REST_PROGRAMME_SEARCH)
               .content(this.json(critereRecherche))
               .contentType(contentType))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.content", hasSize(1)))
               .andExpect(jsonPath("$.content[0].numProg", is(pr170001)));
    }

    @Test
    public void search_programmes_by_rion_theorique() throws Exception {
        ProgrammeCritereRecherche critereRecherche = new ProgrammeCritereRecherche();
        critereRecherche.setRionTheorique("619");
        int expectedRion = 619;
        mockMvc.perform(post(APP_REST_PROGRAMME_SEARCH)
                .content(this.json(critereRecherche))
                .contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].rionTheorique", is(expectedRion)))
                .andExpect(jsonPath("$.content[1].rionTheorique", is(expectedRion)))
                .andExpect(jsonPath("$.content[2].rionTheorique", is(expectedRion)))
                .andExpect(jsonPath("$.content[3].rionTheorique", is(expectedRion)))
                .andExpect(jsonPath("$.content[4].rionTheorique", is(expectedRion)));
    }
    
    
    @Test
    public void search_programmes_by_famille() throws Exception {
        ProgrammeCritereRecherche critereRecherche = new ProgrammeCritereRecherche();

        String copiepriv = "COPIEPRIV";
        critereRecherche.setFamille(copiepriv);

        mockMvc.perform(post(APP_REST_PROGRAMME_SEARCH)
          .content(this.json(critereRecherche))
          .contentType(contentType))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.content[0].famille", is(copiepriv)))
          .andExpect(jsonPath("$.content[1].famille", is(copiepriv)))
          .andExpect(jsonPath("$.content[2].famille", is(copiepriv)))
          .andExpect(jsonPath("$.content[3].famille", is(copiepriv)))
          .andExpect(jsonPath("$.content[4].famille", is(copiepriv)));
    }


    @Test
    public void search_programmes_by_type_utilisation() throws Exception {
        ProgrammeCritereRecherche critereRecherche = new ProgrammeCritereRecherche();

        String typeUtil = "CPRIVAUDPL";
        critereRecherche.setTypeUtilisation(typeUtil);

        mockMvc.perform(post(APP_REST_PROGRAMME_SEARCH)
          .content(this.json(critereRecherche))
          .contentType(contentType))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.content[0].typeUtilisation", is(typeUtil)))
          .andExpect(jsonPath("$.content[1].typeUtilisation", is(typeUtil)))
          .andExpect(jsonPath("$.content[2].typeUtilisation", is(typeUtil)))
          .andExpect(jsonPath("$.content[3].typeUtilisation", is(typeUtil)))
          .andExpect(jsonPath("$.content[4].typeUtilisation", is(typeUtil)));
    }
    
    @Test
    public void find_programme_by_numProg() throws Exception {
      
      mockMvc.perform(
                    get("/app/rest/programme/numProg/PR170001")
                  .contentType(contentType))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.numProg", is("PR170001")));
       
    }
  
    @Test
    @Transactional
    public void add_programme() throws Exception {
      mockMvc.perform(
            post("/app/rest/programme/")
            .content(this.json(createProgrammeDto("Test01", "COPIEPRIV")))
            .contentType(contentType))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.famille.code", is("COPIEPRIV")))
          .andExpect(jsonPath("$.nom", is("Test01")));
      
    }
  
  protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);

        return mockHttpOutputMessage.getBodyAsString();
    }
  
  private ProgrammeDto createProgrammeDto(String nom, String famille) {
      ProgrammeDto dto = new ProgrammeDto();
      dto.setNom(nom);
      dto.setFamille(famille);
      
      return dto;
  }

}