package fr.sacem.priam.batch.felix.config;

import fr.sacem.priam.common.constants.EnvConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.*;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by benmerzoukah on 04/12/2017.
 */
@Configuration
@ComponentScan(basePackages = {"fr.sacem.priam.batch.felix", "fr.sacem.priam.model"})
@ImportResource(value = "classpath:config/job-configuration.xml")
@Profile({"re7", "prod", "dev"})
@PropertySource("classpath:config/application-dev.properties")
public class BatchFelixConfigProd {

    @Bean
    public Map<String, String> configAdmap(){
        Map<String, String> configAdmap = new HashMap<>();

        configAdmap.put(EnvConstants.FELIX_PREPREP_DIR.property(), String.valueOf(EnvConstants.FELIX_PREPREP_DIR));

        return configAdmap;
    }
}
