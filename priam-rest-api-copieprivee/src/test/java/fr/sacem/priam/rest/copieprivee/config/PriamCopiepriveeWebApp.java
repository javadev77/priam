package fr.sacem.priam.rest.copieprivee.config;

import fr.sacem.priam.model.dao.JpaConfigurationTest;
import fr.sacem.priam.security.config.RestMvcConfig;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;

/**
 * Created by benmerzoukah on 18/04/2017.
 */
@Import({JpaConfigurationTest.class, AdampConfigTest.class, RestMvcConfig.class})
@ImportResource(value = "classpath:config/job-configuration.xml")
@PropertySource("classpath:config/application-batch.properties")
@SpringBootApplication(scanBasePackages = {"fr.sacem.priam.rest.copieprivee", "fr.sacem.priam.common",
                                            "fr.sacem.priam.services", "fr.sacem.priam.model", "fr.sacem.priam.batch.affectation.cp"})
@EnableCaching
@ActiveProfiles("test")
@EnableAutoConfiguration(exclude = { SecurityAutoConfiguration.class})
public class PriamCopiepriveeWebApp extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(PriamCopiepriveeWebApp.class);
    }
}
