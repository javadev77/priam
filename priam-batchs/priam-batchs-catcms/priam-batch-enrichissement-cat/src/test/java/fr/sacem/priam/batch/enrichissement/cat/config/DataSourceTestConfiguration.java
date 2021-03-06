package fr.sacem.priam.batch.enrichissement.cat.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

/**
 * Created by fandis on 09/10/2017.
 */
@Configuration
@PropertySource(value = "classpath:configuration/application-test.properties")
@Profile("test")
public class DataSourceTestConfiguration {
    @Value("spring.datasource.url")
    String urlDb;
    @Value("spring.datasource.username")
    String usernameDb;
    @Value("spring.datasource.driver-class-name")
    String driverDb;
    @Value("spring.datasource.password")
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

    DataSourceTestConfiguration(){

    }
}
