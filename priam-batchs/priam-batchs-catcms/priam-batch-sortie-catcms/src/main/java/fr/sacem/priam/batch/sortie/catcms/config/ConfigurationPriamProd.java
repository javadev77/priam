package fr.sacem.priam.batch.sortie.catcms.config;

import fr.sacem.priam.common.constants.EnvConstants;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.*;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by fandis on 09/10/2017.
 */
@Configuration
@ComponentScan(basePackages = {"fr.sacem.priam.batch.sortie.catcms", "fr.sacem.priam.batch.common"})
@Profile("production")
@PropertySource("classpath:config/application-local.properties")
@ImportResource(value = "classpath:config/job-configuration.xml")
public class ConfigurationPriamProd {

    private Enum configurationFromAdMap = EnvConstants.BATCH_CONFIG_PROPERTIES;

    @Bean
    public DataSource dataSource() {
            Properties defaultProps = new Properties();
            try {
                FileInputStream in = new FileInputStream(configurationFromAdMap.toString());
                defaultProps.load(in);
                in.close();
            }catch (IOException e){

            }
        return DataSourceBuilder
                .create()
                .username(defaultProps.getProperty("spring.datasource.username"))
                .password(defaultProps.getProperty("spring.datasource.password"))
                .url(defaultProps.getProperty("spring.datasource.url"))
                .driverClassName(defaultProps.getProperty("spring.datasource.driver-class-name"))
                .build();
    }


    @Bean
    public Long paramAnneeFr() {
        return Long.valueOf(String.valueOf(EnvConstants.PARAM_ANNEE_FR));
    }

    @Bean
    public Long paramAnneeAnf() {
        return Long.valueOf(String.valueOf(EnvConstants.PARAM_ANNEE_ANF));
    }

    ConfigurationPriamProd(){

    }
}
