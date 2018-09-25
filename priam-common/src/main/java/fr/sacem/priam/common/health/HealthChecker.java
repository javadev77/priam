package fr.sacem.priam.common.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;

import java.util.Map;
import java.util.concurrent.CountDownLatch;

class HealthChecker extends Thread {

    private String key;
    private HealthIndicator healthIndicator;
    private CountDownLatch latch;
    private Map<String, Health> results;

    public HealthChecker(CountDownLatch latch, String key, HealthIndicator healthIndicator, Map<String, Health> results) {
        this.key = key;
        this.healthIndicator = healthIndicator;
        this.latch = latch;
        this.results = results;
    }

    @Override
    public void run() {
        try {
            results.put(key, healthIndicator.health());
        } catch (Exception e){
            Health.Builder builder = new Health.Builder();
            results.put(key, builder.down(e).build());
        } finally {
            latch.countDown();
        }
    }
}
