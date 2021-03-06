package fr.sacem.priam.batch.fv.octav.req.config;

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
@ComponentScan(basePackages = {
    "fr.sacem.priam.batch.fv.octav.req",
    "fr.sacem.priam.batch.common.dao",
    "fr.sacem.priam.batch.common.fv",
    "fr.sacem.priam.batch.common.domain"})
@Profile({"dev","local"})
@PropertySource("classpath:config/application-local.properties")
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
