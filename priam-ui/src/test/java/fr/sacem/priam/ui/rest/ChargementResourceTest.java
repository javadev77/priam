package fr.sacem.priam.ui.rest;

import fr.sacem.priam.model.dao.jpa.FichierDao;
import fr.sacem.priam.model.domain.Status;
import fr.sacem.priam.ui.rest.dto.AffectationCriteria;
import fr.sacem.priam.ui.rest.dto.InputChgtCriteria;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
      criteria.setNumProg("PR170001");
      criteria.setStatutCode(newArrayList(Status.AFFECTE.name()));
      
      mockMvc.perform(
        post("/app/rest/chargement/allFichiers")
          .content(this.json(criteria))
          .contentType(contentType))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].statut", is("AFFECTE")));
    }
    
    @Test
    public void deleteDonneesFichiers() throws Exception {
    }
  
}
