package fr.sacem.priam.ui.rest;

import fr.sacem.priam.common.exception.InputValidationException;
import fr.sacem.priam.model.dao.jpa.cp.LigneProgrammeCPDao;
import fr.sacem.priam.model.domain.cp.LigneProgrammeCP;
import fr.sacem.priam.model.domain.dto.KeyValueDto;
import fr.sacem.priam.model.domain.dto.SelectionDto;
import fr.sacem.priam.services.LigneProgrammeService;
import fr.sacem.priam.ui.rest.dto.LigneProgrammeCritereRecherche;
import fr.sacem.priam.ui.rest.dto.ValdierSelectionProgrammeInput;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.NestedServletException;

import java.util.*;

import static org.fest.assertions.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by belwidanej on 10/08/2017.
 */
public class LigneProgrammeCPResourceTest extends RestResourceTest{

  @Rule
  public ExpectedException exception = ExpectedException.none();

  private static final Pageable pageable = new Pageable() {

    @Override
    public int getPageNumber() {
      return 0;
    }

    @Override
    public int getPageSize() {
      return 5;
    }

    @Override
    public int getOffset() {
      return 0;
    }

    @Override
    public Sort getSort() {
      return null;
    }

    @Override
    public Pageable next() {
      return null;
    }

    @Override
    public Pageable previousOrFirst() {
      return null;
    }

    @Override
    public Pageable first() {
      return null;
    }

    @Override
    public boolean hasPrevious() {
      return false;
    }
  };

  public static final String APP_REST_LIGNE_PROGRAMME_SEARCH = "/app/rest/ligneProgramme/search";
  public static final String APP_REST_LIGNE_PROGRAMME_IDE12 = "/app/rest/ligneProgramme/ide12";
  public static final String APP_REST_LIGNE_PROGRAMME_TITRES = "/app/rest/ligneProgramme/titreOeuvre";
  public static final String APP_REST_GET_UTILISATEURS_BY_PROGRAMME = "/app/rest/ligneProgramme/utilisateurs";

  public static final String APP_REST_VALIDER_SELECTION = "/app/rest/ligneProgramme/selection/valider";
  public static final String APP_REST_MODIFIER_SELECTION = "/app/rest/ligneProgramme/selection/modifier";
  public static final String APP_REST_INVALIDER_SELECTION = "/app/rest/ligneProgramme/selection/invalider";
  public static final String APP_REST_ANNULER_SELECTION = "/app/rest/ligneProgramme/selection/annuler";
  public static final String APP_REST_SUPPRIMER_LIGNE_PROGRAMME = "/app/rest/ligneProgramme/";
  public static final String APP_REST_AJOUTER_OEUVRE_MANUEL = "/app/rest/ligneProgramme/selection/ajoutOeuvre";
  public static final String APP_REST_ENREGISTRER_EDITION = "/app/rest/ligneProgramme/selection/enregistrerEdition";
  public static final String APP_REST_ANNULER_EDITION = "/app/rest/ligneProgramme/selection/annulerEdition";




  private static final String NUM_PROG = "170001";
  private static final Long INITIAL_IDE12 = 772L;
  private static final Long IDE12 = 6829877211L;
  private static final String INITIAL_TITRES = "Tes";

  @Autowired
  private LigneProgrammeService ligneProgrammeService;


  @Autowired
  private LigneProgrammeCPDao ligneProgrammeViewDao;


  @Test
  public void findLigneProgrammeByCritere() throws Exception {

    LigneProgrammeCritereRecherche ligneProgrammeCritereRecherche = new LigneProgrammeCritereRecherche();
    ligneProgrammeCritereRecherche.setNumProg(NUM_PROG);

    Page<SelectionDto> ligneProgrammeByCriteria = ligneProgrammeViewDao.findLigneProgrammeByCriteria(NUM_PROG, null, null, null, null, null, pageable);

    mockMvc.perform(post(APP_REST_LIGNE_PROGRAMME_SEARCH)
      .content(this.json(ligneProgrammeCritereRecherche))
      .contentType(contentType))
      .andExpect(status().isOk())
      /*.andExpect(jsonPath("$.content", hasSize(ligneProgrammeByCriteria.getSize())))*/;
  }

  @Test
  public void getListIDE12ByProgramme() throws Exception {

    List<KeyValueDto> ide12sByProgramme = ligneProgrammeViewDao.findIDE12sByProgramme(INITIAL_IDE12, NUM_PROG);

    mockMvc.perform(get(APP_REST_LIGNE_PROGRAMME_IDE12+"?q="+INITIAL_IDE12+"&programme="+NUM_PROG)
      .content(this.json(ide12sByProgramme))
      .contentType(contentType))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$[0].code", is(ide12sByProgramme.get(0).getCode())));
  }

  @Test
  public void getTitresByProgramme() throws Exception {
    List<KeyValueDto> titresByProgramme = ligneProgrammeViewDao.findTitresByProgramme(INITIAL_TITRES, NUM_PROG);

    mockMvc.perform(get(APP_REST_LIGNE_PROGRAMME_TITRES+"?q="+INITIAL_TITRES+"&programme="+NUM_PROG)
      .content(this.json(titresByProgramme))
      .contentType(contentType))
      .andExpect(status().isOk());
  }

  @Test
  public void getUtilisateursByProgramme() throws Exception {
    List<String> titresByProgramme = ligneProgrammeViewDao.findUtilisateursByProgramme(NUM_PROG);

    mockMvc.perform(get(APP_REST_GET_UTILISATEURS_BY_PROGRAMME+"?programme="+NUM_PROG)
      .content(this.json(titresByProgramme))
      .contentType(contentType))
      .andExpect(status().isOk());
  }

  @Test
  public void validerSelection() throws Exception {

    ValdierSelectionProgrammeInput input = new ValdierSelectionProgrammeInput();
    input.setAll(true);
    input.setNumProg(NUM_PROG);

    mockMvc.perform(post(APP_REST_VALIDER_SELECTION)
      .content(this.json(input))
      .contentType(contentType))
      .andExpect(status().isOk());
  }

  @Test
  public void testValiderSelectionEmptyOrNullInput() throws Exception {

    exception.expect(NestedServletException.class);
    ValdierSelectionProgrammeInput in = new ValdierSelectionProgrammeInput();
    mockMvc.perform(post(APP_REST_VALIDER_SELECTION)
      .content(json(in))
      .contentType(contentType))
      .andExpect(status().isInternalServerError());


  }

  @Test
  public void testModifierSelectionEmptyOrNullInput() throws Exception {

    exception.expect(NestedServletException.class);
     mockMvc.perform(post(APP_REST_MODIFIER_SELECTION)
      .content(this.json(new ValdierSelectionProgrammeInput()))
      .contentType(contentType))
      .andExpect(status().isInternalServerError());

  }


  @Test
  public void testInvaliderSelectionnEmptyOrNullInput() throws Exception {

    exception.expect(NestedServletException.class);
    mockMvc.perform(post(APP_REST_INVALIDER_SELECTION)
      .content(this.json(""))
      .contentType(contentType))
      .andExpect(status().isInternalServerError());
  }


  @Test
  public void modifierSelection() throws Exception {

    ValdierSelectionProgrammeInput input = new ValdierSelectionProgrammeInput();
    input.setAll(false);
    input.setNumProg(NUM_PROG);
    Set<Map<String, String>> inselectedSet = new HashSet<>();
    Map<String, String> selection = new HashMap<>();
    selection.put("ide12", 6829877211L+"");
    selection.put("libAbrgUtil", "");
    inselectedSet.add(selection);
    input.setUnselected(inselectedSet);

    mockMvc.perform(post(APP_REST_MODIFIER_SELECTION)
      .content(this.json(input))
      .contentType(contentType))
      .andExpect(status().isOk());
  }

  @Test
  public void invaliderSelection() throws Exception {

    mockMvc.perform(post(APP_REST_INVALIDER_SELECTION)
      .content(this.json(Integer.valueOf(NUM_PROG)))
      .contentType(contentType))
      .andExpect(status().isOk());
  }

  @Test
  public void annulerSelection() throws Exception {

    ValdierSelectionProgrammeInput input = new ValdierSelectionProgrammeInput();
    input.setNumProg(NUM_PROG);

    mockMvc.perform(post(APP_REST_ANNULER_SELECTION)
      .content(this.json(input))
      .contentType(contentType))
      .andExpect(status().isOk());
  }

  @Test
  public void supprimerLigneProgramme() throws Exception {

    LigneProgrammeCP input = createLigneProgramme("170001", 1454545L, "LU1");

    ligneProgrammeService.ajouterOeuvreManuel(input);

    SelectionDto se = new SelectionDto();
    se.setIde12(1454545L);
    se.setCdeUtil("LU1");

    mockMvc.perform(
      delete(APP_REST_SUPPRIMER_LIGNE_PROGRAMME + "170001/" + String.valueOf(se.getIde12()))
        .content(this.json(se))
      .contentType(contentType))
      .andExpect(status().isOk());

    LigneProgrammeCP lu1 = ligneProgrammeViewDao.findOeuvreManuelByIde12AndCdeUtil("170001", 1454545L, "LU1");
    assertThat(lu1).isNull();

    ligneProgrammeService.supprimerLigneProgramme(input.getNumProg(), input.getIde12(), se);
  }

  @Test
  @Transactional
  public void testAjouterOeuvreManuel() throws Exception {
    long ide12 = 124578L;
    LigneProgrammeCP input = createLigneProgramme("170001", ide12, "LU1");

    mockMvc.perform(
      post(APP_REST_AJOUTER_OEUVRE_MANUEL)
        .content(this.json(input))
        .contentType(contentType))
      .andExpect(status().isOk());

    LigneProgrammeCP lu1 = ligneProgrammeViewDao.findOeuvreManuelByIde12AndCdeUtil("170001", ide12, "LU1");
    assertThat(lu1).isNotNull();
    assertThat(lu1.getIde12()).isEqualTo(ide12);
  }

  @Test
  @Transactional
  public void testEnregistrerEdition() throws Exception {

    ValdierSelectionProgrammeInput input = new ValdierSelectionProgrammeInput();
    input.setNumProg("170001");
    mockMvc.perform(
      post(APP_REST_ENREGISTRER_EDITION)
        .content(this.json(input))
        .contentType(contentType))
      .andExpect(status().isOk());

  }



  @Test
  public void testEnregistrerEditionEmptyOrNullInput() throws Exception {

    exception.expect(NestedServletException.class);
    mockMvc.perform(post(APP_REST_ENREGISTRER_EDITION)
      .content(this.json(new ValdierSelectionProgrammeInput()))
      .contentType(contentType))
      .andExpect(status().isInternalServerError());
  }

  @Test
  @Transactional
  public void testAnnulerEdition() throws Exception {

    ValdierSelectionProgrammeInput input = new ValdierSelectionProgrammeInput();
    input.setNumProg("170001");
    mockMvc.perform(
      post(APP_REST_ANNULER_EDITION)
        .content(this.json(input))
        .contentType(contentType))
      .andExpect(status().isOk());

  }

  @Test
  public void testAnnulerSelectionnEmptyOrNullInput() throws Exception {

    exception.expect(NestedServletException.class);
    mockMvc.perform(post(APP_REST_ANNULER_SELECTION)
      .content(this.json(new ValdierSelectionProgrammeInput ()))
      .contentType(contentType))
      .andExpect(status().isInternalServerError());
  }

  @Test
  public void testGetListIDE12ParseError() throws Exception {

    List<KeyValueDto> ide12sByProgramme = ligneProgrammeViewDao.findIDE12sByProgramme(INITIAL_IDE12, NUM_PROG);

    mockMvc.perform(get(APP_REST_LIGNE_PROGRAMME_IDE12+"?q=&programme="+NUM_PROG)
      .content(this.json(ide12sByProgramme))
      .contentType(contentType))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.length()", is(0)));
  }


  private LigneProgrammeCP createLigneProgramme(String numProg, Long ide12, String cdeUtil) {
    LigneProgrammeCP input = new LigneProgrammeCP();
    input.setIde12(ide12);
    input.setCdeUtil(cdeUtil);
    input.setNumProg(numProg);

    return input;
  }
}
