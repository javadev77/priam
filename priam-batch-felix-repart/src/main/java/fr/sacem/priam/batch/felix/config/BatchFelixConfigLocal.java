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
@Profile({"local", "test", "dev"})
@PropertySource("classpath:config/application-dev.properties")
public class BatchFelixConfigLocal {

    @Value("${spring.datasource.url}")
    String urlDb;

    @Value("${spring.datasource.username}")
    String usernameDb;

    @Value("${spring.datasource.driver-class-name}")
    String driverDb;

    @Value("${spring.datasource.password}")
    String passwordDb;

    @Bean(name = "dataSource")
    public DataSource dataSource() {
        return DataSourceBuilder
                .create()
                .username(usernameDb)
                .password(passwordDb)
                .url(urlDb)
                .driverClassName(driverDb)
                .build();
    }

    @Bean
    public Map<String, String> configAdmap(){
        Map<String, String> configAdmap = new HashMap<>();

        configAdmap.put(EnvConstants.FELIX_PREPREP_DIR.property(), String.valueOf(EnvConstants.FELIX_PREPREP_DIR));

        return configAdmap;
    }
}
