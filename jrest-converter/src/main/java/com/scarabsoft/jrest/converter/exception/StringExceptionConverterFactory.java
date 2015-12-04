package com.scarabsoft.jrest.converter.exception;


public class StringExceptionConverterFactory implements ExceptionConverter.ExceptionConverterFactory {
	@Override
	public ExceptionConverter<?> get() {
		return new StringExceptionConverter();
	}
}
