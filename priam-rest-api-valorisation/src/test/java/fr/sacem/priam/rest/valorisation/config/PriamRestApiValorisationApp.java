package fr.sacem.priam.rest.valorisation.config;

import fr.sacem.priam.batch.common.dao.FichierRepository;
import fr.sacem.priam.batch.common.dao.FichierRepositoryImpl;
import fr.sacem.priam.batch.common.domain.Admap;
import fr.sacem.priam.model.dao.JpaConfigurationTest;
import fr.sacem.priam.security.config.RestMvcConfig;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;

@Import({JpaConfigurationTest.class, AdampConfigTest.class, RestMvcConfig.class})
@SpringBootApplication(scanBasePackages = {"fr.sacem.priam.rest.valorisation", "fr.sacem.priam.common", "fr.sacem.priam.services", "fr.sacem.priam.model", "fr.sacem.priam.batch.fv", "fr.sacem.priam.batch.common"})
@PropertySource("classpath:config/application-batch-test.properties")
@ImportResource(value = "classpath:config/job-affectation-fv.xml")
@EnableCaching
@ActiveProfiles("test")
@EnableAutoConfiguration(exclude = { SecurityAutoConfiguration.class})
public class PriamRestApiValorisationApp extends SpringBootServletInitializer {

    @Value("${csvFile}")
    String ouputCsvFile;

    @Autowired
    DataSource dataSource;

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(PriamRestApiValorisationApp.class);
    }

    @Bean
    public Admap admap(){
        Admap admap = new Admap();
        admap.setOutputFile(ouputCsvFile);
        return admap;
    }

    @Bean
    @Primary
    public FichierRepository fichierRepositoryFV() {
        FichierRepositoryImpl fichierRepository = new FichierRepositoryImpl();
        fichierRepository.setDataSource(dataSource);
        fichierRepository.setNomTableLigneProgrammeLog("PRIAM_IMPORT_PROGRAMME_FV_LOG");

        return fichierRepository;
    }
}
