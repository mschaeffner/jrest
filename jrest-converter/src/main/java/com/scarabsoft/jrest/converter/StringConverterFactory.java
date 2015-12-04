package com.scarabsoft.jrest.converter;

import java.lang.reflect.Type;

public class StringConverterFactory implements Converter.ConverterFactory {

	@Override
	public Converter<?> get(Type type) {
		return new StringConverter();
	}

}
