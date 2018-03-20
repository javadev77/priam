package fr.sacem.priam.rest.copieprivee.api;

import com.google.common.base.Strings;
import fr.sacem.priam.model.dao.jpa.SareftjLibutilDao;
import fr.sacem.priam.model.domain.cp.LigneProgrammeCP;
import fr.sacem.priam.model.domain.dto.SelectionDto;
import fr.sacem.priam.model.domain.saref.SareftjLibUtilPK;
import fr.sacem.priam.model.domain.saref.SareftjLibutil;
import fr.sacem.priam.model.util.GlobalConstants;
import fr.sacem.priam.security.model.UserDTO;
import fr.sacem.priam.services.api.LigneProgrammeResource;
import fr.sacem.priam.services.api.LigneProgrammeService;
import fr.sacem.priam.services.cp.LigneProgrammeCPService;
import fr.sacem.priam.services.dto.ValdierSelectionProgrammeInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
public class LigneProgrammeCPResource extends LigneProgrammeResource {

    private LigneProgrammeService ligneProgrammeService;

    @Autowired
    @Qualifier("ligneProgrammeCPService")
    private LigneProgrammeCPService ligneProgrammeCPService;

    @Autowired
    private SareftjLibutilDao sareftjLibutilDao;

    @Autowired
    public LigneProgrammeCPResource(@Qualifier("ligneProgrammeCPService")LigneProgrammeService ligneProgrammeService) {
        this.ligneProgrammeService = ligneProgrammeService;

    }


  @RequestMapping(value = "ligneProgramme/selection/ajoutOeuvre",
          method = RequestMethod.POST,
          produces = MediaType.APPLICATION_JSON_VALUE,
          consumes = MediaType.APPLICATION_JSON_VALUE)
  public SelectionDto ajouterOeuvreManuel(@RequestBody LigneProgrammeCP input, UserDTO userDTO) {
      String lang = GlobalConstants.FR_LANG;
      SareftjLibUtilPK pk = new SareftjLibUtilPK(lang, input.getCdeUtil());
      SareftjLibutil sareftjLibutil = sareftjLibutilDao.findOne(pk);

      String libAbrgUtil = sareftjLibutil.getLibAbrgUtil();
      input.setLibelleUtilisateur(sareftjLibutil.getCdeUtil() + (Strings.isNullOrEmpty(libAbrgUtil) ? "" :  " - " + libAbrgUtil));
      input.setUtilisateur(userDTO.getUserId());
      input.setSelectionEnCours(Boolean.TRUE);

      ligneProgrammeCPService.ajouterOeuvreManuel(input);

      return new SelectionDto();
  }

  @RequestMapping(value = "ligneProgramme/selection/durdif",
          method = RequestMethod.GET,
          produces = MediaType.APPLICATION_JSON_VALUE)
  public Map<String, Long> getDurDifProgramme(@RequestParam(value = "numProg") String numProg, @RequestParam(value = "statut") String statut) {
      return ligneProgrammeCPService.getDurDifProgramme(numProg,statut);
  }

    @RequestMapping(value = "ligneProgramme/utilisateurs",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> getUtilisateursByProgramme(@RequestParam(value = "programme") String programme) {
        return ligneProgrammeCPService.getUtilisateursByProgramme(programme);
    }




    @RequestMapping(value = "ligneProgramme/selection/temporaire/modifier",
                    method = RequestMethod.POST,
                    produces = MediaType.APPLICATION_JSON_VALUE,
                    consumes = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public List<String> modifierSelectionTemporaire(@RequestBody ValdierSelectionProgrammeInput input) {
        super.modifierSelectionTemporaire(input);
        String numProg = input.getNumProg();
        if (!input.getSelected().isEmpty()) {
            ligneProgrammeCPService.modifierDurOrNbrDifTemporaire(numProg, input.getSelected(), Boolean.TRUE);
        }
        if (!input.getUnselected().isEmpty()) {
            ligneProgrammeCPService.modifierDurOrNbrDifTemporaire(numProg, input.getUnselected(), Boolean.FALSE);
        }

        return new ArrayList<>();
    }


    @Override
    public LigneProgrammeService getLigneProgrammeService() {
        return this.ligneProgrammeService;
    }
}
