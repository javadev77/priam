package fr.sacem.priam.ui.rest;


import fr.sacem.priam.model.dao.jpa.*;
import fr.sacem.priam.model.domain.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

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

    @Autowired
    RionDao rionDao;

    @Autowired
    private TerritoireDao territoireDao;

    @RequestMapping(value = "/libellefamille",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> [] getAllLibelleFamille(Locale locale) {
        List<LibelleFamille> labels = libelleFamilleDao.findByLang(StringUtils.upperCase(locale.getLanguage()));
        List<Map<String, String>> result = new ArrayList<>(labels.size());

        labels.forEach(libelle -> {
            Map<String, String> map;
            map = new HashMap<>();
            map.put("id", libelle.getCode());
            map.put("value", libelle.getLibelle());

            result.add(map);
        });


        return result.toArray(new Map[0]);
    }

    @RequestMapping(value = "/libelletypeutil",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public  Map<String, String> [] getAllLibelleTypeUtilisation(Locale locale) {
        List<LibelleTypeUtilisation> labels = libelleTypeUtilisationDao.findByLang(StringUtils.upperCase(locale.getLanguage()));

        List<Map<String, String>> result = new ArrayList<>(labels.size());
        labels.forEach(libelle -> {
          Map<String, String> map;
          map = new HashMap<>();
          map.put("id", libelle.getCode());
          map.put("value", libelle.getLibelle());

          result.add(map);
        });

      return result.toArray(new Map[0]);
    }

    @RequestMapping(value = "/familleByTypeUil",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Map<String, String> []> getFamilleByTypeUtilisation(Locale locale) {
        List<Famille> all = familleDao.findAll();

        Map<String, Map<String, String> []> result = new HashMap<>();

        String lang = StringUtils.upperCase(locale.getLanguage());
        all.forEach( famille -> {
            List<String> typeUtilCodes = transform(famille.getTypeUtilisations(), typeUtilisation -> typeUtilisation.getCode());
            result.put(famille.getCode(), getLibelleTypeUtilisationByCodes(typeUtilCodes, lang));
        });

        return result;
    }

    private Map<String, String> [] getLibelleTypeUtilisationByCodes(List<String> typeUtilCodes, String lang) {
        List<LibelleTypeUtilisation> byCodeAndLang = libelleTypeUtilisationDao.findByCodeAndLang(typeUtilCodes, lang);
        List<Map<String, String>> result = new ArrayList<>(byCodeAndLang.size());
        byCodeAndLang.forEach(libelle -> {

            Map<String, String> map;
            map = new HashMap<>();
            map.put("id", libelle.getCode());
            map.put("value", libelle.getLibelle());

            result.add(map);
          });

        return result.toArray(new Map[0]);
    }

    @RequestMapping(value = "/rions",
      method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String>[] getRions(Locale locale) {
      List<Rion> rions = rionDao.findAfterRion(639);
      List<Map<String, String>> result = new ArrayList<>(rions.size());
      rions.forEach(rion -> {

        Map<String, String> map = new HashMap<>();
        map.put("id", String.valueOf(rion.getRion()));
        map.put("value", String.valueOf(rion.getRion()));

        result.add(map);
      });

      return result.toArray(new Map[0]);
    }


  @RequestMapping(value = "/territoire",
    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
  public  Map<String, String> [] getAllTerritoire(Locale locale) {
    List<Territoire> territoires = territoireDao.findByLang(StringUtils.upperCase(locale.getLanguage()));

    List<Map<String, String>> result = new ArrayList<>(territoires.size());
    territoires.forEach(libelle -> {
      Map<String, String> map;
      map = new HashMap<>();
      map.put("id", libelle.getCdePaysIso4N()+"");
      map.put("value", new StringBuilder()
                        .append(libelle.getCdePaysIso4N())
                        .append(" - ")
                        .append(libelle.getNomPaysAbr())
                        .toString()
      );

      result.add(map);
    });

    return result.toArray(new Map[0]);
  }

}
