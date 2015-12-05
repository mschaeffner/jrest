package com.scarabsoft.jrest.converter;

import com.scarabsoft.jrest.converter.exception.GsonExceptionConverterFactory;
import com.scarabsoft.jrest.exception.SimpleException;

public class SimpleExceptionConverterFactory extends GsonExceptionConverterFactory {

	@Override
	protected Class<? extends RuntimeException> getReturnType() {
		return SimpleException.class;
	}

}
