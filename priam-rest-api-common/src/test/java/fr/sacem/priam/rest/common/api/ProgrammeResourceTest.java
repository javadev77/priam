package fr.sacem.priam.rest.common.api;

import fr.sacem.priam.model.dao.jpa.FichierDao;
import fr.sacem.priam.model.dao.jpa.ProgrammeViewDao;
import fr.sacem.priam.model.domain.dto.ProgrammeDto;
import fr.sacem.priam.rest.common.api.dto.ProgrammeCritereRecherche;
import fr.sacem.priam.rest.common.config.RestResourceTest;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by benmerzoukah on 14/06/2017.us
 */
public class ProgrammeResourceTest extends RestResourceTest {
    public static final String APP_REST_PROGRAMME_SEARCH = "/app/rest/programme/search";

    @Autowired
    private ProgrammeViewDao programmeViewDao;

    @Autowired
    private FichierDao fichierDao;


    @Test
    public void search_programmes() throws Exception {
        ProgrammeCritereRecherche o = new ProgrammeCritereRecherche();
        o.setFamille("ALL");
        o.setTypeUtilisation("ALL");
        mockMvc.perform(post(APP_REST_PROGRAMME_SEARCH)
               .content(this.json(o))
               .contentType(contentType))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.totalPages",is(1)))
               .andExpect(jsonPath("$.totalElements", is(6)));
    }

    @Test
    public void search_programmes_by_numProg() throws Exception {
        ProgrammeCritereRecherche critereRecherche = new ProgrammeCritereRecherche();
        String pr170001 = "170001";
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
                .andExpect(jsonPath("$.content[0].rionTheorique", is(expectedRion)));
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
          .andExpect(jsonPath("$.content[0].famille", is(copiepriv)));
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
                    get("/app/rest/programme/numProg/170001")
                  .contentType(contentType))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.numProg", is("170001")));

    }

    @Test
    public void should_nom_programme_exist() throws Exception {
      String pr01 = "Programme 01";
      mockMvc.perform(
        get("/app/rest/programme/nom/"+ pr01)
          .contentType(contentType))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", is(true)));

    }

    @Test
    public void should_nom_programme_not_exist() throws Exception {
      mockMvc.perform(
        get("/app/rest/programme/nom/notfound")
          .contentType(contentType))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", is(false)));

    }

    @Test
    @Transactional
    @Ignore
    public void add_programme() throws Exception {
      mockMvc.perform(
            post("/app/rest/programme/")
            .content(this.json(createProgrammeDto("Test02", "COPIEPRIV", "CPRIVSONPH")))
            .contentType(contentType))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.famille.code", is("COPIEPRIV")))
          .andExpect(jsonPath("$.nom", is("Test02")));

    }

    @Test
    @Transactional
    @Ignore
    public void test_abandonner_programme() throws Exception {
      mockMvc.perform(
        put("/app/rest/programme/abandon")
          .content(this.json(createProgrammeDtoWithNumProg("180090","Test01", "COPIEPRIV")))
                //.content("")
          .contentType(contentType))
        .andExpect(status().isOk());
        //.andExpect(jsonPath("$.statut", is("ABANDONNE")));

    }


  @Test
  @Transactional
  public void test_getAllNomProgForAutocmplete() throws Exception {
    mockMvc.perform(
      get("/app/rest/programme/nomprog/autocomplete")
        .contentType(contentType))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$[0]", is("PROG FD06 TEST")));

  }

  @Test
  @Transactional
  public void test_getAllNumProgForAutocmplete() throws Exception {
    mockMvc.perform(
      get("/app/rest/programme/numprog/autocomplete")
        .contentType(contentType))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$[0]", is("170001")));

  }


    @Test
    @Transactional
    @Ignore
    public void test_modifier_programme() throws Exception {
        mockMvc.perform(
                put("/app/rest/programme/")
                        .content(this.json(createProgrammeDtoWithNumProg("170001","Test32", "COPIEPRIV")))
                        .contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom", is("Test32")));

    }


   private ProgrammeDto createProgrammeDtoWithNumProg(String numProg, String nom, String famille) {
        ProgrammeDto programmeDto = createProgrammeDto(nom, famille, null);
        programmeDto.setNumProg(numProg);

        return  programmeDto;
   }

    private ProgrammeDto createProgrammeDto(String nom, String famille, String typeUtil) {
        ProgrammeDto dto = new ProgrammeDto();
        dto.setNom(nom);
        dto.setFamille(famille);
        dto.setTypeUtilisation(typeUtil);

        dto.setDateDbtPrg(new Date());
        dto.setDateFinPrg(new Date());


        return dto;
    }

}
