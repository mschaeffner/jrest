package com.scibee.freya.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.scibee.freya.converter.Converter;
import com.scibee.freya.converter.LazyConverterFactory;
import com.scibee.freya.converter.exception.ExceptionConverterFactory;
import com.scibee.freya.converter.exception.LazyExceptionConverterFactory;

@Target(value = ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Mapping {

	String url() default "";

	Class<? extends Converter.ConverterFactory> converterFactory() default LazyConverterFactory.class;

	Class<? extends ExceptionConverterFactory> exceptionFactory() default LazyExceptionConverterFactory.class;
}
