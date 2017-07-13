package fr.sacem.priam.ui.rest;


import com.google.common.collect.Lists;
import fr.sacem.priam.model.dao.jpa.FichierDao;
import fr.sacem.priam.model.domain.Status;
import fr.sacem.priam.model.domain.dto.FileDto;
import fr.sacem.priam.services.FichierService;
import fr.sacem.priam.ui.rest.dto.AffectationCriteria;
import fr.sacem.priam.ui.rest.dto.InputChgtCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

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
    public Page<FileDto> rechercheFichiers(@RequestBody InputChgtCriteria input, Pageable pageable) {
        List<Status> status = statusCriterion(input);
  
        String codeFamille = familleCriterion(input);
  
        String codeTypeUtil = typeUtilisationCriterion(input);
        
        return fichierDao.findAllFichiersByCriteria(codeFamille, codeTypeUtil, status,pageable);
    }
  
  private String familleCriterion(@RequestBody InputChgtCriteria input) {
    String codeFamille = null;
    if(!"ALL".equals(input.getFamilleCode())) {
        codeFamille = input.getFamilleCode();
    }
    return codeFamille;
  }
  
    @RequestMapping(value = "/allFichiers",
                    method = RequestMethod.POST,
                    consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public List<FileDto> findFichiersAffectes(@RequestBody AffectationCriteria input) {
        logger.info("findFichiersAffectes() - numProg=[" + input.getNumProg() + "]");
        List<Status> status = statusCriterion(input);
        String codeFamille = familleCriterion(input);
        String codeTypeUtil = typeUtilisationCriterion(input);
        String numProg = input.getNumProg();
        
        return fichierDao.findFichiersAffectes(codeFamille, codeTypeUtil, status, numProg);
    
    }
  
  private String typeUtilisationCriterion(@RequestBody InputChgtCriteria input) {
    String codeTypeUtil = null;
    if(!"ALL".equals(input.getTypeUtilisationCode())) {
      codeTypeUtil = input.getTypeUtilisationCode();
    }
    return codeTypeUtil;
  }
  
  private List<Status> statusCriterion(@RequestBody InputChgtCriteria input) {
    List<Status> status = null;
    if(input.getStatutCode() == null || input.getStatutCode().isEmpty()) {
      status = Arrays.asList(Status.values());
    } else {
      status = Lists.transform(input.getStatutCode(), code -> Status.valueOf(code));
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
}
