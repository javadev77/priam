package fr.sacem.priam.batch.fv.octav.rep.config;

import fr.sacem.priam.batch.common.domain.Admap;
import fr.sacem.priam.common.constants.EnvConstants;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import javax.sql.DataSource;
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
@ComponentScan(basePackages = {"fr.sacem.priam.batch.fv.octav.rep", "fr.sacem.priam.batch.common.dao", "fr.sacem.priam.batch.common.fv", "fr.sacem.priam.batch.common.domain"})
@Profile("production")
@PropertySource("classpath:config/application-production.properties")
public class ConfigurationPriamProd {

    private Enum configurationFromAdMap = EnvConstants.BATCH_CONFIG_PROPERTIES;
    private String inputDirectory = String.valueOf(EnvConstants.OCATV_CTNU_REP_IN_DIR);
    private String outputDirectory = String.valueOf(EnvConstants.OCATV_CTNU_REP_ARCHIVES_DIR);
    private String patternFilename = String.valueOf(EnvConstants.PATTERN_FILE_NAME);

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
    public Admap admap(){
        Admap admap = new Admap();

        admap.setInputFile(inputDirectory);
        admap.setOutputFile(outputDirectory);
        admap.setPatternFileName(patternFilename);

        return admap;
    }

    ConfigurationPriamProd(){

    }
}
