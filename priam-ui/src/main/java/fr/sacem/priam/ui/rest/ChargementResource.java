package fr.sacem.priam.ui.rest;


import com.google.common.collect.Lists;
import fr.sacem.priam.model.dao.jpa.FichierDao;
import fr.sacem.priam.model.domain.Status;
import fr.sacem.priam.model.domain.dto.FileDto;
import fr.sacem.priam.services.FichierService;
import fr.sacem.priam.ui.rest.dto.ChargementCritereRecherche;
import fr.sacem.priam.ui.rest.dto.InputChgtCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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
    
    @RequestMapping(value = "/initCritereRecherche",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public ChargementCritereRecherche initCritereRecherche() {
    
        //TODO : HABIB - A compeleter
        return new ChargementCritereRecherche();
    }
  
    @RequestMapping(value = "/search",
                    method = RequestMethod.POST,
                    consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<FileDto> rechercheFichiers(@RequestBody InputChgtCriteria input, Pageable pageable) {
        List<Status> status = null;
        if(input.getStatutCode().isEmpty()) {
            status = Arrays.asList(Status.values());
        } else {
            status = Lists.transform(input.getStatutCode(), code -> Status.valueOf(code));
        }
  
        String codeFamille = null;
        if(!"ALL".equals(input.getFamilleCode())) {
            codeFamille = input.getFamilleCode();
        }
  
        String codeTypeUtil = null;
        if(!"ALL".equals(input.getTypeUtilisationCode())) {
          codeTypeUtil = input.getTypeUtilisationCode();
        }
        //Pageable realPageable = PagingUtil.parenthesisEncapsulation(pageable);
        return fichierDao.findAllFichiersByCriteria(codeFamille, codeTypeUtil, status,pageable);
    }
    
    @RequestMapping(value = "/deleteFichier/{fileId}",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public FileDto deleteDonneesFichiers(@PathVariable("fileId") Long fileId) {
        fichierService.deleteDonneesFichiers(fileId);
        FileDto fileDto = fichierDao.findById(fileId);
        logger.info("File = " + fileDto.getNomFichier());
        return fileDto;
    }
}
