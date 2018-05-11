package fr.sacem.priam.batch.penef.cms.ant.config;

import fr.sacem.priam.batch.common.domain.Admap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.*;

import javax.sql.DataSource;


/**
 * Created by fandis on 09/10/2017.
 */
@Configuration
@ComponentScan(basePackages = "fr.sacem.*")
@ImportResource(value = "classpath:config/job-configuration.xml")
@Profile({"dev","local","integ","dev2"})
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

    @Value("${input.archives}")
    String inputDirectory;

    @Value("${output.archives}")
    String outputDirectory;

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
        admap.setInputFile(inputDirectory);
        admap.setOutputFile(outputDirectory);
        return admap;
    }

    ConfigurationPriamLocal(){

    }
}
