package com.scarabsoft.jrest.interceptor;

public class ParamEntity {

	private final String name;
	private final Object value;
    private final String filename;

	public ParamEntity(String name, Object value, String filename) {
		this.name = name;
		this.value = value;
        this.filename = filename;
	}

	public String getName() {
		return name;
	}

	public Object getValue() {
		return value;
	}

    public String getFilename() {
        return filename;
    }
}
