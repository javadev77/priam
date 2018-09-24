package fr.sacem.priam.common.health;

import org.springframework.boot.actuate.endpoint.HealthEndpoint;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthAggregator;
import org.springframework.boot.actuate.health.HealthIndicator;

import java.util.Collections;
import java.util.Map;

/**
 * Created by benmerzoukah on 19/09/2018.
 */
public class PriamHealthEndpoint extends HealthEndpoint {

    private final ParallelHealthIndicator healthIndicator;

    public PriamHealthEndpoint(HealthAggregator healthAggregator, Map<String, HealthIndicator> healthIndicators) {
        super(healthAggregator, Collections.emptyMap());
        ParallelHealthIndicator healthIndicator = new ParallelHealthIndicator(healthAggregator);
        for (Map.Entry<String, HealthIndicator> h : healthIndicators.entrySet()) {
            healthIndicator.addHealthIndicator(getKey(h.getKey()), h.getValue());
        }
        this.healthIndicator = healthIndicator;
    }

    @Override
    public Health invoke() {
        return healthIndicator.health();
    }

    /**
     * Turns the bean name into a key that can be used in the map of health information.
     */
    private String getKey(String name) {
        int index = name.toLowerCase().indexOf("healthindicator");
        if (index > 0) {
            return name.substring(0, index);
        }
        return name;
    }
}
