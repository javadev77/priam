package fr.sacem.priam.ui.rest;

import fr.sacem.priam.model.dao.jpa.LigneProgrammeDao;
import fr.sacem.priam.model.domain.Programme;
import fr.sacem.priam.model.domain.criteria.LigneProgrammeCriteria;
import fr.sacem.priam.model.domain.dto.KeyValueDto;
import fr.sacem.priam.model.domain.dto.ProgrammeDto;
import fr.sacem.priam.model.domain.dto.SelectionDto;
import fr.sacem.priam.services.LigneProgrammeService;
import fr.sacem.priam.services.ProgrammeService;
import fr.sacem.priam.ui.rest.dto.LigneProgrammeCritereRecherche;
import fr.sacem.priam.ui.rest.dto.ValdierSelectionProgrammeInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

  @Autowired
  private LigneProgrammeDao ligneProgrammeDao;

  private static Logger logger = LoggerFactory.getLogger(LigneProgrammeResource.class);
/*
  @RequestMapping(value = "ligneProgramme/numprog",
    method = RequestMethod.POST,
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
  Page<SelectionDto> findLigneProgrammeByProgrammeId(@RequestBody String numProg, Pageable pageable){

    Page<LigneProgramme> ligneProgrammes = ligneProgrammeDao.findLigneProgrammeByProgrammeId(numProg,pageable);
    Page<SelectionDto> dtoPage =ligneProgrammes.map(convert());
    logger.info("Nbr de lignes programmes pour le programme :"+numProg+" est de :"+ ligneProgrammes.getTotalElements());

    return dtoPage;
  }
*/
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
      criteria.setUtilisateur(ligneProgramme.getUtilisateur());

    Page<SelectionDto> ligneProgrammes = ligneProgrammeService.findLigneProgrammeByCriteria(criteria,pageable);

    //Page<SelectionDto> dtoPage =ligneProgrammes.map(convert());
    //logger.info("Nbr de lignes programmes pour le programme :"+ligneProgramme.getNumProg()+" est de :"+ ligneProgrammes.getTotalElements());

    return ligneProgrammes;
  }

  private boolean parseSelection(String selection) {
    return SELECTIONNE.equals(selection);
  }

/*
  private Converter<LigneProgramme, SelectionDto> convert() {
    return source -> {
      SelectionDto selectionDto=new  SelectionDto();
      selectionDto.setRoleParticipant1(source.getRoleParticipant1());
      selectionDto.setIde12(source.getIde12());
      selectionDto.setAjout(source.getAjout());
      selectionDto.setDuree(source.getDurDif());
      selectionDto.setNomParticipant1(source.getNomParticipant1());
      selectionDto.setUtilisateur(source.getUtilisateur());
      selectionDto.setQuantite(0l);
      selectionDto.setTitreOeuvre(source.getTitreOeuvre());
      selectionDto.setSelection(source.getSelection());
      selectionDto.setId(source.getId());
      return selectionDto;
    };
  }
*/
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

    modifierSelection(input, programme);

    return new ArrayList<>();
  }

  private void modifierSelection(@RequestBody ValdierSelectionProgrammeInput input, Programme programme) {

    if(input.isDeselectAll()) {
      ligneProgrammeService.deselectAll(programme.getNumProg());
    } else
    if(input.isAll()) {
      ligneProgrammeService.selectAll(programme.getNumProg());
    } else if (!input.getSelected().isEmpty()) {
      ligneProgrammeService.selectLigneProgramme(programme.getNumProg(), input.getSelected());
    } else if (!input.getUnselected().isEmpty()) {
      ligneProgrammeService.selectAllLigneProgrammeExcept(programme.getNumProg(), input.getUnselected());
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

    modifierSelection(input, programme);

    return new ArrayList<>();
  }

  @RequestMapping(value = "ligneProgramme/{numProg}/{ide12}",
    method = RequestMethod.DELETE,
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
  public boolean supprimerLigneProgramme(@PathVariable(name = "numProg") String numProg, @PathVariable(name = "ide12") Long ide12) {
    ligneProgrammeService.supprimerLigneProgramme(numProg, ide12);
    return true;
  }


}
