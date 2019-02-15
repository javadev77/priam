package fr.sacem.priam.rest.valorisation.config;

import fr.sacem.priam.batch.fv.config.BatchConfigProd;
import fr.sacem.priam.common.config.PropertiesWithJavaConfig;
import fr.sacem.priam.model.config.JpaConfiguration;
import fr.sacem.priam.security.config.RestMvcConfig;
import fr.sacem.priam.security.config.SecurityRestConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;

/**
 * Entry point for config of Valorisation WebApp
 * Created by benmerzoukah on 21/11/2018.
 */
@Import(value = {PropertiesWithJavaConfig.class, RestMvcConfig.class,
    JpaConfiguration.class, SecurityRestConfiguration.class, BatchConfigProd.class})
@SpringBootApplication(scanBasePackages = {"fr.sacem.priam.rest.valorisation", "fr.sacem.priam.common", "fr.sacem.priam.services", "fr.sacem.priam.model"})

@EnableCaching
public class PriamRestApiValorisationWebapp extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(PriamRestApiValorisationWebapp.class);
    }

}
