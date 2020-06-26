package com.blueoptima.ratelimiter.domain;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * POJO for containing the token, to be used in Token bucket algorithm
 * 
 * @author saurav.das
 * 
 */
public class TokenCounter {

	private Semaphore semaphore;
	private int maxPermits;
	private TimeUnit timePeriod;
	private ScheduledExecutorService scheduler;

	/**
	 * @param initMaxPermits
	 *            initial max request/per user/per unit time
	 * @param timePeriod
	 *            time period after which token is replenished
	 * @return TokenCounter factory method to create TokenBucket
	 * 
	 */
	public static TokenCounter create(int initMaxPermits, TimeUnit timePeriod) {
		return new TokenCounter(initMaxPermits, timePeriod);
	}

	/**
	 * @param initMaxPermits
	 *            initial max request/per user/per unit time
	 * @param timePeriod
	 *            time period after which token is replenished
	 */
	private TokenCounter(int initMaxPermits, TimeUnit timePeriod) {
		super();
		this.semaphore = new Semaphore(initMaxPermits);
		this.maxPermits = initMaxPermits;
		this.timePeriod = timePeriod;
		this.scheduler = Executors.newScheduledThreadPool(1);
	}

	public Semaphore getSemaphore() {
		return semaphore;
	}

	public void setSemaphore(Semaphore semaphore) {
		this.semaphore = semaphore;
	}

	public int getMaxPermits() {
		return maxPermits;
	}

	public void setMaxPermits(int maxPermits) {
		this.maxPermits = maxPermits;
	}

	public TimeUnit getTimePeriod() {
		return timePeriod;
	}

	public void setTimePeriod(TimeUnit timePeriod) {
		this.timePeriod = timePeriod;
	}

	public ScheduledExecutorService getScheduler() {
		return scheduler;
	}

	public void setScheduler(ScheduledExecutorService scheduler) {
		this.scheduler = scheduler;
	}

	public boolean tryAcquire() {
		return semaphore.tryAcquire();
	}

	public void terminateScheduler() {
		scheduler.shutdownNow();
	}
}
