package com.joje.dbee.exception;

public class FileStorageException extends RuntimeException {
	private static final long serialVersionUID = 4914393828375823635L;

	public FileStorageException(String message) {
		super(message);
	}

	public FileStorageException(String message, Throwable cause) {
		super(message, cause);
	}
}
