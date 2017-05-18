package fr.sacem.priam.ui.rest;


import fr.sacem.priam.model.dao.jpa.FamilleDao;
import fr.sacem.priam.model.dao.jpa.LibelleFamilleDao;
import fr.sacem.priam.model.dao.jpa.LibelleTypeUtilisationDao;
import fr.sacem.priam.model.domain.Famille;
import fr.sacem.priam.model.domain.LibelleFamille;
import fr.sacem.priam.model.domain.LibelleTypeUtilisation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.google.common.collect.Lists.transform;

/**
 * Created by benmerzoukah on 16/05/2017.
 */
@RestController
@RequestMapping("/app/rest/general")
public class GeneralResource {
  
    private static Logger logger = LoggerFactory.getLogger(GeneralResource.class);

    @Autowired
    LibelleFamilleDao libelleFamilleDao;
  
    @Autowired
    LibelleTypeUtilisationDao libelleTypeUtilisationDao;
    
    @Autowired
    FamilleDao familleDao;
  
  
    @RequestMapping(value = "/libellefamille",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> getAllLibelleFamille(Locale locale) {
        List<LibelleFamille> labels = libelleFamilleDao.findByLang(StringUtils.upperCase(locale.getLanguage()));
        Map<String, String> map = new HashMap<>();
        for(LibelleFamille libelle : labels){
          map.put(libelle.getCode(), libelle.getLibelle());
        }
        return map;
    }
  
    @RequestMapping(value = "/libelletypeutil",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> getAllLibelleTypeUtilisation(Locale locale) {
        List<LibelleTypeUtilisation> labels = libelleTypeUtilisationDao.findByLang(StringUtils.upperCase(locale.getLanguage()));
        Map<String, String> map = new HashMap<>();
        for(LibelleTypeUtilisation libelle : labels){
          map.put(libelle.getCode(), libelle.getLibelle());
        }
        return map;
    }
  
    @RequestMapping(value = "/familleByTypeUil",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, List<String>> getFamilleByTypeUtilisation() {
        List<Famille> all = familleDao.findAll();
        Map<String, List<String>> map = new HashMap<>();
        
        for(Famille famille : all){
          map.put(famille.getCode(), transform(famille.getTypeUtilisations(), typeUtilisation -> typeUtilisation.getCode()));
        }
        
        return map;
    }
  
}
