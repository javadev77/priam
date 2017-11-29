package fr.sacem.priam.ui;


import fr.sacem.priam.common.config.PropertiesWithJavaConfig;
import fr.sacem.priam.model.config.JpaConfiguration;
import fr.sacem.priam.security.config.RestMvcConfig;
import fr.sacem.priam.security.config.SecurityRestConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchAutoConfiguration;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;

/**
 * Created by benmerzoukah on 18/04/2017.
 */
@Import(value = {PropertiesWithJavaConfig.class})
@SpringBootApplication(scanBasePackages = {"fr.sacem.priam.ui", "fr.sacem.priam.model"})
@EnableAutoConfiguration(exclude = {
  ElasticsearchAutoConfiguration.class,
  ElasticsearchDataAutoConfiguration.class,
  DataSourceAutoConfiguration.class,
  HibernateJpaAutoConfiguration.class,
  WebMvcAutoConfiguration.class,
  JacksonAutoConfiguration.class
})
public class PriamWebApp extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(PriamWebApp.class);
    }
}
