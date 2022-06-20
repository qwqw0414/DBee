package com.joje.dbee.exception;

public class BadRequestException extends RuntimeException {

	private static final long serialVersionUID = -1251345461683076117L;

	public BadRequestException(String message) {
		super(message);
	}

	public BadRequestException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
