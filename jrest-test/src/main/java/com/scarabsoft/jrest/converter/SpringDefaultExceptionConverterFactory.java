package com.scarabsoft.jrest.converter;

import com.scarabsoft.jrest.converter.exception.GsonExceptionConverterFactory;
import com.scarabsoft.jrest.exception.SpringDefaultException;

public class SpringDefaultExceptionConverterFactory extends GsonExceptionConverterFactory {

	@Override
	protected Class<? extends RuntimeException> getReturnType() {
		return SpringDefaultException.class;
	}

}
