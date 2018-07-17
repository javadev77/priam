package fr.sacem.priam.rest.common.api;

import fr.sacem.priam.model.dao.jpa.SareftjLibterDao;
import fr.sacem.priam.model.dao.jpa.SareftjLibutilDao;
import fr.sacem.priam.model.dao.jpa.SareftrRionDao;
import fr.sacem.priam.model.domain.saref.SareftjLibter;
import fr.sacem.priam.model.domain.saref.SareftjLibutil;
import fr.sacem.priam.model.domain.saref.SareftrRion;
import fr.sacem.priam.model.util.GlobalConstants;
import fr.sacem.priam.rest.common.config.RestResourceTest;
import fr.sacem.priam.security.model.UserDTO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static fr.sacem.priam.common.constants.EnvConstants.MIPSA_WEB_COMPONENT_HTML_URL;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by benmerzoukah on 13/11/2017.
 */
public class GeneralResourceTest extends RestResourceTest {

  @Autowired
  SareftrRionDao sareftrRionDao;

  @Autowired
  private SareftjLibterDao sareftjLibterDao;

  @Autowired
  private SareftjLibutilDao sareftjLibutilDao;

  @Test
  public void getAllLibelleFamille() throws Exception {
    TestingAuthenticationToken principal = new TestingAuthenticationToken(new UserDTO("benmerzoukah"), null);
    SecurityContextHolder.getContext().setAuthentication(principal);

    mockMvc.perform(
        get("/app/rest/general/libellefamille")
          .contentType(contentType)
              .principal(principal)
          )

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
        .andExpect(jsonPath("$[0].value", is("Copie Privée sonore Phono")))
        .andExpect(jsonPath("$[1].id", is("CPRIVSONRD")))
        .andExpect(jsonPath("$[1].value", is("Copie Privée sonore RD")));
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
    List<SareftjLibter> sareftjLibters = sareftjLibterDao.findByLang(GlobalConstants.FR_LANG);
    mockMvc.perform(
      get("/app/rest/general/territoire")
        .contentType(contentType))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.length()", is(sareftjLibters.size())))
      .andExpect(jsonPath("$[0].id", is(String.valueOf(sareftjLibters.get(0).getCdePaysIso4N()))));

  }

  @Test
  public void getMipsaConfig() throws Exception {

    mockMvc.perform(
      get("/app/rest/general/config/mipsa")
        .contentType(contentType))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$['" + MIPSA_WEB_COMPONENT_HTML_URL.property() +  "']", is(String.valueOf(MIPSA_WEB_COMPONENT_HTML_URL))));
  }

  @Test
  public void getLibelleCdeUtilisateur() throws Exception {
    List<SareftjLibutil> labels = sareftjLibutilDao.findByLang(GlobalConstants.FR_LANG);
    mockMvc.perform(
      get("/app/rest/general/libelleUtilisateur")
        .contentType(contentType))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.length()", is(labels.size())))
      .andExpect(jsonPath("$[0].id", is(String.valueOf(labels.get(0).getCdeUtil()))));
  }

  @Test
  public void getCurrentUser() throws Exception {

    mockMvc.perform(
      get("/app/rest/general/currentUser")
        .contentType(contentType))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.userId", is(UserDTO.GUEST.getUserId())))
      .andExpect(jsonPath("$.displayName", is(UserDTO.GUEST.getDisplayName())));
  }

  @Test
  public void getParametrageByUser() throws Exception {
    TestingAuthenticationToken principal = new TestingAuthenticationToken(new UserDTO("benmerzoukah"), null);
    SecurityContextHolder.getContext().setAuthentication(principal);

    mockMvc.perform(
      get("/app/rest/general/parametres")
        .contentType(contentType)
              .principal(principal))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$['USER_FAMILLE']", is("ALL,Toutes")));
  }

  @Test
  @Transactional
  public void setParametrageByUser() throws Exception {
    Map<String, String> params = new HashMap<>();
    params.put("USER_PAGE_SIZE", "30");


    mockMvc.perform(
      put("/app/rest/general/parametres")
        .content(this.json(params))
        .contentType(contentType))
      .andExpect(status().isOk());
  }

  @Test
  public void appInfoContext() throws Exception {
    mockMvc.perform(
      get("/app/rest/general/appinfo")
        .contentType(contentType))
      .andExpect(status().isOk());
  }

}
