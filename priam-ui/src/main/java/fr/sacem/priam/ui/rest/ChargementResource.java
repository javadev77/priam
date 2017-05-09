package fr.sacem.priam.ui.rest;

import fr.sacem.priam.ui.dao.FichierDao;
import fr.sacem.priam.ui.rest.dto.ChargementCritereRecherche;
import fr.sacem.priam.ui.rest.dto.FileData;
import fr.sacem.priam.ui.rest.dto.InputChgtCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    public List<FileData> rechercheFichiers(@RequestBody InputChgtCriteria input) {
  
        //TODO : HABIB - A compeleter
  
      //int fromIndex = (pageId - 1) * PAGE_SIZE;
      //List<FileData> fileData = FILES.subList(fromIndex, (fromIndex + PAGE_SIZE) - 1);
      List<FileData> fichiers = fichierDao.findAllFichiersByName();
      logger.info("Fichiers " + fichiers);
      
      return fichiers;
    }
}
