package com.blueoptima.ratelimiter.exception;

public class UserNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1403057783259478003L;

	/**
	 * 
	 */
	public UserNotFoundException() {
		super();

	}

	/**
	 * @param message
	 *            errorMessage for user not found
	 * @param cause
	 *            errorCause
	 */
	public UserNotFoundException(String message, Throwable cause) {
		super(message, cause);

	}

	/**
	 * @param message
	 *            errorMessage for user not found errorMessage for user not found
	 */
	public UserNotFoundException(String message) {
		super(message);

	}

}
