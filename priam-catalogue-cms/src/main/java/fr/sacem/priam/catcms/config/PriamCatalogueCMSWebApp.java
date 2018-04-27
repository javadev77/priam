package fr.sacem.priam.catcms.config;

import fr.sacem.priam.model.config.JpaConfiguration;
import fr.sacem.priam.security.config.RestMvcConfig;
import fr.sacem.priam.security.config.SecurityRestConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;

@Import(value = {RestMvcConfig.class, JpaConfiguration.class, SecurityRestConfiguration.class})
@SpringBootApplication(scanBasePackages = {"fr.sacem.priam.common", "fr.sacem.priam.catcms"})
@EnableCaching
public class PriamCatalogueCMSWebApp extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(PriamCatalogueCMSWebApp.class);
    }
}
