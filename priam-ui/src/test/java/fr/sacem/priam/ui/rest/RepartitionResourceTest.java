package fr.sacem.priam.ui.rest;

import fr.sacem.priam.model.dao.jpa.FichierFelixDao;
import fr.sacem.priam.model.domain.FichierFelix;
import fr.sacem.priam.model.domain.StatutFichierFelix;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static fr.sacem.priam.ui.rest.LigneProgrammeCPResourceTest.APP_REST_LIGNE_PROGRAMME_TITRES;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by benmerzoukah on 14/11/2017.
 */
public class RepartitionResourceTest extends RestResourceTest {

  private static final String APP_REST_GET_FICHIER_FELIX = "/app/rest/repartition/fichierfelix/{numProg}";
  private static final String APP_REST_CREATE_FICHIER_FELIX = "/app/rest/repartition/validateFelixData/{numProg}";

  @Autowired
  private FichierFelixDao fichierFelixDao;

  @Test
  @Transactional
  public void runAsyncCreateFichierFelix() throws Exception {

    FichierFelix ff = createMockFichierFelix("170001", StatutFichierFelix.EN_COURS);

    mockMvc.perform(
      get(APP_REST_CREATE_FICHIER_FELIX, "170001")
        .contentType(contentType))
      .andExpect(status().isOk());
  }

  private FichierFelix createMockFichierFelix(String numProg, StatutFichierFelix statut) {
    FichierFelix ff = new FichierFelix();
    ff.setDateCreation(new Date());
    ff.setStatut(statut);
    ff.setNumProg(numProg);

    fichierFelixDao.save(ff);
    fichierFelixDao.flush();

    return ff;
  }

  @Test
  public void getFichierFelix() throws Exception {
    mockMvc.perform(
      get(APP_REST_GET_FICHIER_FELIX, "170001")
        .contentType(contentType))

      .andExpect(status().isOk());

  }

  @Test
  public void generateFelixDataWithErrors() throws Exception {
  }

  @Test
  public void downloadFichierFelixRepartitionABlan() throws Exception {
  }

  @Test
  public void generateFelixData() throws Exception {
  }

}
