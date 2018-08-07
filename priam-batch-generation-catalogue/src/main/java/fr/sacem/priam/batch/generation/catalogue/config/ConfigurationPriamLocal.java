package fr.sacem.priam.batch.generation.catalogue.config;

import fr.sacem.priam.batch.common.domain.Admap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.*;

import javax.sql.DataSource;


/**
 * Created by fandis on 09/10/2017.
 */
@Configuration
@ComponentScan(basePackages = {"fr.sacem.priam.batch.generation.catalogue"})
@ImportResource(value = "classpath:config/job-configuration.xml")
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
