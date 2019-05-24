package fr.sacem.priam.batch.fv.participation.fonds.config;

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
@ComponentScan(basePackages = {"fr.sacem.priam.batch.fv.participation.fonds", "fr.sacem.priam.batch.common.reader"})
@Profile("production")
@PropertySource("classpath:config/application-production.properties")
@ImportResource(value = "classpath:config/job-configuration.xml")
public class BatchConfigProd {

    private Enum configurationFromAdMap = EnvConstants.BATCH_CONFIG_PROPERTIES;
    private String inputDirectory = String.valueOf(EnvConstants.PRIAM_PARTICIPATION_FONDS_IN_DIR);
    private String outputDirectory = String.valueOf(EnvConstants.PRIAM_PARTICIPATION_FONDS_ARCHIVES_DIR);
    private String prefixFileName = String.valueOf(EnvConstants.PATTERN_FILE_NAME);

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
        admap.setPatternFileName(prefixFileName);

        return admap;
    }

    BatchConfigProd(){

    }
}
