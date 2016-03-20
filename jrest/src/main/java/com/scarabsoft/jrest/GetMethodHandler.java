package com.scarabsoft.jrest;

import com.scarabsoft.jrest.annotation.Body;
import com.scarabsoft.jrest.annotation.Get;
import com.scarabsoft.jrest.converter.body.BodyConverter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

final class GetMethodHandler extends AbstractMethodHandler {

    @Override
    protected String getUrl(Method method) {
        return method.getAnnotation(Get.class).value();
    }

    @Override
    protected BodyEntity getBodyEntity(BodyConverter converter, Method method, Object[] parameters) {
        for (Annotation annotation : method.getAnnotations()) {
            if (annotation instanceof Body) {
                throw new RuntimeException("@Body not supported for GET request");
            }
        }
        return null;
    }

}
