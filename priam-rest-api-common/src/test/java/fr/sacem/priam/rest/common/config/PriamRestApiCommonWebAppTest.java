package fr.sacem.priam.rest.common.config;


import fr.sacem.priam.model.dao.JpaConfigurationTest;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

/**
 * Created by benmerzoukah on 18/04/2017.
 */
@Import(JpaConfigurationTest.class)
@SpringBootApplication(scanBasePackages = {"fr.sacem.priam.rest.common", "fr.sacem.priam.common", "fr.sacem.priam.services", "fr.sacem.priam.model"})
@EnableCaching
@ActiveProfiles("test")
public class PriamRestApiCommonWebAppTest extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(PriamRestApiCommonWebAppTest.class);
    }
}
