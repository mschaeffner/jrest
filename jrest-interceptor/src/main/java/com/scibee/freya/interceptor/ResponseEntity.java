package com.scibee.freya.interceptor;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ResponseEntity<T> {

	private final int statusCode;
	private final Map<String, String> headerMap = new HashMap<>();
	private final T object;

	public ResponseEntity(int statusCode, T object) {
		this.statusCode = statusCode;
		this.object = object;
	}

	public T getObject() {
		return object;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public Optional<String> getHeader(String key) {
		return Optional.ofNullable(headerMap.get(key));
	}

	public void addHeader(String key, String value) {
		headerMap.put(key, value);
	}

}
