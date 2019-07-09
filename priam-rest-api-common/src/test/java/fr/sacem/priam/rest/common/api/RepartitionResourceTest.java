package fr.sacem.priam.rest.common.api;

import fr.sacem.priam.model.domain.dto.ProgrammeDto;
import fr.sacem.priam.rest.common.config.RestResourceTest;
import org.junit.Ignore;
import org.junit.Test;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by benmerzoukah on 14/11/2017.
 */
public class RepartitionResourceTest extends RestResourceTest {

  private static final String APP_REST_GET_FICHIER_FELIX = "/app/rest/repartition/fichierfelix/{numProg}";

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
            post("/app/rest/repartition/downloadFichierFelix")
                    .contentType(contentType))

            .andExpect(status().isOk());
  }

  @Test
  @Transactional("transactionManager")
  public void generateFelixDataWithErrors_OK() throws Exception {

    mockMvc.perform(
            post("/app/rest/repartition/downloadFichierFelix?filename=test-f&numProg=180001")
                    .contentType(contentType))

            .andExpect(status().isOk());
  }

  @Test
  @Transactional("transactionManager")
  public void downloadFichierFelixRepartitionABlanc_KO() throws Exception {
    mockMvc.perform(
            post("/app/rest/repartition/downloadFichierFelix?numProg=170001")
                    .contentType(contentType))

            .andExpect(status().isOk());
  }

  @Test
  @Ignore
  @Transactional("transactionManager")
  public void generateFelixData() throws Exception {

    ProgrammeDto pr = new ProgrammeDto();
    pr.setNumProg("170001");

    mockMvc.perform(
            get("/app/rest/repartition/generateFichierFelix?numProg=170001&modeRepartition=MISE_EN_REPART?typeRepart=OEUVRE")
                    .contentType(contentType))

            .andExpect(status().isOk());
  }

}
