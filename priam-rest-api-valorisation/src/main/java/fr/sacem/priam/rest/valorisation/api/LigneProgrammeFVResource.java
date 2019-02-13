package fr.sacem.priam.rest.valorisation.api;

import fr.sacem.priam.model.domain.dto.SelectionDto;
import fr.sacem.priam.model.domain.fv.LigneProgrammeFV;
import fr.sacem.priam.security.model.UserDTO;
import fr.sacem.priam.services.api.LigneProgrammeResource;
import fr.sacem.priam.services.api.LigneProgrammeService;
import fr.sacem.priam.services.dto.ValdierSelectionProgrammeInput;
import fr.sacem.priam.services.fv.LigneProgrammeFVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/app/rest/")
@Profile({"dev", "prod","re7"})
public class LigneProgrammeFVResource extends LigneProgrammeResource {

    private LigneProgrammeService ligneProgrammeService;

    @Autowired
    @Qualifier("ligneProgrammeFVService")
    private LigneProgrammeFVService ligneProgrammeFVService;

    @Autowired
    public LigneProgrammeFVResource(@Qualifier("ligneProgrammeFVService")LigneProgrammeService ligneProgrammeService) {
        this.ligneProgrammeService = ligneProgrammeService;

    }

    @RequestMapping(value = "ligneProgramme/selection/compteurs",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> getDurDifProgramme(@RequestParam(value = "numProg") String numProg, @RequestParam(value = "statut") String statut) {
        return ligneProgrammeFVService.calculerCompteurs(numProg,statut);
    }

    @Override
    public LigneProgrammeService getLigneProgrammeService() {
        return this.ligneProgrammeService;
    }

    @Override
    public List<String> modifierSelectionTemporaire(@RequestBody ValdierSelectionProgrammeInput input, UserDTO userDTO) {
        super.modifierSelectionTemporaire(input, userDTO);
        String numProg = input.getNumProg();
        if (!input.getSelected().isEmpty()) {
            ligneProgrammeFVService.modifierPointsTemporaire(numProg, input.getSelected(), Boolean.TRUE, userDTO);
        }
        if (!input.getUnselected().isEmpty()) {
            ligneProgrammeFVService.modifierPointsTemporaire(numProg, input.getUnselected(), Boolean.FALSE, userDTO);
        }

        return new ArrayList<>();
    }

    @RequestMapping(value = "ligneProgramme/selection/ajoutOeuvre",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public SelectionDto ajouterOeuvreManuel(@RequestBody LigneProgrammeFV input, UserDTO userDTO) {
        input.setUtilisateur(userDTO.getUserId());

        ligneProgrammeFVService.ajouterOeuvreManuel(input, userDTO);

        return new SelectionDto();
    }
}
