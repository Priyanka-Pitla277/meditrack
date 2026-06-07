package com.airtribe.meditrack.exception;

public class InvalidDataException extends RuntimeException {

	// 1. Unique serial version ID for serialization safety

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 2. Default constructor
	public InvalidDataException() {
		super();
	}

	// 3. Constructor that accepts a custom error message (Most Common)
	public InvalidDataException(String message) {
		super(message);
	}

	// 4. Constructor that accepts a custom message AND another root cause exception
	public InvalidDataException(String message, Throwable cause) {
		super(message, cause);
	}
}