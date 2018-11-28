package fr.sacem.priam.batch.filiation.npu.config;

import fr.sacem.priam.batch.common.domain.Admap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.*;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = {"fr.sacem.priam.batch.filiation.npu"})
@Profile({"local"})
@PropertySource("classpath:config/application-local.properties")
@ImportResource(value = "classpath:config/job-configuration.xml")
public class ConfigurationPriamLocal {
    @Value("${spring.datasource.url}")
    String urlDb;

    @Value("${spring.datasource.username}")
    String usernameDb;

    @Value("${spring.datasource.driver-class-name}")
    String driverDb;

    @Value("${spring.datasource.password}")
    String passwordDb;

    @Value("${input.archives}")
    String inputDirectory;

    @Value("${output.archives}")
    String outputDirectory;

    @Value("${pattern.file.name}")
    String patternFileName;

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
        admap.setPatternFileName(patternFileName);
        return admap;
    }

    ConfigurationPriamLocal(){

    }
}
