package fr.sacem.priam.rest.valorisation.config;

import fr.sacem.priam.model.dao.JpaConfigurationTest;
import fr.sacem.priam.security.config.RestMvcConfig;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@Import({JpaConfigurationTest.class, AdampConfigTest.class, RestMvcConfig.class})
@SpringBootApplication(scanBasePackages = {"fr.sacem.priam.rest.valorisation", "fr.sacem.priam.common", "fr.sacem.priam.services", "fr.sacem.priam.model"})
@EnableCaching
@ActiveProfiles("test")
@EnableAutoConfiguration(exclude = { SecurityAutoConfiguration.class})
public class PriamRestApiValorisationApp extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(PriamRestApiValorisationApp.class);
    }
}
