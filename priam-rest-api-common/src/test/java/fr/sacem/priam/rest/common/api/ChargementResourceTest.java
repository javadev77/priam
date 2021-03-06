package fr.sacem.priam.rest.common.api;

import static com.google.common.collect.Lists.newArrayList;
import fr.sacem.priam.model.dao.jpa.FichierDao;
import fr.sacem.priam.model.domain.Status;
import fr.sacem.priam.model.domain.dto.FileDto;
import fr.sacem.priam.rest.common.api.dto.AffectationCriteria;
import fr.sacem.priam.rest.common.api.dto.InputChgtCriteria;
import fr.sacem.priam.rest.common.config.RestResourceTest;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by benmerzoukah on 13/07/2017.
 */
public class ChargementResourceTest extends RestResourceTest {

  public static final String COPIEPRIV = "COPIEPRIV";

  @Autowired
  FichierDao fichierDao;

  @Test
    public void rechercheFichiers_by_famille() throws Exception {
      InputChgtCriteria criteria = new InputChgtCriteria();
      criteria.setFamilleCode(COPIEPRIV);
      mockMvc.perform(
                post("/app/rest/chargement/search")
               .content(this.json(criteria))
               .contentType(contentType))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].famille", is("COPIEPRIV")));
//          .andExpect(jsonPath("$.content", new BaseMatcher() {
//            @Override
//            public void describeTo(Description description) {
//
//            }
//
//            @Override
//            public boolean matches(Object o) {
//              JSONArray data = (JSONArray) o;
//              for (Iterator<Object> it = data.iterator(); it.hasNext(); ) {
//                Object element = it.next();
//                if(element instanceof JSONObject) {
//                  JSONObject element1 = (JSONObject) element;
//
//                }
//
//              }
//              return o instanceof JSONArray && data.size() == 4;
//            }
//          }));
    }

    @Test
    public void find_fichiers_affectes_by_numprog() throws Exception {
      AffectationCriteria criteria = new AffectationCriteria();
      criteria.setNumProg("170001");
      criteria.setStatutCode(newArrayList(Status.AFFECTE.name()));
      criteria.setFamilleCode("ALL");
      criteria.setTypeUtilisationCode("ALL");

      mockMvc.perform(
        post("/app/rest/chargement/allFichiers")
          .content(this.json(criteria))
          .contentType(contentType))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].statut", is("AFFECTE")));

      criteria.setNumProg("190004");
      mockMvc.perform(
              post("/app/rest/chargement/allFichiers")
                      .content(this.json(criteria))
                      .contentType(contentType))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$", hasSize(1)));

      criteria.setNumProg("190002");
      mockMvc.perform(
              post("/app/rest/chargement/allFichiers")
                      .content(this.json(criteria))
                      .contentType(contentType))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$", hasSize(1)));

        criteria.setNumProg("190005");
        mockMvc.perform(
                post("/app/rest/chargement/allFichiers")
                        .content(this.json(criteria))
                        .contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    @Transactional("transactionManager")
    public void deleteDonneesFichiers() throws Exception {
      FileDto fileDto = new FileDto();
      String fichier125 = "Fichier 125";
      fileDto.setId(fichierDao.findByNomFichier(fichier125).getId());

      mockMvc.perform(
           put("/app/rest/chargement/deleteFichier")
          .content(this.json(fileDto))
          .contentType(contentType))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.nomFichier", is(fichier125)))
        .andExpect(jsonPath("$.statut", is("ABANDONNE")));

    }

    public void findFichiersAffectes() throws Exception {

    }

}
