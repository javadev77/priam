package fr.sacem.priam.batch.affectation.config;
import fr.sacem.domain.Admap;
import fr.sacem.priam.common.constants.EnvConstants;
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
@Profile({"prod"})
@PropertySource("classpath:config/applicatin.properties")
public class BatchConfigProd {

    @Value("${spring.datasource.url}")
    String urlDb;

    @Value("${spring.datasource.username}")
    String usernameDb;

    @Value("${spring.datasource.driver-class-name}")
    String driverDb;

    @Value("${spring.datasource.password}")
    String passwordDb;

    private String inputDirectory = String.valueOf(EnvConstants.OCTAV_ZIP_IN);
    private String outputDirectory = String.valueOf(EnvConstants.OCTAV_ZIP_ARCHIVES);


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
