package com.scibee.freya.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.scibee.freya.interceptor.RequestInterceptorFactory;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = { ElementType.TYPE, ElementType.METHOD })
public @interface Interceptor {

	Class<? extends RequestInterceptorFactory> factory();

}
