package com.scarabsoft.jrest.converter.exception;

import java.io.IOException;
import java.io.InputStream;

public class LazyExceptionConverter implements ExceptionConverter<RuntimeException> {
	@Override
	public RuntimeException convert(InputStream stream) throws IOException {
		return new RuntimeException();
	}

	@Override
	public BodyConverter getBodyConverter() {
		throw new RuntimeException("Not Implemented yet.");
	}

}
