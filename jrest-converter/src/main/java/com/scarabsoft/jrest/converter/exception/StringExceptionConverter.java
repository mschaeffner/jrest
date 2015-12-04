package com.scarabsoft.jrest.converter.exception;

import java.io.IOException;
import java.io.InputStream;

import com.scarabsoft.jrest.util.IOUtil;

public class StringExceptionConverter implements ExceptionConverter<RuntimeException> {

	@Override
	public RuntimeException convert(InputStream stream) throws IOException {
		final String message = IOUtil.streamToString(stream);
		return new RuntimeException(message);
	}

	@Override
	public BodyConverter getBodyConverter() {
		throw new RuntimeException("Not Implemented yet.");
	}

}
