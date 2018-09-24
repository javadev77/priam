package fr.sacem.priam.common.health;

import fr.sacem.priam.common.constants.EnvConstants;
import org.springframework.stereotype.Component;

/**
 * Created by benmerzoukah on 19/09/2018.
 */
@Component
public class PriamAppHealthIndicator extends RemoteHealthIndicator {

  @Override
    protected String healthServiceContext() {
      return EnvConstants.PRIAM_APP_SERVICE_URL.toString();
    //return "http://dev.sacem.fr/priam-common";

    }
}
