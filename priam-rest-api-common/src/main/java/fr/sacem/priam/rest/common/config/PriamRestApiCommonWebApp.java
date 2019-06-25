package fr.sacem.priam.rest.common.config;

import fr.sacem.priam.batch.felix.config.BatchFelixConfigLocal;
import fr.sacem.priam.batch.felix.config.BatchFelixConfigProd;
import fr.sacem.priam.common.config.HealthConfig;
import fr.sacem.priam.common.config.PropertiesWithJavaConfig;
import fr.sacem.priam.model.config.JpaConfiguration;
import fr.sacem.priam.model.config.RestConfig;
import fr.sacem.priam.security.config.RestMvcConfig;
import fr.sacem.priam.security.config.SecurityRestConfiguration;
import fr.sacem.priam.services.utils.SpringAsyncConfig;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

/**
 * Created by benmerzoukah on 15/11/2017.
 */
@Import(value = {PropertiesWithJavaConfig.class, RestMvcConfig.class, JpaConfiguration.class, SecurityRestConfiguration.class,
                 SpringAsyncConfig.class, HealthConfig.class, RestConfig.class, BatchFelixConfigLocal.class, BatchFelixConfigProd.class})
@SpringBootApplication(scanBasePackages = {"fr.sacem.priam.rest.common", "fr.sacem.priam.common", "fr.sacem.priam.services", "fr.sacem.priam.model"})
@EnableCaching
@Profile({"prod", "dev", "re7"})
public class PriamRestApiCommonWebApp extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(PriamRestApiCommonWebApp.class);
    }

}
