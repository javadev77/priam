package fr.sacem.priam.batch.fv.affectation.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * Created by benmerzoukah on 04/12/2017.
 */
@Configuration
@ComponentScan(basePackages = {"fr.sacem.priam.batch.fv.affectation","fr.sacem.priam.batch.common"})
@Profile({"local", "test"})
@PropertySource("classpath:config/application-local.properties")
@ImportResource(value = "classpath:config/job-affectation-fv.xml")
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

    @Bean
    public PlatformTransactionManager transactionManager(){
        DataSourceTransactionManager dsTx = new DataSourceTransactionManager();
        dsTx.setDataSource(dataSource());

        return dsTx;
    }
}
