package com.scarabsoft.jrest.annotation;

import com.scarabsoft.jrest.converter.ConverterFactory;
import com.scarabsoft.jrest.converter.LazyConverterFactory;
import com.scarabsoft.jrest.converter.exception.ExceptionConverter;
import com.scarabsoft.jrest.converter.exception.StringExceptionConverterFactory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Mapping {

    String url() default "";

    Class<? extends ConverterFactory> converterFactory() default LazyConverterFactory.class;

    Class<? extends ExceptionConverter.ExceptionConverterFactory> exceptionFactory() default StringExceptionConverterFactory.class;
}
