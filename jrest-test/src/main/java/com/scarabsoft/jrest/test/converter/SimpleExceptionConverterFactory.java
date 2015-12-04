package com.scarabsoft.jrest.test.converter;

import com.scarabsoft.jrest.converter.exception.GsonExceptionConverterFactory;
import com.scarabsoft.jrest.test.exception.SimpleException;

public class SimpleExceptionConverterFactory extends GsonExceptionConverterFactory {

	@Override
	protected Class<? extends RuntimeException> getReturnType() {
		return SimpleException.class;
	}

}
