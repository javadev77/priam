package fr.sacem.priam.batch.affectation.cp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.*;

import javax.sql.DataSource;

/**
 * Created by benmerzoukah on 04/12/2017.
 */
@Configuration
@ComponentScan(basePackages = "fr.sacem.priam.batch.affectation.*")
@ImportResource(value = "classpath:config/job-configuration.xml")
@Profile({"local", "test"})
@PropertySource("classpath:config/application-dev.properties")
public class BatchConfigLocal {

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
}
