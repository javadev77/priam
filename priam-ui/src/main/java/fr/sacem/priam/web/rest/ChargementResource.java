package fr.sacem.priam.web.rest;

import fr.sacem.priam.web.rest.dto.ChargementCritereRecherche;
import fr.sacem.priam.web.rest.dto.FileData;
import fr.sacem.priam.web.rest.dto.InputChgtCriteria;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by benmerzoukah on 27/04/2017.
 */
@RestController
@RequestMapping("/app/rest/chargement")
public class ChargementResource {
  
  private static final int PAGE_SIZE = 10;
  private static List<FileData> FILES = new ArrayList<>();
    
    static {
      populateData();
    }
  
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
  
      return FILES;
    }
    
    private static void populateData() {
        
        for(int i=0; i<10; i++) {
  
          if(i % 2 == 0) {
            FILES.add(new FileData("Fichier " + i,
              "Copie prive",
              "Copie privee sono",
              new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()),
              new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()),
              180 * i,
              "En cours"
            ));
            
          } else {
            FILES.add(new FileData("Fichier " + i,
              "Copie prive",
              "Copie privee sono",
              new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()),
              new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()),
              170 * i,
              "Chargement OK")
            );
            
          }
  
        }
        
      
    }
}
