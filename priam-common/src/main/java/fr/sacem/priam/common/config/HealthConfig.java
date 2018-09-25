package fr.sacem.priam.common.config;

import fr.sacem.priam.common.health.PriamHealthEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.EndpointWebMvcAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.ManagementServerPropertiesAutoConfiguration;
import org.springframework.boot.actuate.endpoint.HealthEndpoint;
import org.springframework.boot.actuate.endpoint.mvc.HealthMvcEndpoint;
import org.springframework.boot.actuate.health.HealthAggregator;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.OrderedHealthAggregator;
import org.springframework.context.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by benmerzoukah on 19/09/2018.
 */
@Import({
        ManagementServerPropertiesAutoConfiguration.class,
        EndpointWebMvcAutoConfiguration.class
})
@Configuration
@ComponentScan("fr.sacem.priam.common.health")
public class HealthConfig {

    @Autowired(required = false)
    private Map<String, HealthIndicator> healthIndicators = new HashMap<>();

    @Bean
    @Primary
    public HealthEndpoint healthEndpoint(HealthAggregator healthAggregator) {
        return new PriamHealthEndpoint(healthAggregator, this.healthIndicators);
    }

    @Bean @Primary
    public HealthAggregator healthAggregator(){
        return new OrderedHealthAggregator();
    }

    @Bean @Primary
    public HealthMvcEndpoint healthMvcEndpoint(HealthEndpoint healthEndpoint){
        return new HealthMvcEndpoint(healthEndpoint, false);
    }


}


