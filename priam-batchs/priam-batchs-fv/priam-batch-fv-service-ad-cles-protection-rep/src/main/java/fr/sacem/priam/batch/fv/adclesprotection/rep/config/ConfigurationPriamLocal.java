package fr.sacem.priam.batch.fv.adclesprotection.rep.config;

import fr.sacem.priam.batch.common.domain.Admap;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by fandis on 09/10/2017.
 */
@Configuration
@ComponentScan(basePackages =
        {"fr.sacem.priam.batch.fv.adclesprotection.rep",
         "fr.sacem.priam.batch.common.dao",
         "fr.sacem.priam.batch.common.domain",
         "fr.sacem.priam.batch.common.fv"})

@Profile("local")
@PropertySource("classpath:config/application-${spring.profiles.active}.properties")
public class ConfigurationPriamLocal {

    @Value("${spring.datasource.url}")
    String urlDb;

    @Value("${spring.datasource.username}")
    String usernameDb;

    @Value("${spring.datasource.driver-class-name}")
    String driverDb;

    @Value("${spring.datasource.password}")
    String passwordDb;

    @Value("${csvFile}")
    String ouputCsvFile;

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

    @Bean
    public Admap admap(){
        Admap admap = new Admap();
        admap.setOutputFile(ouputCsvFile);
        return admap;
    }

    ConfigurationPriamLocal(){

    }
}
