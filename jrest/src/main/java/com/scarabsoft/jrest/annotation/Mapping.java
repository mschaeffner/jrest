package com.scarabsoft.jrest.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.scarabsoft.jrest.converter.Converter;
import com.scarabsoft.jrest.converter.exception.ExceptionConverterFactory;
import com.scarabsoft.jrest.converter.exception.LazyExceptionConverterFactory;
import com.scarabsoft.jrest.converter.LazyConverterFactory;

@Target(value = ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Mapping {

	String url() default "";

	Class<? extends Converter.ConverterFactory> converterFactory() default LazyConverterFactory.class;

	Class<? extends ExceptionConverterFactory> exceptionFactory() default LazyExceptionConverterFactory.class;
}
