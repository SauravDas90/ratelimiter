package com.blueoptima.ratelimiter.service;

import static org.junit.Assert.assertEquals;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.util.ReflectionTestUtils;

import com.blueoptima.ratelimiter.RatelimiterApplicationTests;
import com.blueoptima.ratelimiter.domain.TokenCounter;
import com.blueoptima.ratelimiter.domain.UserApiKey;
import com.blueoptima.ratelimiter.exception.RateLimitExceededException;
import com.blueoptima.ratelimiter.exception.UserNotFoundException;
import com.blueoptima.ratelimiter.repository.UserApiKeyRepository;
import com.blueoptima.ratelimiter.repository.UserRepository;

public class RateLimitTokenInterceptorTest extends RatelimiterApplicationTests {

	private final static Logger LOGGER = LoggerFactory.getLogger(RateLimitTokenInterceptorTest.class);

	@MockBean
	private TokenBucketScheduler tokenBucketScheduler;

	@MockBean
	private UserApiKeyRepository userApiKeyRepository;

	@MockBean
	private UserRepository userRepository;

	@InjectMocks
	private TokenRateLimiterInterceptor tokenInterceptor;

	@Before
	public void setup() {

		MockitoAnnotations.initMocks(this);
		ReflectionTestUtils.setField(tokenInterceptor, "defaultUserLimit", 3);
		ReflectionTestUtils.setField(tokenInterceptor, "userLock", new ReentrantLock());
		ReflectionTestUtils.setField(tokenInterceptor, "limiters", new ConcurrentHashMap<>());

		UserApiKey userSauDevkey = new UserApiKey();
		userSauDevkey.setApiName("developers");
		userSauDevkey.setRateLimit(4);
		userSauDevkey.setUserName("Saurav");
		Optional<UserApiKey> opUserSauDevkey = Optional.of(userSauDevkey);

		TokenCounter tokenCounter = TokenCounter.create(2, TimeUnit.MINUTES);

		Mockito.when(userRepository.existsById(Mockito.isA(String.class))).thenReturn(true);
		Mockito.when(userApiKeyRepository.findById(Mockito.isA(String.class))).thenReturn(opUserSauDevkey);
		Mockito.when(tokenBucketScheduler.createAndSchedule(Mockito.isA(Integer.class), Mockito.isA(TimeUnit.class)))
				.thenReturn(tokenCounter);

	}

	/**
	 * test case to throw the {@code RateLimitExceededException}
	 * 
	 * @throws Exception
	 */
	@Test(expected = RateLimitExceededException.class)
	public void testRateLimitInterceptorRateLimitExceeded() throws Exception {

		Object handler = new Object();
		MockHttpServletResponse response = new MockHttpServletResponse();
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.addHeader("client-id", "Saurav");

		boolean output = tokenInterceptor.preHandle(request, response, handler);
		LOGGER.info("output is :: {}", output);
		assertEquals(output, true);
		boolean output2 = tokenInterceptor.preHandle(request, response, handler);
		LOGGER.info("output is :: {}", output2);
		assertEquals(output2, true);
		boolean output3 = tokenInterceptor.preHandle(request, response, handler);
		LOGGER.info("output is :: {}", output3);
		assertEquals(output3, true);

	}

	/**
	 * test case to throw the {@code UserNotFoundException}
	 * 
	 * @throws Exception
	 */
	@Test(expected = UserNotFoundException.class)
	public void testRateLimitInterceptorUserNotFound() throws Exception {

		Mockito.when(userRepository.existsById(Mockito.isA(String.class))).thenReturn(false);

		Object handler = new Object();
		MockHttpServletResponse response = new MockHttpServletResponse();
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.addHeader("client-id", "BlueOptima");

		boolean output = tokenInterceptor.preHandle(request, response, handler);
		LOGGER.info("output is :: {}", output);
		assertEquals(output, true);

	}

}
