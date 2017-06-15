package fr.sacem.priam.ui.rest;


import fr.sacem.priam.model.dao.JpaConfigurationTest;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;

/**
 * Created by benmerzoukah on 18/04/2017.
 */
@Import(JpaConfigurationTest.class)
@SpringBootApplication(scanBasePackages = {"fr.sacem.priam"})
@EnableCaching
public class PriamWebAppTest extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(PriamWebAppTest.class);
    }
}
