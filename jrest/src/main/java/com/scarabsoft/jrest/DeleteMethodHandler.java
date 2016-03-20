package com.scarabsoft.jrest;

import com.scarabsoft.jrest.annotation.Body;
import com.scarabsoft.jrest.annotation.Delete;
import com.scarabsoft.jrest.converter.body.BodyConverter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

final class DeleteMethodHandler extends AbstractMethodHandler {
    @Override
    protected String getUrl(Method method) {
        return method.getAnnotation(Delete.class).value();
    }

    @Override
    protected BodyEntity getBodyEntity(BodyConverter converter, Method method, Object[] parameters) {
        for (Annotation annotation : method.getAnnotations()) {
            if (annotation instanceof Body) {
                throw new RuntimeException("@Body not supported for DELETE request");
            }
        }
        return null;
    }
}
