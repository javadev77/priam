package fr.sacem.priam.common.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthAggregator;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

public class ParallelHealthIndicator implements HealthIndicator {

	private final Map<String, HealthIndicator> indicators;

	private final HealthAggregator healthAggregator;

	/**
     * Create a new {@link ParallelHealthIndicator}.
	 */
	public ParallelHealthIndicator(HealthAggregator healthAggregator) {
		this(healthAggregator, new LinkedHashMap<>());
	}

	/**
	 * Create a new {@link ParallelHealthIndicator} from the specified indicators.
	 * @param indicators a map of {@link HealthIndicator}s with the key being used as an
	 * indicator name.
	 */
	public ParallelHealthIndicator(HealthAggregator healthAggregator,
                                   Map<String, HealthIndicator> indicators) {
		Assert.notNull(healthAggregator, "HealthAggregator must not be null");
		Assert.notNull(healthAggregator, "Indicators must not be null");
		this.indicators = new LinkedHashMap<>(indicators);
		this.healthAggregator = healthAggregator;
	}

	public void addHealthIndicator(String name, HealthIndicator indicator) {
		this.indicators.put(name, indicator);
	}

	@Override
	public Health health() {
        final CountDownLatch latch = new CountDownLatch(this.indicators.size());
		final Map<String, Health> healths = new ConcurrentHashMap<>();

        List<HealthChecker> list = new ArrayList<>();
		for (final Map.Entry<String, HealthIndicator> entry : this.indicators.entrySet()) {
            list.add(new HealthChecker(latch, entry.getKey(), entry.getValue(), healths));
		}
        for(HealthChecker hc : list){
            hc.run();
        }
        try{
            latch.await();  //main thread is waiting on CountDownLatch to finish
        }catch(InterruptedException ie){
            ie.printStackTrace();
        }

        return this.healthAggregator.aggregate(healths);
	}

}
