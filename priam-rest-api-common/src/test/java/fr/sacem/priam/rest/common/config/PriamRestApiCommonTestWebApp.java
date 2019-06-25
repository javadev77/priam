package fr.sacem.priam.rest.common.config;

import fr.sacem.priam.model.dao.JpaConfigurationTest;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.ActiveProfiles;

/**
 * Created by benmerzoukah on 18/04/2017.
 */
@ActiveProfiles("test")
@Import({JpaConfigurationTest.class, AdmapConfigTest.class, RestMvcConfigTest.class})
@ImportResource(value = "classpath:config/job-configuration.xml")
@SpringBootApplication(scanBasePackages = {"fr.sacem.priam.rest.common", "fr.sacem.priam.common",
                                            "fr.sacem.priam.services", "fr.sacem.priam.model", "fr.sacem.priam.batch.felix"})
@EnableCaching
@EnableAutoConfiguration(exclude = { SecurityAutoConfiguration.class})
public class PriamRestApiCommonTestWebApp extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(PriamRestApiCommonTestWebApp.class);
    }
}
