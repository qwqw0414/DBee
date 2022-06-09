package com.joje.dbee.exception;

public class DBeeException extends RuntimeException {

	private static final long serialVersionUID = 4868026318000140605L;

	public DBeeException() {
		super();
	}

	public DBeeException(String message, Throwable cause) {
		super(message, cause);
	}

	public DBeeException(String message) {
		super(message);
	}
	
	public DBeeException(Throwable cause) {
		super(cause);
	}
}
