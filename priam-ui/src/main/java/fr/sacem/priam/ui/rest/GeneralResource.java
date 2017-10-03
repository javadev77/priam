package fr.sacem.priam.ui.rest;


import com.google.common.collect.Maps;
import fr.sacem.priam.common.util.SsoUtils;
import fr.sacem.priam.model.dao.jpa.*;
import fr.sacem.priam.model.domain.*;
import fr.sacem.priam.model.domain.saref.*;
import fr.sacem.priam.services.ParametrageService;
import fr.sacem.priam.ui.rest.dto.UserDTO;
import fr.sacem.priam.ui.security.SsoAuthenticationToken;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

import static com.google.common.collect.Lists.transform;
import static fr.sacem.priam.common.constants.EnvConstants.*;

/**
 * Created by benmerzoukah on 16/05/2017.
 */
@RestController
@RequestMapping("/app/rest/general")
public class GeneralResource {

  private static final Integer RION_639 = 639 ;
  private static Logger logger = LoggerFactory.getLogger(GeneralResource.class);

    @Autowired
    LibelleFamilleDao libelleFamilleDao;

    @Autowired
    SareftjLibtyputilDao sareftjLibtyputilDao;

    @Autowired
    SareftrFamiltyputilDao sareftrFamiltyputilDao;

    @Autowired
    SareftrRionDao sareftrRionDao;

    @Autowired
    private SareftjLibterDao sareftjLibterDao;

    @Autowired
    private ParametrageService parametrageService;

    @Autowired
    private SareftjLibutilDao sareftjLibutilDao;

    @RequestMapping(value = "/libellefamille",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> [] getAllLibelleFamille(Locale locale) {
        List<SareftjLibfamiltyputil> labels = libelleFamilleDao.findByLang(StringUtils.upperCase(locale.getLanguage()));
        List<Map<String, String>> result = new ArrayList<>(labels.size());

        labels.forEach(libelle -> {
            result.add(createStringMap(libelle.getCode(), libelle.getLibelle()));
        });


      return result.toArray(new Map[0]);
    }

    @RequestMapping(value = "/libelletypeutil",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public  Map<String, String> [] getAllLibelleTypeUtilisation(Locale locale) {
        List<SareftjLibtyputil> labels = sareftjLibtyputilDao.findByLang(StringUtils.upperCase(locale.getLanguage()));

        List<Map<String, String>> result = new ArrayList<>(labels.size());
        labels.forEach(libelle -> {
          result.add(createStringMap(libelle.getCode(), libelle.getLibelle()));
        });

      return result.toArray(new Map[0]);
    }

    @RequestMapping(value = "/familleByTypeUil",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Map<String, String> []> getFamilleByTypeUtilisation(Locale locale) {
        List<SareftrFamiltyputil> all = sareftrFamiltyputilDao.findAll();

        Map<String, Map<String, String> []> result = new HashMap<>();

        String lang = StringUtils.upperCase(locale.getLanguage());
        all.forEach( famille -> {
            List<String> typeUtilCodes = transform(famille.getSareftrTyputils(), typeUtilisation -> typeUtilisation.getCode());
            result.put(famille.getCode(), getLibelleTypeUtilisationByCodes(typeUtilCodes, lang));
        });

        return result;
    }

    private Map<String, String> [] getLibelleTypeUtilisationByCodes(List<String> typeUtilCodes, String lang) {
        List<SareftjLibtyputil> byCodeAndLang = sareftjLibtyputilDao.findByCodeAndLang(typeUtilCodes, lang);
        List<Map<String, String>> result = new ArrayList<>(byCodeAndLang.size());
        byCodeAndLang.forEach(libelle -> {

            result.add(createStringMap(libelle.getCode(), libelle.getLibelle()));
          });

        return result.toArray(new Map[0]);
    }

    private Map<String, String> createStringMap(String code, String libelle) {
        Map<String, String> map;
        map = new HashMap<>();
        map.put("id", code);
        map.put("value", libelle);

        return map;
    }

    @RequestMapping(value = "/rions",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String>[] getRions(Locale locale) {
        List<SareftrRion> sareftrRions = sareftrRionDao.findAfterRion(RION_639);
        List<Map<String, String>> result = new ArrayList<>(sareftrRions.size());
        sareftrRions.forEach(rion -> {
          String rionValue = String.valueOf(rion.getRion());
          String formattedRion = rion.getDatrglmt() != null ? String.format("%s - %s", rionValue, new SimpleDateFormat("MMMM YYYY", locale).format(rion.getDatrglmt())) : rionValue;
          result.add(createStringMap(rionValue, formattedRion));
        });

        return result.toArray(new Map[0]);
    }

  @RequestMapping(value = "/rions_creation",
    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
  public Map<String, String>[] getRionsCreation(Locale locale) {
    List<SareftrRion> sareftrRions = sareftrRionDao.findAllByDateRglmtAfterCurrentDate();
    List<Map<String, String>> result = new ArrayList<>(sareftrRions.size());
    sareftrRions.forEach(rion -> {
      String rionValue = String.valueOf(rion.getRion());
      String formattedRion = rion.getDatrglmt() != null ? String.format("%s - %s", rionValue, new SimpleDateFormat("MMMM YYYY", locale).format(rion.getDatrglmt())) : rionValue;
      result.add(createStringMap(rionValue, formattedRion));
    });

    return result.toArray(new Map[0]);
  }

    @RequestMapping(value = "/territoire",
                   method = RequestMethod.GET,
                   produces = MediaType.APPLICATION_JSON_VALUE)
    public  Map<String, String> [] getAllTerritoire(Locale locale) {
        List<SareftjLibter> sareftjLibters = sareftjLibterDao.findByLang(StringUtils.upperCase(locale.getLanguage()));

        List<Map<String, String>> result = new ArrayList<>(sareftjLibters.size());
        sareftjLibters.forEach(libelle -> {

          result.add(createStringMap(libelle.getCdePaysIso4N() + "",
                                      new StringBuilder()
                                        .append(libelle.getCdePaysIso4N())
                                        .append(" - ")
                                        .append(libelle.getNomPaysAbr())
                                        .toString()));
        });

        return result.toArray(new Map[0]);
    }

    @RequestMapping(value = "/ssotoken",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public String getSsoToken() {
        return SsoUtils.getSsoToken() ;
    }

    @RequestMapping(value = "/config/mipsa",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> getMipsaConfig() {
        logger.debug("===== Request Mipsa Config ======");

        Map<String, String> conf = Maps.newHashMap();

        conf.put(MIPSA_WEB_COMPONENT_HTML_URL.property(), String.valueOf(MIPSA_WEB_COMPONENT_HTML_URL)) ;
        conf.put(MIPSA_WEB_COMPONENT_BASEURL.property(), String.valueOf(MIPSA_WEB_COMPONENT_BASEURL)) ;
        conf.put(MIPSA_WEB_COMPONENT_CDEDECL.property(), String.valueOf(MIPSA_WEB_COMPONENT_CDEDECL)) ;
        conf.put(MIPSA_WEB_COMPONENT_CDETYPINTERLOC.property(), String.valueOf(MIPSA_WEB_COMPONENT_CDETYPINTERLOC)) ;
        conf.put(MIPSA_WEB_COMPONENT_USESSOTOKEN.property(), String.valueOf(MIPSA_WEB_COMPONENT_USESSOTOKEN)) ;

        logger.info("MIPSA CONFIG = " + conf);

        return conf;
    }


    @RequestMapping(value = "/libelleUtilisateur",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
   public  Map<String, String> [] getLibelleCdeUtilisateur(Locale locale) {
      List<SareftjLibutil> labels = sareftjLibutilDao.findByLang(StringUtils.upperCase(locale.getLanguage()));

      List<Map<String, String>> result = new ArrayList<>(labels.size());
      labels.forEach(libelle -> {
          String cdeUtil = libelle.getCdeUtil();
          String libAbrgUtil = libelle.getLibAbrgUtil();

          result.add(createStringMap(cdeUtil, (StringUtils.isNotEmpty(libAbrgUtil) ? StringUtils.trimToEmpty(libAbrgUtil) : "") + "(" + cdeUtil + ")"));

      });

      return result.toArray(new Map[0]);
   }



    @RequestMapping(value = "/currentUser",
      method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO getCurrentUser(Locale locale) {

      SsoAuthenticationToken currentUser = getSsoAuthenticationToken();

        return currentUser.getUser();
    }

  @RequestMapping(value = "/parametres",
    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
  public Map<String, String> getParametrageByUser() {

    SsoAuthenticationToken currentUser = getSsoAuthenticationToken();
    return parametrageService.findByUserId(currentUser.getUser().getUserId());

  }

  @RequestMapping(value = "parametres",
    method = RequestMethod.PUT,
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
  public void setParametrageByUser(@RequestBody Map<String, String> parametres) {

    SsoAuthenticationToken currentUser = getSsoAuthenticationToken();

    for (Map.Entry<String, String> entry: parametres.entrySet()) {
      parametrageService.save(new Parametrage( entry.getValue(), entry.getKey(), currentUser.getUser().getUserId()));
    }

  }

  private SsoAuthenticationToken getSsoAuthenticationToken() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    return (SsoAuthenticationToken) auth;
  }

}
