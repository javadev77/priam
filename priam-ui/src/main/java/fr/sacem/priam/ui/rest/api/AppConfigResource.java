/*
package fr.sacem.priam.ui.rest.api;

import fr.sacem.priam.common.constants.EnvConstants;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

*/
/**
 * Created by benmerzoukah on 23/01/2018.
 *//*


@RestController
@RequestMapping("/app/rest/appConfig")
public class AppConfigResource {

  @RequestMapping(method = RequestMethod.GET,
                 produces = MediaType.APPLICATION_JSON_VALUE)
  public Map<String, String> appConfig() {

    Map<String, String> appConfig = new HashMap<>();

    appConfig.put("priam.reader.common.url", String.valueOf(EnvConstants.PRIAM_SERVICE_REST_COMMON_URL));
    appConfig.put("priam.reader.cms.url", String.valueOf(EnvConstants.PRIAM_SERVICE_REST_CMS_URL));
    appConfig.put("priam.reader.cp.url", String.valueOf(EnvConstants.PRIAM_SERVICE_REST_CP_URL));


    return appConfig;
  }


}
*/
