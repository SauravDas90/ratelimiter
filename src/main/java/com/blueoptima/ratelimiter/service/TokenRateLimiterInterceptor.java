package com.blueoptima.ratelimiter.service;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.blueoptima.ratelimiter.domain.TokenCounter;
import com.blueoptima.ratelimiter.domain.UserApiKey;
import com.blueoptima.ratelimiter.exception.RateLimitExceededException;
import com.blueoptima.ratelimiter.exception.UserNotFoundException;
import com.blueoptima.ratelimiter.repository.UserApiKeyRepository;
import com.blueoptima.ratelimiter.repository.UserRepository;

/**
 * preHandle implements the rate limiting algorithm using the Token Bucket
 * algorithm for rate limiting. The class uses {@code ScheduledExecutorService}
 * schedules the release of permits after a fixed interval
 * 
 * <p>
 * here 00:00 represents minutes::seconds
 * </p>
 * <p>
 * 00:00: 4 permits for user1, 5 permits for user2
 * </p>
 * <p>
 * 00:01: 3 permits, after 1st request by user1
 * </p>
 * <p>
 * 00:02: 2 permits, after 2nd request by user1
 * </p>
 * <p>
 * 00:07: 1 permit , after 3rd request by user1
 * </p>
 * <p>
 * 00:08: 4 permits, after 1st request by user2
 * </p>
 * <p>
 * 05:00: permits are replenished for user1,so currently 4 permits for user1
 * </p>
 * <p>
 * 05:08: permits are replenished for user2,so currently 5 permits for user2
 * </p>
 *
 * 
 * @author saurav.das
 * 
 */

public class TokenRateLimiterInterceptor extends HandlerInterceptorAdapter {

	private final static Logger LOGGER = LoggerFactory.getLogger(TokenRateLimiterInterceptor.class);
	private final static String TOKENERRORMESSAGE = "Token Limit exceeeded for the given interval";
	private final static String USERNOTFOUNDMESSAGE = "User doesn't exists, invalid user";

	@Value("${rate.minute.limit}")
	private int defaultUserLimit;

	@Autowired
	private TokenBucketScheduler tokenBucketScheduler;

	@Autowired
	private UserApiKeyRepository userApiKeyRepository;

	@Autowired
	private UserRepository userRepository;

	private ReentrantLock userLock = new ReentrantLock();

	private Map<String, Map<String, TokenCounter>> limiters = new ConcurrentHashMap<>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.handler.HandlerInterceptorAdapter#preHandle(
	 * javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse, java.lang.Object)
	 * 
	 * adds X-RateLimit-Limit to Response header on being successful
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String userId = request.getHeader("client-id");
		String requestUri = request.getRequestURI();
		String api = requestUri.substring(requestUri.lastIndexOf('/') + 1);
		LOGGER.info("api::{}, userId ::{}", api, userId);
		if (userId == null) {
			return false;
		}

		boolean userExists = userRepository.existsById(userId);
		// check if the user exists in the in-memory db, else throw an exception
		if (!userExists)
			throw new UserNotFoundException(USERNOTFOUNDMESSAGE);

		// create the userApiKey by the combination of user and api to be hit
		// use the key to obtain the user limit for the given api
		String userApiKey = userId.concat("_").concat(api);
		Optional<UserApiKey> user = userApiKeyRepository.findById(userApiKey);

		// set the default user limit per api,
		// if the limit exists for the userApiKey, update the perApiUserLimit

		int perApiUserLimit = defaultUserLimit;
		if (user.isPresent()) {
			perApiUserLimit = user.get().getRateLimit();
		}

		TokenCounter rateLimiter = getRateLimiter(userId, perApiUserLimit, api);
		boolean allowRequest = rateLimiter.tryAcquire();

		if (!allowRequest) {
			throw new RateLimitExceededException(TOKENERRORMESSAGE);
		}
		Integer limit = rateLimiter.getSemaphore().availablePermits();
		LOGGER.info(" Remaining Semaphore :: {}", limit.toString());
		response.addHeader("X-RateLimit-Limit", limit.toString());
		return allowRequest;
	}

	/**
	 * @param userId
	 * @param perApiUserLimit
	 * @param api
	 * @return TokenCounter
	 * 
	 *         obtain the rateLimiter for per user/per api
	 */
	private TokenCounter getRateLimiter(String userId, int perApiUserLimit, String api) {
		Map<String, TokenCounter> perApiUserLimiter = limiters.computeIfAbsent(api, s -> new ConcurrentHashMap<>());
		return perApiUserLimiter.computeIfAbsent(userId, newuser -> {
			return createRateLimiter(perApiUserLimit);
		});
	}

	/**
	 * @param perApiUserLimit
	 * @return TokenCounter
	 * 
	 *         create and schedule the token bucket algorithm
	 */
	private TokenCounter createRateLimiter(int perApiUserLimit) {
		try {
			// using the lock to prevent multiple threads
			// prevent the creation of rate limit for a given user/api
			userLock.tryLock(20, TimeUnit.SECONDS);
			LOGGER.info("Locking via the lock :: ");
			return tokenBucketScheduler.createAndSchedule(perApiUserLimit, TimeUnit.MINUTES);

		} catch (InterruptedException e) {
			e.printStackTrace();
			throw new RuntimeException("failed to obtain lock");
		} finally {

			LOGGER.info("Locking released :: ");
			userLock.unlock();
		}

	}
}
