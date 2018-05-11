package fr.sacem.priam.catcms.config;

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

/**
 * Created by benmerzoukah on 18/04/2017.
 */
@Import({JpaConfigurationTest.class, RestMvcConfig.class})
@SpringBootApplication(scanBasePackages = {"fr.sacem.priam.catcms", "fr.sacem.priam.common", "fr.sacem.priam.model"})
@EnableCaching
@ActiveProfiles("test")
@EnableAutoConfiguration(exclude = { SecurityAutoConfiguration.class})
public class PriamCatCmsTestWebApp extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(PriamCatCmsTestWebApp.class);
    }
}
