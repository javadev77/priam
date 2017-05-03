package fr.sacem.priam.web.rest;

import com.google.common.collect.Lists;
import fr.sacem.priam.web.rest.dto.ChargementCritereRecherche;
import fr.sacem.priam.web.rest.dto.FileData;
import fr.sacem.priam.web.rest.dto.InputChgtCriteria;
import fr.sacem.priam.web.rest.dto.StatutFichier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * Created by benmerzoukah on 27/04/2017.
 */
@RestController
@RequestMapping("/app/rest/chargement")
public class ChargementResource {
  
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
      
        
        return populateData();
    }
    
    private List<FileData> populateData() {
        List<FileData> result = Lists.newArrayList();
        result.add(new FileData("Fichier 01",
                                "Copie prive",
                                "Copie privee sono",
                                 new Date(),
                                 new Date(),
                                 180,
                                new StatutFichier("EN_COURS", "En cours")
                                ));
      result.add(new FileData("Fichier 02",
                              "Copie prive",
                              "Copie privee sono",
                              new Date(),
                              new Date(),
                              280,
                              new StatutFichier("EN_COURS", "En cours")
      ));
  
      result.add(new FileData("Fichier 03",
                              "Copie prive",
                              "Copie privee sono",
                              new Date(),
                              new Date(),
                              390,
                              new StatutFichier("EN_COURS", "En cours")
      ));
  
  
      return result;
    }
}
