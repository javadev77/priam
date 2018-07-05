package fr.sacem.priam.services.api;


import fr.sacem.priam.common.exception.InputValidationException;
import fr.sacem.priam.model.domain.Programme;
import fr.sacem.priam.model.domain.criteria.LigneProgrammeCriteria;
import fr.sacem.priam.model.domain.dto.KeyValueDto;
import fr.sacem.priam.model.domain.dto.ProgrammeDto;
import fr.sacem.priam.model.domain.dto.SelectionDto;
import fr.sacem.priam.security.model.UserDTO;
import fr.sacem.priam.services.ProgrammeService;
import fr.sacem.priam.services.dto.LigneProgrammeCritereRecherche;
import fr.sacem.priam.services.dto.ValdierSelectionProgrammeInput;
import fr.sacem.priam.services.journal.annotation.LogOeuvre;
import fr.sacem.priam.services.journal.annotation.LogSuppressionOeuvre;
import fr.sacem.priam.services.journal.annotation.TypeLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by benmerzoukah on 07/12/2017.
 */
public abstract class LigneProgrammeResource {

    private static final String ALL = "Tous";
    public static final String SELECTIONNE = "Sélectionné";

    @Autowired
    private ProgrammeService programmeService;

    public LigneProgrammeResource() {
    }

    @RequestMapping(value = "ligneProgramme/search",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    Page<? extends SelectionDto> findLigneProgrammeByCritere(@RequestBody LigneProgrammeCritereRecherche ligneProgramme, Pageable pageable){

        LigneProgrammeCriteria criteria = new LigneProgrammeCriteria();

        criteria.setNumProg(ligneProgramme.getNumProg());
        criteria.setIde12(ligneProgramme.getIde12());

        if(ligneProgramme.getTitre() != null && !ligneProgramme.getTitre().isEmpty())
            criteria.setTitre(ligneProgramme.getTitre());

        if(ligneProgramme.getAjout() != null && !ALL.equals(ligneProgramme.getAjout()))
            criteria.setAjout(ligneProgramme.getAjout());

        if(ligneProgramme.getSelection() != null && !ALL.equals(ligneProgramme.getSelection()))
            criteria.setSelection(SELECTIONNE.equals(ligneProgramme.getSelection()));

        if(ligneProgramme.getUtilisateur() != null && !ligneProgramme.getUtilisateur().isEmpty() && !ALL.equals(ligneProgramme.getUtilisateur()))
            criteria.setUtilisateur(ligneProgramme.getUtilisateur().split(" - ")[0]);

        return getLigneProgrammeService().findLigneProgrammeByCriteria(criteria, pageable);

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

        return getLigneProgrammeService().getListIDE12ByProgramme(ide12, programme);
    }


    @RequestMapping(value = "ligneProgramme/titreOeuvre",
            method = RequestMethod.GET,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<KeyValueDto> getTitresByProgramme(@RequestParam(value = "q") String query, @RequestParam(value = "programme") String programme) {
        return getLigneProgrammeService().getTitresByProgramme(query, programme);
    }

    @RequestMapping(value = "ligneProgramme/selection/valider",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public List<String> validerSelection(@RequestBody ValdierSelectionProgrammeInput input, UserDTO userDTO) {

        if(input == null || input.getNumProg() == null || input.getNumProg().isEmpty())
            throw new InputValidationException("input or num programme must not be null !");

        ProgrammeDto programmeDTO = new ProgrammeDto();
        programmeDTO.setNumProg(input.getNumProg());

        programmeDTO.setUserValidation(userDTO.getDisplayName());
        getLigneProgrammeService().enregistrerEdition(input.getNumProg());

        programmeService.validerProgramme(programmeDTO, userDTO);

        return new ArrayList<>();
    }

    @RequestMapping(value = "ligneProgramme/selection/modifier",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<String> modifierSelection(@RequestBody ValdierSelectionProgrammeInput input, UserDTO userDTO) {

        if(input == null || input.getNumProg() == null || input.getNumProg().isEmpty())
            throw new InputValidationException("input or num programme must not be null !");

        ProgrammeDto programmeDTO = new ProgrammeDto();
        programmeDTO.setNumProg(input.getNumProg());
        programmeDTO.setUsermaj(userDTO.getDisplayName());
        programmeService.invaliderProgramme(programmeDTO, userDTO);

        modifierSelection(input, input.getNumProg());

        return new ArrayList<>();
    }

    @RequestMapping(value = "ligneProgramme/selection/temporaire/modifier",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<String> modifierSelectionTemporaire(@RequestBody ValdierSelectionProgrammeInput input, UserDTO userDTO) {

        if(input == null || input.getNumProg() == null || input.getNumProg().isEmpty())
            throw new InputValidationException("input or num programme must not be null !");

        ProgrammeDto programmeDTO = new ProgrammeDto();
        programmeDTO.setNumProg(input.getNumProg());


        modifierSelection(input, input.getNumProg());

        return new ArrayList<>();
    }

    private void modifierSelection(@RequestBody ValdierSelectionProgrammeInput input, String numProg) {
        if (!input.getSelected().isEmpty()) {
            getLigneProgrammeService().selectLigneProgramme(numProg, input.getSelected());
        }
        if (!input.getUnselected().isEmpty()) {
            getLigneProgrammeService().deselectLigneProgramme(numProg, input.getUnselected());
        }
    }

    @RequestMapping(value = "ligneProgramme/selection/invalider",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public List<String> invaliderSelection(@RequestBody String numProg, UserDTO userDTO) {

        if(numProg == null || numProg.isEmpty())
            throw new InputValidationException("num programme must not be empty !");

        ProgrammeDto programmeDTO = new ProgrammeDto();
        programmeDTO.setNumProg(numProg);
        programmeDTO.setUsermaj(userDTO.getDisplayName());
        programmeService.invaliderProgramme(programmeDTO, userDTO);

        return new ArrayList<>();
    }

    @RequestMapping(value = "ligneProgramme/selection/annuler",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public List<String> annulerSelection(@RequestBody ValdierSelectionProgrammeInput input, UserDTO userDTO) {

        if(input == null || input.getNumProg() == null || input.getNumProg().isEmpty())
            throw new InputValidationException("input or num programme must not be null !");

        ProgrammeDto programmeDTO = new ProgrammeDto();
        programmeDTO.setNumProg(input.getNumProg());
        programmeDTO.setUseraffecte(userDTO.getDisplayName());

        Programme programme = programmeService.updateStatutProgrammeToAffecte(programmeDTO, userDTO);
        getLigneProgrammeService().annulerSelection(input.getNumProg(), userDTO.getDisplayName());


        return new ArrayList<>();
    }

    @RequestMapping(value = "ligneProgramme/{numProg}/{ide12}",
            method = RequestMethod.DELETE,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @LogSuppressionOeuvre(event = TypeLog.SUPPRESSION_OEUVRE)
    public boolean supprimerLigneProgramme(@PathVariable(name = "numProg") String numProg,
                                           @PathVariable(name = "ide12") Long ide12,
                                           @RequestBody SelectionDto selectedLigneProgramme, UserDTO userDto) {
        getLigneProgrammeService().supprimerLigneProgramme(numProg, ide12, selectedLigneProgramme);

        return true;
    }


    @RequestMapping(value = "ligneProgramme/selection/enregistrerEdition",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @LogOeuvre(event = TypeLog.SELECTION)
    public void enregistrerEdition(@RequestBody ValdierSelectionProgrammeInput input, UserDTO userDTO) {
        if(input == null || input.getNumProg() == null || input.getNumProg().isEmpty())
            throw new InputValidationException("input or num programme must not be null !");

        ProgrammeDto programmeDTO = new ProgrammeDto();
        programmeDTO.setNumProg(input.getNumProg());
        programmeDTO.setUsermaj(userDTO.getDisplayName());
        programmeService.enregistrerSelection(programmeDTO);

        getLigneProgrammeService().enregistrerEdition(input, userDTO);
    }


    @RequestMapping(value = "ligneProgramme/selection/annulerEdition",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public void annulerEdition(@RequestBody ValdierSelectionProgrammeInput input, UserDTO userDto) {
        getLigneProgrammeService().annulerEdition(input.getNumProg(), userDto.getDisplayName());
    }

    public abstract LigneProgrammeService getLigneProgrammeService();

}
