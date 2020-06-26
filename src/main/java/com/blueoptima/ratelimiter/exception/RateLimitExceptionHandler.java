/**
 * 
 */
package com.blueoptima.ratelimiter.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

/**
 * Exception handler for {@code RateLimitExceededException} and
 * {@code UserNotFoundException} with custom message and
 * appropriate{@code HttpStatus} to pass to the caller/UI
 * 
 * @author saurav.das
 *
 */
@ControllerAdvice
public class RateLimitExceptionHandler {

	private final static Logger LOGGER = LoggerFactory.getLogger(RateLimitExceptionHandler.class);

	/**
	 * handler method for {@code RateLimitExceededException} returns
	 * HttpStatus.TOO_MANY_REQUESTS error code
	 * 
	 * @param ex
	 *            incoming {@code RateLimitExceededException} exception
	 * @param request
	 *            webrequest
	 * @return Object representing the error message
	 */
	@ExceptionHandler(RateLimitExceededException.class)
	public final ResponseEntity<Object> handleRateLimitExceededException(RateLimitExceededException ex,
			WebRequest request) {

		String userId = request.getHeader("client-id");
		String httprequest = request.getDescription(true);
		String api = httprequest.substring(httprequest.lastIndexOf('/'), httprequest.lastIndexOf(';'));

		LOGGER.error("Handling the Rate Limit Exception for api::{}, userId ::{}", api, userId);

		StringBuilder errorMessage = new StringBuilder();
		errorMessage.append(userId).append(" has exceeded the rate limit for ").append(api)
				.append(" .Please wait for 5 minutes");

		return new ResponseEntity<>(errorMessage.toString(), HttpStatus.TOO_MANY_REQUESTS);
	}

	/**
	 * handler method for {@code UserNotFoundException} returns
	 * HttpStatus.UNAUTHORIZED error code
	 * 
	 * @param ex
	 *            incoming {@code UserNotFoundException} exception
	 * @param request
	 *            webrequest
	 * @return Object representing the error message
	 */
	@ExceptionHandler(UserNotFoundException.class)
	public final ResponseEntity<Object> handUserNotFound(UserNotFoundException ex, WebRequest request) {

		String userId = request.getHeader("client-id");

		StringBuilder errorMessage = new StringBuilder();
		errorMessage.append(userId).append(" does not exists, invalid user ");

		LOGGER.error("Handling the Rate Limit Exception for userId ::{}", userId);

		return new ResponseEntity<>(errorMessage.toString(), HttpStatus.UNAUTHORIZED);
	}

}
