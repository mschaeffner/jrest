package com.scarabsoft.jrest.converter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.scarabsoft.jrest.converter.Converter.ConverterFactory;

import java.lang.reflect.Type;

public class GsonConverterFactory implements ConverterFactory {

	protected final Gson gson = new Gson();

	public Converter<?> get(Type type) {
		final TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
		return new GsonConverter<>(adapter, type);
	}

}
