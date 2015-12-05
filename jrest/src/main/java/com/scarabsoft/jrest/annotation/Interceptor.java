package com.scarabsoft.jrest.annotation;

import com.scarabsoft.jrest.interceptor.RequestInterceptorFactory;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Repeatable(value = Interceptors.class)
public @interface Interceptor {

    Class<? extends RequestInterceptorFactory> factory();

}
