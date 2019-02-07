package fr.sacem.priam.catcms.config;

import fr.sacem.priam.common.config.HealthConfig;
import fr.sacem.priam.common.config.PropertiesWithJavaConfig;
import fr.sacem.priam.model.config.JpaConfiguration;
import fr.sacem.priam.security.config.RestMvcConfig;
import fr.sacem.priam.security.config.SecurityRestConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;

@Import(value = {PropertiesWithJavaConfig.class, RestMvcConfig.class,
        JpaConfiguration.class, SecurityRestConfiguration.class,HealthConfig.class})

@SpringBootApplication(scanBasePackages = {"fr.sacem.priam.common", "fr.sacem.priam.catcms", "fr.sacem.priam.model"})
public class PriamCatalogueCMSWebApp extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(PriamCatalogueCMSWebApp.class);
    }
}
