package com.scarabsoft.jrest.converter;

import com.scarabsoft.jrest.converter.body.BodyConverter;

import java.lang.reflect.Type;

public class LazyConverterFactory implements ConverterFactory {

	@Override
	public Converter<?> getConverter(Type type) {
		return new LazyConverter();
	}

	@Override
	public BodyConverter getBodyConverter() {
		return null;
	}

}
