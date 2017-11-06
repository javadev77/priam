package fr.sacem.priam.ui.rest;


import fr.sacem.priam.model.config.JpaConfiguration;
import fr.sacem.priam.model.dao.JpaConfigurationTest;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;

/**
 * Created by benmerzoukah on 18/04/2017.
 */
@Import(JpaConfigurationTest.class)
@SpringBootApplication(scanBasePackages = {"fr.sacem.priam"})
@EnableCaching
@ActiveProfiles("test")
public class PriamWebAppTest extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(PriamWebAppTest.class);
    }
}
