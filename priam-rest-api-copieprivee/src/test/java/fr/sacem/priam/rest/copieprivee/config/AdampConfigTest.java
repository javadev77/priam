package fr.sacem.priam.rest.copieprivee.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

import static fr.sacem.priam.common.constants.EnvConstants.FELIX_PREPREP_DIR;

/**
 * Created by benmerzoukah on 19/03/2018.
 */
@Configuration
public class AdampConfigTest {

    @Bean(name = "configAdmap")
    public Map<String, String> configAdmap() {
        Map<String, String> config = new HashMap<>();

        config.put(FELIX_PREPREP_DIR.property(), "/work");

        return config;
    }
}
