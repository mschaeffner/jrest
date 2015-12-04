package com.scibee.freya.test.converter;

import com.scibee.freya.converter.exception.GsonExceptionConverterFactory;
import com.scibee.freya.test.exception.SpringDefaultException;

public class SpringDefaultExceptionConverterFactory extends GsonExceptionConverterFactory {

	@Override
	protected Class<? extends RuntimeException> getReturnType() {
		return SpringDefaultException.class;
	}

}
