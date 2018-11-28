package fr.sacem.priam.batch.affectation.config;

import fr.sacem.priam.batch.common.domain.Admap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.*;

import javax.sql.DataSource;

/**
 * Created by benmerzoukah on 04/12/2017.
 */
@Configuration
@ComponentScan(basePackages = {"fr.sacem.priam.batch.affectation.*", "fr.sacem.priam.model.dao.*"})
@Profile({"local", "test"})
@PropertySource("classpath:config/application-local.properties")
@ImportResource(value = "classpath:config/job-configuration.xml")
public class BatchConfigLocal {

    @Value("${spring.datasource.url}")
    String urlDb;

    @Value("${spring.datasource.username}")
    String usernameDb;

    @Value("${spring.datasource.driver-class-name}")
    String driverDb;

    @Value("${spring.datasource.password}")
    String passwordDb;

    @Value("${input.catalog.octav}")
    String inputDirectory;

    @Value("${archives.catalog.octav}")
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
}
