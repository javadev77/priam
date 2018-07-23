package fr.sacem.priam.batch.purge.catcms.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.*;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = {"fr.sacem.priam.batch.purge.catcms", "fr.sacem.priam.batch.common"})
@Profile({"local"})
@PropertySource("classpath:config/application-local.properties")
@ImportResource(value = "classpath:config/job-configuration.xml")
public class ConfigurationPriamLocal {
    @Value("${spring.datasource.url}")
    String urlDb;

    @Value("${spring.datasource.username}")
    String usernameDb;

    @Value("${spring.datasource.driver-class-name}")
    String driverDb;

    @Value("${spring.datasource.password}")
    String passwordDb;

    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder
                .create()
                .username(usernameDb)
                .password(passwordDb)
                .url(urlDb)
                .driverClassName(driverDb)
                .build();
    }

    @Bean(name = "paramAnneeFr")
    public Long paramAnneeFr() {
        return 10L;
    }

    @Bean(name = "paramAnneeAnf")
    public Long paramAnneeAnf() {
        return 1L;
    }

    ConfigurationPriamLocal(){

    }
}
