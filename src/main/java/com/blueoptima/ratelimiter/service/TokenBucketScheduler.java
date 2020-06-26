package com.blueoptima.ratelimiter.service;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.blueoptima.ratelimiter.domain.TokenCounter;

/**
 * {@code TokenCounter} This acts as a service to create and manage scheduling
 * of TokenCounter uses scheduleAtFixedRate from
 * {@code ScheduledExecutorService}
 * 
 * @author saurav.das
 * 
 */

@Service("tokenBucketScheduler")
public class TokenBucketScheduler {

	private final static Logger LOGGER = LoggerFactory.getLogger(TokenBucketScheduler.class);

	/**
	 * Creates a token counter and schedules the token bucket replenishment
	 * algorithm by For creation of TokenCounter use <b>Factory method pattern</b>
	 * 
	 * @param initMaxPermits
	 *            maximum initial permits as configured in database
	 * @param timePeriod
	 *            timePeriod after which token will be replenished, specify the
	 *            TimeUnit
	 * @return tokenCounter TokenCounter with parameters
	 */
	public TokenCounter createAndSchedule(int initMaxPermits, TimeUnit timePeriod) {
		TokenCounter tokenCounter = TokenCounter.create(initMaxPermits, timePeriod);
		scheduleTokenReplenishment(tokenCounter);
		return tokenCounter;
	}

	/**
	 * schedules the token bucket replenishment algorithm, replenishes the acquired
	 * sempahores/permits at the end of certain period if userapi limit is not
	 * provided, it will use the default user limit as provided in
	 * application.properties
	 * 
	 * @param tokenCounter
	 *            TokenCounter with parameters
	 */
	public void scheduleTokenReplenishment(TokenCounter tokenCounter) {
		ScheduledExecutorService tokenBucketScheduler = tokenCounter.getScheduler();
		Semaphore tokenSemaphore = tokenCounter.getSemaphore();
		int maxPermits = tokenCounter.getMaxPermits();

		LOGGER.info("Before replenishining available permits :: {}", tokenSemaphore.availablePermits());
		tokenBucketScheduler.scheduleAtFixedRate(() -> {
			tokenSemaphore.release(maxPermits - tokenSemaphore.availablePermits());
		}, 1, 5, tokenCounter.getTimePeriod());
		LOGGER.info("After replenishining available permits totalPermits ::{}  available:: {}", maxPermits,
				tokenSemaphore.availablePermits());
	}

}
