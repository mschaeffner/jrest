package com.scarabsoft.jrest.converter.exception;


public class LazyExceptionConverterFactory implements ExceptionConverter.ExceptionConverterFactory {
	@Override
	public ExceptionConverter<?> get() {
		return new LazyExceptionConverter();
	}
}
