package fr.sacem.priam.rest.copieprivee.config;


import fr.sacem.priam.batch.affectation.cp.config.BatchConfigLocal;
import fr.sacem.priam.batch.affectation.cp.config.BatchConfigProd;
import fr.sacem.priam.common.config.PropertiesWithJavaConfig;
import fr.sacem.priam.model.config.JpaConfiguration;
import fr.sacem.priam.security.config.RestMvcConfig;
import fr.sacem.priam.security.config.SecurityRestConfiguration;
import org.springframework.boot.actuate.autoconfigure.*;
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
 * Created by benmerzoukah on 15/11/2017.
 */

@Import(value = {PropertiesWithJavaConfig.class, RestMvcConfig.class, JpaConfiguration.class,
        SecurityRestConfiguration.class, BatchConfigLocal.class, BatchConfigProd.class})

@SpringBootApplication(scanBasePackages = {"fr.sacem.priam.rest.copieprivee", "fr.sacem.priam.common", "fr.sacem.priam.services", "fr.sacem.priam.model"})
@EnableCaching
@EnableAutoConfiguration(exclude = {
        ElasticsearchAutoConfiguration.class,
        ElasticsearchDataAutoConfiguration.class,
        DataSourceAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class,
        AuditAutoConfiguration.class,
        EndpointAutoConfiguration.class,
        EndpointMBeanExportAutoConfiguration.class,
        HealthIndicatorAutoConfiguration.class,
        MetricFilterAutoConfiguration.class,
        MetricRepositoryAutoConfiguration.class,
        PublicMetricsAutoConfiguration.class,
        TraceRepositoryAutoConfiguration.class,
        TraceWebFilterAutoConfiguration.class,
        WebMvcAutoConfiguration.class,
        JacksonAutoConfiguration.class
})
public class PriamRestApiCopiepriveeWebApp extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(PriamRestApiCopiepriveeWebApp.class);
    }

}
