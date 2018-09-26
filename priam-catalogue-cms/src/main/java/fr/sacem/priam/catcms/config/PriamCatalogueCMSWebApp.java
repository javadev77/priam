package fr.sacem.priam.catcms.config;

import fr.sacem.priam.common.config.HealthConfig;
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
import org.springframework.context.annotation.Import;

@Import(value = {RestMvcConfig.class, SecurityRestConfiguration.class, JpaConfiguration.class, HealthConfig.class})
@SpringBootApplication(scanBasePackages = {"fr.sacem.priam.common", "fr.sacem.priam.catcms"})
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
public class PriamCatalogueCMSWebApp extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(PriamCatalogueCMSWebApp.class);
    }
}
