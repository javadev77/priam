package fr.sacem.priam.ui;


import fr.sacem.priam.model.config.JpaConfiguration;
import fr.sacem.priam.services.utils.SpringAsyncConfig;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;

/**
 * Created by benmerzoukah on 18/04/2017.
 */
@Import(value = {JpaConfiguration.class, SpringAsyncConfig.class})
@SpringBootApplication(scanBasePackages = {"fr.sacem.priam"})
@EnableCaching
public class PriamWebApp extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(PriamWebApp.class);
    }
}
