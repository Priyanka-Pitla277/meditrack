package com.airtribe.learntrack.exception;

public class InvalidDataException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 2. Create a constructor that accepts a message
    public InvalidDataException(String message) {
        // 3. Pass the message to the parent (Throwable) class
        super(message);
    }
}