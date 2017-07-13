package fr.sacem.priam.ui.rest;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import fr.sacem.priam.model.dao.jpa.FichierDao;
import fr.sacem.priam.model.dao.jpa.ProgrammeViewDao;
import fr.sacem.priam.model.domain.Fichier;
import fr.sacem.priam.model.domain.Status;
import fr.sacem.priam.model.domain.dto.AffectationDto;
import fr.sacem.priam.model.domain.dto.ProgrammeDto;
import fr.sacem.priam.ui.rest.dto.ProgrammeCritereRecherche;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/**
 * Created by benmerzoukah on 14/06/2017.
 */
public class ProgrammeResourceTest extends RestResourceTest {
    public static final String APP_REST_PROGRAMME_SEARCH = "/app/rest/programme/search";

    @Autowired
    private ProgrammeViewDao programmeViewDao;
    
    @Autowired
    private FichierDao fichierDao;

  
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
    public void add_programme() throws Exception {
      mockMvc.perform(
            post("/app/rest/programme/")
            .content(this.json(createProgrammeDto("Test01", "COPIEPRIV")))
            .contentType(contentType))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.famille.code", is("COPIEPRIV")))
          .andExpect(jsonPath("$.nom", is("Test01")));
      
    }
  
    @Test
    @Transactional
    public void test_abandonner_programme() throws Exception {
      mockMvc.perform(
        put("/app/rest/programme/abandon")
          .content(this.json(createProgrammeDto("PR170001","Test01", "COPIEPRIV")))
          .contentType(contentType))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.statut", is("ABANDONNE")));
      
    }
  
  @Test
  @Transactional
  public void test_affecterFichiers() throws Exception {
    mockMvc.perform(
      put("/app/rest/programme/affectation")
        .content(this.json(createAffectationDto("PR170001", Arrays.asList("F01", "F02"))))
        .contentType(contentType))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.statut", is("AFFECTE")));
    
  }
  
  @Test
  @Transactional
  public void test_affecterFichiers_all_vide() throws Exception {
    mockMvc.perform(
      put("/app/rest/programme/affectation")
        .content(this.json(createAffectationDto("PR170001", Collections.emptyList())))
        .contentType(contentType))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.statut", is("CREE")));
    
  }
  
  
  @Test
  @Transactional
  public void test_deaffecterFichiers() throws Exception {
    mockMvc.perform(
      put("/app/rest/programme/toutDesaffecter")
        .content("PR170001")
        .contentType(contentType))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.statut", is("CREE")));
    
  }
  
  @Test
  @Transactional
  public void test_getAllNomProgForAutocmplete() throws Exception {
    mockMvc.perform(
      get("/app/rest/programme/nomprog/autocomplete")
        .contentType(contentType))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$[0]", is("Programme 01")));
    
  }
  
  @Test
  @Transactional
  public void test_getAllNumProgForAutocmplete() throws Exception {
    mockMvc.perform(
      get("/app/rest/programme/numprog/autocomplete")
        .contentType(contentType))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$[0]", is("PR170001")));
    
  }
  
  private AffectationDto createAffectationDto(String numProg, List<String> fichiers) {
      AffectationDto affectationDto = new AffectationDto();
      affectationDto.setNumProg(numProg);
      List<Fichier> transform = Lists.transform(fichiers, new Function<String, Fichier>() {
        @Override
        public Fichier apply(String f) {
          Fichier fichier = new Fichier();
          fichier.setNomFichier( f);
          fichier.setStatut(Status.CHARGEMENT_OK);
          
          fichierDao.save(fichier);
  
          return fichier;
        }
      });
      
      affectationDto.setFichiers(transform);
      
      return affectationDto;
    }
    private ProgrammeDto createProgrammeDto(String numProg, String nom, String famille) {
        ProgrammeDto programmeDto = createProgrammeDto(nom, famille);
        programmeDto.setNumProg(numProg);
        return  programmeDto;
    }
    
    private ProgrammeDto createProgrammeDto(String nom, String famille) {
        ProgrammeDto dto = new ProgrammeDto();
        dto.setNom(nom);
        dto.setFamille(famille);
        
        return dto;
    }

}
