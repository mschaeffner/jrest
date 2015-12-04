package com.scarabsoft.jrest.converter.exception;


public class LazyExceptionConverterFactory implements ExceptionConverterFactory {
	@Override
	public ExceptionConverter<?> get() {
		return new LazyExceptionConverter();
	}
}
