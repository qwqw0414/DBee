package com.joje.dbee.exception;

public class HttpRequestException extends RuntimeException {

	private static final long serialVersionUID = 1852189122077766151L;

	public HttpRequestException(String message) {
		super(message);
	}

	public HttpRequestException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
