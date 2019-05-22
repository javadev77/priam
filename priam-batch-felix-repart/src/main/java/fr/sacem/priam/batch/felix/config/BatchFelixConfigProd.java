package fr.sacem.priam.batch.felix.config;

import fr.sacem.priam.common.constants.EnvConstants;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;

/**
 * Created by benmerzoukah on 04/12/2017.
 */
@Configuration
@ComponentScan(basePackages = {"fr.sacem.priam.batch.felix", "fr.sacem.priam.model"})
@ImportResource(value = "classpath:config/job-configuration.xml")
@Profile({"re7", "prod", "dev"})
public class BatchFelixConfigProd {

    @Bean
    public Map<String, String> configAdmap(){
        Map<String, String> configAdmap = new HashMap<>();

        configAdmap.put(EnvConstants.FELIX_PREPREP_DIR.property(), String.valueOf(EnvConstants.FELIX_PREPREP_DIR));

        return configAdmap;
    }

    @Bean
    public DataSource dataSource() {
        JndiDataSourceLookup jndiDataSourceLookup = new JndiDataSourceLookup();
        jndiDataSourceLookup.setResourceRef(Boolean.TRUE);
        String priamDatasourceJndi = EnvConstants.PRIAM_DB_JNDI.toString();
        DataSource dataSource = jndiDataSourceLookup.getDataSource(priamDatasourceJndi);

        return dataSource;
    }
}