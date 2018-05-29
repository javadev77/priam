package fr.sacem.priam.rest.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.HashMap;
import java.util.Map;

import static fr.sacem.priam.common.constants.EnvConstants.FELIX_PREPREP_DIR;

/**
 * Created by benmerzoukah on 16/03/2018.
 */
@Configuration
@Profile("test")
public class AdmapConfigTest {

    @Bean(name = "configAdmap")
    public Map<String, String> configAdmap() {
        Map<String, String> config = new HashMap<>();

        config.put(FELIX_PREPREP_DIR.property(), this.getClass().getResource("/temp").getFile());

        return config;
    }

}
