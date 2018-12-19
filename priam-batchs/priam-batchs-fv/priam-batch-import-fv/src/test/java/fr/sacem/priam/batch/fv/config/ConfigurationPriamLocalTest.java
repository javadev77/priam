package fr.sacem.priam.batch.fv.config;

import fr.sacem.priam.batch.common.domain.Admap;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import javax.sql.DataSource;

import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;


/**
 * Created by fandis on 09/10/2017.
 */
@Configuration
@ComponentScan(basePackages = {"fr.sacem.priam.batch.fv", "fr.sacem.priam.batch.common"})
@Profile({"test"})
@PropertySource("classpath:config/application-${spring.profiles.active}.properties")
public class ConfigurationPriamLocalTest {

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
    public Admap admap(){
        Admap admap = new Admap();
        admap.setInputFile(inputDirectory);
        admap.setOutputFile(outputDirectory);
        admap.setPatternFileName(patternFileName);
        return admap;
    }

    @Bean
    public JobLauncherTestUtils jobLauncherTestUtils() {
        return new JobLauncherTestUtils();
    }

    @Value("classpath:script/init-schema.sql")
    private Resource initSchema;

    @Value("classpath:org/springframework/batch/core/schema-drop-h2.sql")
    private Resource schemaDrop;

    @Value("classpath:org/springframework/batch/core/schema-h2.sql")
    private Resource schema;

    @Bean
    public DataSource dataSource() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        builder.setType(H2).addScript("classpath:script/init-schema.sql").addScript("classpath:script/data.sql");
        return builder.build();
    }

    ConfigurationPriamLocalTest(){

    }
}
