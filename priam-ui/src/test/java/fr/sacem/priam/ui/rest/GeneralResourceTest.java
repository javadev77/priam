package fr.sacem.priam.ui.rest;

import fr.sacem.priam.model.dao.jpa.SareftrRionDao;
import fr.sacem.priam.model.domain.saref.SareftrRion;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by benmerzoukah on 13/11/2017.
 */
public class GeneralResourceTest extends RestResourceTest {
  @Autowired
  SareftrRionDao sareftrRionDao;

  @Test
  public void getAllLibelleFamille() throws Exception {
      mockMvc.perform(
        get("/app/rest/general/libellefamille")
          .contentType(contentType))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id", is("COPIEPRIV")))
        .andExpect(jsonPath("$[0].value", is("Copie Privée")));
  }

  @Test
  public void getAllLibelleTypeUtilisation() throws Exception {
      mockMvc.perform(
        get("/app/rest/general/libelletypeutil")
          .contentType(contentType))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id", is("CPRIVSONPH")))
        .andExpect(jsonPath("$[0].value", is("Copie privée sonore Phono")))
        .andExpect(jsonPath("$[1].id", is("CPRIVSONRD")))
        .andExpect(jsonPath("$[1].value", is("Copie Privée Sonore radio")));
  }

  @Test
  public void getFamilleByTypeUtilisation() throws Exception {
    mockMvc.perform(
      get("/app/rest/general/familleByTypeUil")
        .contentType(contentType))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$['COPIEPRIV'][0].id", is("CPRIVSONPH")))
      .andExpect(jsonPath("$['COPIEPRIV'][1].id", is("CPRIVSONRD")));

  }

  @Test
  public void getRions() throws Exception {

    mockMvc.perform(
      get("/app/rest/general/rions")
        .contentType(contentType))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$[0].id", is("639")))
      .andExpect(jsonPath("$[0].value", is("639 - juillet 2018")));
  }

  @Test
  public void getRionsCreation() throws Exception {
    List<SareftrRion> sareftrRions = sareftrRionDao.findAllByDateRglmtAfterCurrentDate();
    mockMvc.perform(
      get("/app/rest/general/rions_creation")
        .contentType(contentType))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.length()", is(sareftrRions.size())))
      .andExpect(jsonPath("$[0].id", is(String.valueOf(sareftrRions.get(0).getRion()))));
  }

  @Test
  public void getAllTerritoire() throws Exception {
  }

  @Test
  public void getSsoToken() throws Exception {
  }

  @Test
  public void getMipsaConfig() throws Exception {
  }

  @Test
  public void getLibelleCdeUtilisateur() throws Exception {
  }

  @Test
  public void getCurrentUser() throws Exception {
  }

  @Test
  public void getParametrageByUser() throws Exception {
  }

  @Test
  public void setParametrageByUser() throws Exception {
  }

  @Test
  public void appInfoContext() throws Exception {
  }

}
