package com.scibee.freya.test.exception;

public class SimpleException extends RuntimeException {

	private static final long serialVersionUID = -4642748580984580347L;

	private String message;
	private String path;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
