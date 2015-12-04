package com.scibee.freya.converter;

import java.lang.reflect.Type;

import com.scibee.freya.converter.Converter.ConverterFactory;

public class LazyConverterFactory implements ConverterFactory {

	@Override
	public Converter<?> get(Type type) {
		return new LazyConverter();
	}

}
