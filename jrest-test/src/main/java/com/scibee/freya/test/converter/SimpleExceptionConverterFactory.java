package com.scibee.freya.test.converter;

import com.scibee.freya.converter.exception.GsonExceptionConverterFactory;
import com.scibee.freya.test.exception.SimpleException;

public class SimpleExceptionConverterFactory extends GsonExceptionConverterFactory {

	@Override
	protected Class<? extends RuntimeException> getReturnType() {
		return SimpleException.class;
	}

}
