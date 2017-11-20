package fr.sacem.priam.rest.api.copieprivee;

import fr.sacem.priam.model.config.JpaConfiguration;
import fr.sacem.priam.security.config.RestMvcConfig;
import fr.sacem.priam.security.config.SecurityRestConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;

/**
 * Created by benmerzoukah on 15/11/2017.
 */
@Import(value = {RestMvcConfig.class, JpaConfiguration.class, SecurityRestConfiguration.class})
@SpringBootApplication(scanBasePackages = {"fr.sacem.priam.rest.api.copieprivee", "fr.sacem.priam.common", "fr.sacem.priam.services", "fr.sacem.priam.model"})
@EnableCaching
public class PriamRestApiCopiepriveeWebApp extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(PriamRestApiCopiepriveeWebApp.class);
    }

}
