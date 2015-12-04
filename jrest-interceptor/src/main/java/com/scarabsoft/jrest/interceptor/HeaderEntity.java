package com.scarabsoft.jrest.interceptor;

public final class HeaderEntity {

	private final String key;
	private final String value;

	public HeaderEntity(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}

}
