package fr.sacem.config;

import fr.sacem.priam.common.constants.EnvConstants;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by fandis on 09/10/2017.
 */
@Configuration
@PropertySource(value = "classpath:config/application.properties")
public class DataSourceConfiguration {
    private Enum ConfigurationFromAdMap = EnvConstants.BATCH_CONFIG_PROPERTIES;
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
    DataSourceConfiguration(){

    }
}
