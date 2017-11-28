package fr.sacem.priam.rest.api.common.web.rest;

import fr.sacem.priam.model.dao.jpa.FichierDao;
import fr.sacem.priam.model.domain.Status;
import fr.sacem.priam.model.domain.dto.FileDto;
import fr.sacem.priam.rest.api.common.RestResourceTest;
import fr.sacem.priam.rest.api.common.web.rest.dto.AffectationCriteria;
import fr.sacem.priam.rest.api.common.web.rest.dto.InputChgtCriteria;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    }

    @Test
    @Transactional
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

}
