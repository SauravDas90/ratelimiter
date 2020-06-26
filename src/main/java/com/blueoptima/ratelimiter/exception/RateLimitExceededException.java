package com.blueoptima.ratelimiter.exception;

/**
 * Subclass of {@code RuntimeException}, it represents the </b>rate limit
 * exceeded for {@code TimeUnit}<b/> for a given combination of user and api
 * 
 * @author saurav.das
 *
 */
public class RateLimitExceededException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1403057783259478003L;

	/**
	 * 
	 */
	public RateLimitExceededException() {
		super();

	}

	/**
	 * @param message
	 *            errorMessage for exceeding Rate Limit for an {@code TimeUnit}
	 * @param cause
	 *            errorCause
	 */
	public RateLimitExceededException(String message, Throwable cause) {
		super(message, cause);

	}

	/**
	 * @param message
	 *            errorMessage for exceeding Rate Limit for an {@code TimeUnit}
	 *            errorMessage for exceeding Rate Limit for an {@code TimeUnit}
	 */
	public RateLimitExceededException(String message) {
		super(message);

	}

}
