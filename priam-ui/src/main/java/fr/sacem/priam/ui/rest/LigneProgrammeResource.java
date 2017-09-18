package fr.sacem.priam.ui.rest;

import fr.sacem.priam.model.domain.LigneProgramme;
import fr.sacem.priam.model.domain.Programme;
import fr.sacem.priam.model.domain.criteria.LigneProgrammeCriteria;
import fr.sacem.priam.model.domain.dto.KeyValueDto;
import fr.sacem.priam.model.domain.dto.ProgrammeDto;
import fr.sacem.priam.model.domain.dto.SelectionDto;
import fr.sacem.priam.services.LigneProgrammeService;
import fr.sacem.priam.services.ProgrammeService;
import fr.sacem.priam.ui.rest.dto.LigneProgrammeCritereRecherche;
import fr.sacem.priam.ui.rest.dto.ValdierSelectionProgrammeInput;
import fr.sacem.priam.ui.rest.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by fandis on 18/07/2017.
 */
@RestController
@RequestMapping("/app/rest/")
public class LigneProgrammeResource {

  private static final String ALL = "Tous";
  public static final String SELECTIONNE = "Sélectionné";

  @Autowired
  private LigneProgrammeService ligneProgrammeService;

  @Autowired
  private ProgrammeService programmeService;

  private static Logger logger = LoggerFactory.getLogger(LigneProgrammeResource.class);

  @RequestMapping(value = "ligneProgramme/search",
    method = RequestMethod.POST,
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
  Page<SelectionDto> findLigneProgrammeByCritere(@RequestBody LigneProgrammeCritereRecherche ligneProgramme, Pageable pageable){

    LigneProgrammeCriteria criteria = new LigneProgrammeCriteria();

    criteria.setNumProg(ligneProgramme.getNumProg());
    criteria.setIde12(ligneProgramme.getIde12());

    if(ligneProgramme.getTitre() != null && !ligneProgramme.getTitre().isEmpty())
      criteria.setTitre(ligneProgramme.getTitre());

    if(ligneProgramme.getAjout() != null && !ALL.equals(ligneProgramme.getAjout()))
      criteria.setAjout(ligneProgramme.getAjout());

    if(ligneProgramme.getSelection() != null && !ALL.equals(ligneProgramme.getSelection()))
      criteria.setSelection(parseSelection(ligneProgramme.getSelection()));

    if(ligneProgramme.getUtilisateur() != null && !ligneProgramme.getUtilisateur().isEmpty() && !ALL.equals(ligneProgramme.getUtilisateur()))
      criteria.setUtilisateur(ligneProgramme.getUtilisateur().split(" - ")[0]);

    Page<SelectionDto> ligneProgrammes = ligneProgrammeService.findLigneProgrammeByCriteria(criteria,pageable);

    return ligneProgrammes;
  }

  private boolean parseSelection(String selection) {
    return SELECTIONNE.equals(selection);
  }

  @RequestMapping(value = "ligneProgramme/ide12",
    method = RequestMethod.GET,
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
  public List<KeyValueDto> getListIDE12ByProgramme(@RequestParam(value = "q") String query, @RequestParam(value = "programme") String programme) {

    Long ide12 ;

    try {
      ide12 = Long.parseLong(query);
    }catch (NumberFormatException ex) {
      return new ArrayList<>();
    }

    return ligneProgrammeService.getListIDE12ByProgramme(ide12, programme);
  }


  @RequestMapping(value = "ligneProgramme/titreOeuvre",
    method = RequestMethod.GET,
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
  public List<KeyValueDto> getTitresByProgramme(@RequestParam(value = "q") String query, @RequestParam(value = "programme") String programme) {
    return ligneProgrammeService.getTitresByProgramme(query, programme);
  }

  @RequestMapping(value = "ligneProgramme/utilisateurs",
    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
  public List<String> getUtilisateursByProgramme(@RequestParam(value = "programme") String programme) {
    return ligneProgrammeService.getUtilisateursByProgramme(programme);
  }

  @RequestMapping(value = "ligneProgramme/selection/valider",
    method = RequestMethod.POST,
    produces = MediaType.APPLICATION_JSON_VALUE,
    consumes = MediaType.APPLICATION_JSON_VALUE
  )
  public List<String> validerSelection(@RequestBody ValdierSelectionProgrammeInput input) {

    if(input == null || input.getNumProg() == null || input.getNumProg().isEmpty())
      throw new RuntimeException("input or num programme must not be null !");

    ProgrammeDto programmeDTO = new ProgrammeDto();
    programmeDTO.setNumProg(input.getNumProg());

    Programme programme = programmeService.validerProgramme(programmeDTO);

    //modifierSelection(input, programme);

    return new ArrayList<>();
  }

  @RequestMapping(value = "ligneProgramme/selection/modifier",
    method = RequestMethod.POST,
    produces = MediaType.APPLICATION_JSON_VALUE,
    consumes = MediaType.APPLICATION_JSON_VALUE
  )
  public List<String> modifierSelection(@RequestBody ValdierSelectionProgrammeInput input) {

    if(input == null || input.getNumProg() == null || input.getNumProg().isEmpty())
      throw new RuntimeException("input or num programme must not be null !");

    ProgrammeDto programmeDTO = new ProgrammeDto();
    programmeDTO.setNumProg(input.getNumProg());

    Programme programme = programmeService.invaliderProgramme(programmeDTO);

    modifierSelection(input, input.getNumProg());

    return new ArrayList<>();
  }

  private void modifierSelection(@RequestBody ValdierSelectionProgrammeInput input, String numProg) {

      /*if(input.isDeselectAll()) {
        ligneProgrammeService.deselectAll(programme.getNumProg());
      } else
      if(input.isAll()) {
        ligneProgrammeService.selectAll(programme.getNumProg());
      } else if (!input.getSelected().isEmpty()) {
        ligneProgrammeService.selectLigneProgramme(programme.getNumProg(), input.getSelected());
      } else if (!input.getUnselected().isEmpty()) {
        ligneProgrammeService.selectAllLigneProgrammeExcept(programme.getNumProg(), input.getUnselected() );
  
  
      }*/
  
      if (!input.getSelected().isEmpty()) {
        ligneProgrammeService.selectLigneProgramme(numProg, input.getSelected());
      }
      if (!input.getUnselected().isEmpty()) {
        ligneProgrammeService.deselectLigneProgramme(numProg, input.getUnselected());
      }
  }

  @RequestMapping(value = "ligneProgramme/selection/invalider",
    method = RequestMethod.POST,
    produces = MediaType.APPLICATION_JSON_VALUE,
    consumes = MediaType.APPLICATION_JSON_VALUE
  )
  public List<String> invaliderSelection(@RequestBody String numProg) {

    if(numProg == null || numProg.isEmpty())
      throw new RuntimeException("num programme must not be empty !");

    ProgrammeDto programmeDTO = new ProgrammeDto();
    programmeDTO.setNumProg(numProg);

    Programme programme = programmeService.invaliderProgramme(programmeDTO);

    return new ArrayList<>();
  }

  @RequestMapping(value = "ligneProgramme/selection/annuler",
    method = RequestMethod.POST,
    produces = MediaType.APPLICATION_JSON_VALUE,
    consumes = MediaType.APPLICATION_JSON_VALUE
  )
  public List<String> annulerSelection(@RequestBody ValdierSelectionProgrammeInput input) {

    if(input == null || input.getNumProg() == null || input.getNumProg().isEmpty())
      throw new RuntimeException("input or num programme must not be null !");

    ProgrammeDto programmeDTO = new ProgrammeDto();
    programmeDTO.setNumProg(input.getNumProg());

    Programme programme = programmeService.updateStatutProgrammeToAffecte(programmeDTO);
    ligneProgrammeService.selectAll(programme.getNumProg());

    modifierSelection(input, programme.getNumProg());

    return new ArrayList<>();
  }

  @RequestMapping(value = "ligneProgramme/{numProg}/{ide12}",
    method = RequestMethod.DELETE,
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
  public boolean supprimerLigneProgramme(@PathVariable(name = "numProg") String numProg,
                                         @PathVariable(name = "ide12") Long ide12,
                                         @RequestBody SelectionDto selectedLigneProgramme) {
    ligneProgrammeService.supprimerLigneProgramme(numProg, ide12, selectedLigneProgramme);
    
    return true;
  }
  
  
  
    @RequestMapping(value = "ligneProgramme/selection/ajoutOeuvre",
                    method = RequestMethod.POST,
                    produces = MediaType.APPLICATION_JSON_VALUE,
                    consumes = MediaType.APPLICATION_JSON_VALUE)
    public SelectionDto ajouterOeuvreManuel(@RequestBody LigneProgramme input, UserDTO userDTO) {
         input.setUtilisateur(userDTO.getUserId());
         ligneProgrammeService.ajouterOeuvreManuel(input);
         
         return new SelectionDto();
    }
    
  
    @RequestMapping(value = "ligneProgramme/durdifAllSelect",
      method = RequestMethod.POST,
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Long> calculerDureeAllSelection(@RequestBody ValdierSelectionProgrammeInput input) {
      return ligneProgrammeService.calculerDureeAllSelection(input.getNumProg(), input.getSelected(), input.isAll());
    }
  
  @RequestMapping(value = "ligneProgramme/selection/enregistrerEdition",
    method = RequestMethod.POST,
    consumes = MediaType.APPLICATION_JSON_VALUE)
  public void enregistrerEdition(@RequestBody ValdierSelectionProgrammeInput input) {
      ligneProgrammeService.enregistrerEdition(input.getNumProg());
  }
  
  
  @RequestMapping(value = "ligneProgramme/selection/annulerEdition",
    method = RequestMethod.POST,
    consumes = MediaType.APPLICATION_JSON_VALUE)
  public void annulerEdition(@RequestBody ValdierSelectionProgrammeInput input) {
    ligneProgrammeService.annulerEdition(input.getNumProg());
  }
  


}
