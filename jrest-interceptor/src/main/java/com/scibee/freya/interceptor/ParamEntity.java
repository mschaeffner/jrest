package com.scibee.freya.interceptor;

public class ParamEntity {

	private final String name;
	private final Object value;

	public ParamEntity(String name, Object value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public Object getValue() {
		return value;
	}

}
