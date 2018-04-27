package fr.sacem.priam.common.config;

import fr.sacem.priam.common.constants.EnvConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;

import javax.sql.DataSource;

@Configuration
@Profile({"dev", "re7", "prod"})
public class DataSourceConfig {

    @Bean
    @Primary
    public DataSource dataSource2() {
        JndiDataSourceLookup jndiDataSourceLookup = new JndiDataSourceLookup();
        jndiDataSourceLookup.setResourceRef(Boolean.TRUE);
        String priamDatasourceJndi = EnvConstants.PRIAM_DB_JNDI.toString();
        DataSource dataSource = jndiDataSourceLookup.getDataSource(priamDatasourceJndi);

        return dataSource;
    }

}
