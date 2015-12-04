package com.scibee.freya.converter.exception;


public class StringExceptionConverterFactory implements ExceptionConverterFactory {
	@Override
	public ExceptionConverter<?> get() {
		return new StringExceptionConverter();
	}
}
