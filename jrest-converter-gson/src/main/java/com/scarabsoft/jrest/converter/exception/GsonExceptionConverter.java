package com.scarabsoft.jrest.converter.exception;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;

public class GsonExceptionConverter<T extends RuntimeException> implements ExceptionConverter<T> {

	private final TypeAdapter<T> typeAdapter;

	public GsonExceptionConverter(TypeAdapter<T> typeAdapter) {
		this.typeAdapter = typeAdapter;
	}

	@Override
	public T convert(InputStream inputStream) throws IOException {
		return typeAdapter.read(new JsonReader(new InputStreamReader(inputStream)));
	}

	@Override
	public BodyConverter getBodyConverter() {
		throw new RuntimeException("Not Implemented yet.");
	}

}
