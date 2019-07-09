package fr.sacem.priam.rest.common.api;

import com.google.common.collect.Lists;
import fr.sacem.priam.common.TypeUtilisationEnum;
import fr.sacem.priam.model.dao.jpa.FichierDao;
import fr.sacem.priam.model.domain.Status;
import fr.sacem.priam.model.domain.dto.FileDto;
import fr.sacem.priam.model.domain.fv.EnrichissementLog;
import fr.sacem.priam.model.domain.fv.StatutEnrichissementFV;
import fr.sacem.priam.rest.common.api.dto.AffectationCriteria;
import fr.sacem.priam.rest.common.api.dto.InputChgtCriteria;
import fr.sacem.priam.security.model.UserDTO;
import fr.sacem.priam.services.FichierService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by benmerzoukah on 27/04/2017.
 */
@RestController
@RequestMapping("/app/rest/chargement")
public class ChargementResource {

    private static Logger logger = LoggerFactory.getLogger(ChargementResource.class);

    @Autowired
    FichierDao fichierDao;

    @Autowired
    FichierService fichierService;


    @RequestMapping(value = "/search",
                    method = RequestMethod.POST,
                    consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<FileDto> rechercheFichiers(@RequestBody InputChgtCriteria input, UserDTO currentUser, Pageable pageable) {
        List<Status> status = statusCriterion(input);

        List<String> codeFamille = familleCriterion(input, currentUser);

        List<String> codeTypeUtil = typeUtilisationCriterion(input, currentUser);

        return fichierDao.findAllFichiersByCriteria(codeFamille, codeTypeUtil, status,pageable);
    }

    private List<String> familleCriterion(@RequestBody InputChgtCriteria input, UserDTO currentUser) {
        if(input.getFamilleCode() != null && !"ALL".equals(input.getFamilleCode())) {
            return Lists.newArrayList(input.getFamilleCode());
        } else {
            return currentUser.authorizedFamilles();
        }
    }

    @RequestMapping(value = "/allFichiers",
                    method = RequestMethod.POST,
                    consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public List<FileDto> findFichiersAffectes(@RequestBody AffectationCriteria input, UserDTO currentUser) {
        logger.info("findFichiersAffectes() - numProg=[" + input.getNumProg() + "]");
        List<Status> status = statusCriterion(input);
        List<String> codeFamille = familleCriterion(input, currentUser);
        List<String> codeTypeUtil = typeUtilisationCriterion(input, currentUser);
        String numProg = input.getNumProg();

        List<FileDto> fichiersAffectes = fichierDao.findFichiersAffectes(codeFamille, codeTypeUtil, status, numProg)
                    .stream()
                    .filter(f ->
                                    ("FDSVAL".equals(f.getFamille())
                            && StatutEnrichissementFV.DONE_SRV_AD_INFO == f.getStatutEnrichissementFV()
                            && (!TypeUtilisationEnum.FV_FONDS_06.getCode().equals(f.getTypeUtilisation())
                                ||!TypeUtilisationEnum.FV_FONDS_12.getCode().equals(f.getTypeUtilisation())))
                            ||
                                    ("FDSVAL".equals(f.getFamille())
                            && f.getStatutEnrichissementFV() == null
                            && (TypeUtilisationEnum.FV_FONDS_06.getCode().equals(f.getTypeUtilisation())
                            ||TypeUtilisationEnum.FV_FONDS_12.getCode().equals(f.getTypeUtilisation())))

                            ||
                                    (!"FDSVAL".equals(f.getFamille())
                            && f.getStatutEnrichissementFV() == null))
                            .collect(Collectors.toList());


        return fichiersAffectes;
    }


  private List<String> typeUtilisationCriterion(@RequestBody InputChgtCriteria input, UserDTO currentUser) {
    if(input.getTypeUtilisationCode() != null && !"ALL".equals(input.getTypeUtilisationCode())) {
      return Lists.newArrayList(input.getTypeUtilisationCode());
    }
    return currentUser.authorizedTypeUtilisations();
  }

  private List<Status> statusCriterion(@RequestBody InputChgtCriteria input) {
    List<Status> status = null;
    if(input.getStatutCode() == null || input.getStatutCode().isEmpty()) {
      status = Arrays.asList(Status.values());
    } else {
      status = Lists.transform(input.getStatutCode(), Status::valueOf);
    }
    return status;
  }

  @RequestMapping(value = "/deleteFichier",
                    method = RequestMethod.PUT,
                    consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public FileDto deleteDonneesFichiers(@RequestBody FileDto fileDtoBody) {
        Long fileId = fileDtoBody.getId();
        fichierService.deleteDonneesFichiers(fileId);
        FileDto fileDto = fichierDao.findById(fileId);
        logger.info("File = " + fileDto.getNomFichier());
        return fileDto;
    }

  @RequestMapping(value = "/{idFichier}/log",
    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
  public Set<String> getChargementLog(@PathVariable(name = "idFichier") Long idFichier) {
     return fichierService.getChargementLog(idFichier);
  }

}
