package com.scibee.freya.converter;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.scibee.freya.converter.Converter.ConverterFactory;

public class GsonConverterFactory implements ConverterFactory {

	protected final Gson gson = new Gson();

	public Converter<?> get(Type type) {
		if (type.equals(void.class) || type.equals(Void.class)) {
			return new GsonConverter<>(null, type);
		}
		final TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
		return new GsonConverter<>(adapter, type);
	}

}
