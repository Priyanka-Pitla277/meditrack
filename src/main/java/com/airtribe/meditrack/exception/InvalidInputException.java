package com.airtribe.meditrack.exception;

public class InvalidInputException extends RuntimeException {

	// 1. Unique serial version ID for serialization safety

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 2. Default constructor
	public InvalidInputException() {
		super();
	}

	// 3. Constructor that accepts a custom error message (Most Common)
	public InvalidInputException(String message) {
		super(message);
	}

	// 4. Constructor that accepts a custom message AND another root cause exception
	public InvalidInputException(String message, Throwable cause) {
		super(message, cause);
	}
}