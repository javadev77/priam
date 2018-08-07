package fr.sacem.priam.batch.generation.catalogue.config;
import fr.sacem.priam.batch.common.domain.Admap;
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
@ComponentScan(basePackages = {"fr.sacem.priam.batch.generation.catalogue"})
@ImportResource(value = "classpath:config/job-generation-catalogue-fr.xml")
@Profile({"production", "re7"})
@PropertySource("classpath:config/application-production.properties")
public class ConfigurationPriamProd {

    private Enum ConfigurationFromAdMap = EnvConstants.BATCH_CONFIG_PROPERTIES;
    private String outputDirectory = String.valueOf(EnvConstants.PARTICIPANTS_REQ_OCTAV_CSV_DIR);

    @Bean
    public DataSource dataSource() {
            Properties defaultProps = new Properties();
            try {
                FileInputStream in = new FileInputStream(ConfigurationFromAdMap.toString());
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
    public Admap admap(){
        Admap admap = new Admap();

        admap.setOutputFile(outputDirectory);

        return admap;
    }
    ConfigurationPriamProd(){

    }
}
