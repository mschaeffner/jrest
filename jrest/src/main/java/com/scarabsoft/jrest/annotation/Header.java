package com.scarabsoft.jrest.annotation;

import java.lang.annotation.*;

@Target(value = {ElementType.METHOD, ElementType.TYPE, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(value = Headers.class)
public @interface Header {

    String key();

    String value() default "";

}
