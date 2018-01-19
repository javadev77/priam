package fr.sacem.priam.rest.cms.config;

import fr.sacem.priam.batch.affectation.config.BatchConfigProd;
import fr.sacem.priam.common.config.PropertiesWithJavaConfig;
import fr.sacem.priam.model.config.JpaConfiguration;
import fr.sacem.priam.batch.affectation.config.BatchConfigLocal;
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
@Import(value = {PropertiesWithJavaConfig.class, RestMvcConfig.class,
        JpaConfiguration.class, SecurityRestConfiguration.class, BatchConfigLocal.class, BatchConfigProd.class})
@SpringBootApplication(scanBasePackages = {"fr.sacem.priam.rest.cms", "fr.sacem.priam.common", "fr.sacem.priam.services", "fr.sacem.priam.model"})
@EnableCaching
public class PriamRestApiCMSWebApp extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(PriamRestApiCMSWebApp.class);
    }

}
