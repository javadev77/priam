package fr.sacem.priam.rest.copieprivee.config;

import fr.sacem.priam.batch.affectation.cp.config.BatchConfigProd;
import fr.sacem.priam.common.config.PropertiesWithJavaConfig;
import fr.sacem.priam.model.config.JpaConfiguration;
import fr.sacem.priam.security.config.RestMvcConfig;
import fr.sacem.priam.security.config.SecurityRestConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

/**
 * Created by benmerzoukah on 15/11/2017.
 */

@Import(value = {PropertiesWithJavaConfig.class, RestMvcConfig.class, JpaConfiguration.class,
        SecurityRestConfiguration.class, BatchConfigProd.class})

@SpringBootApplication(scanBasePackages = {"fr.sacem.priam.rest.copieprivee", "fr.sacem.priam.common", "fr.sacem.priam.services", "fr.sacem.priam.model"})
@EnableCaching
@Profile({"prod", "dev", "re7"})
public class PriamRestApiCopiepriveeWebApp extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(PriamRestApiCopiepriveeWebApp.class);
    }

}
