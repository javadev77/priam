package fr.sacem.priam.rest.common.api;

import fr.sacem.priam.common.exception.TechnicalException;
import fr.sacem.priam.model.dao.jpa.FichierFelixDao;
import fr.sacem.priam.model.domain.FichierFelix;
import fr.sacem.priam.model.domain.StatutFichierFelix;
import fr.sacem.priam.model.domain.dto.ProgrammeDto;
import fr.sacem.priam.rest.common.config.RestResourceTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.NestedServletException;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
  public void arunAsyncCreateFichierFelix() throws Exception {
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

  @Test(expected = Exception.class)
  public void generateFelixDataWithErrors_KO() throws Exception {

    mockMvc.perform(
            post("/app/rest/repartition/downloadFichierFelixError")
                    .contentType(contentType))

            .andExpect(status().isOk());
  }

  @Test
  @Transactional
  public void generateFelixDataWithErrors_OK() throws Exception {
    //FichierFelix ff = createMockFichierFelix("180001", StatutFichierFelix.EN_COURS);

    mockMvc.perform(
            post("/app/rest/repartition/downloadFichierFelixError?filename=test-f&numProg=180001")
                    .contentType(contentType))

            .andExpect(status().isOk());
  }

  @Test(expected = NestedServletException.class)
  @Transactional
  public void downloadFichierFelixRepartitionABlanc_KO() throws Exception {
    mockMvc.perform(
            post("/app/rest/repartition/downloadFichierFelix?numProg=170001")
                    .contentType(contentType))

            .andExpect(status().isOk());
  }

  @Test
  @Transactional
  public void generateFelixData() throws Exception {

    ProgrammeDto pr = new ProgrammeDto();
    pr.setNumProg("170001");

    mockMvc.perform(
            post("/app/rest/repartition/generateFelixData")
                    .content(json(pr))
                    .contentType(contentType))

            .andExpect(status().isOk());
  }

}
