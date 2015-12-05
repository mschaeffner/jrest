package com.scarabsoft.jrest;

import com.scarabsoft.jrest.annotation.Body;
import com.scarabsoft.jrest.annotation.Delete;
import com.scarabsoft.jrest.converter.body.BodyConverter;
import com.scarabsoft.jrest.interceptor.BodyEntity;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class DeleteMethodHandler extends AbstractMethodHandler {
    @Override
    protected String getUrl(Method method) {
        return method.getAnnotation(Delete.class).url();
    }

    @Override
    public BodyEntity getBodyEntity(BodyConverter converter, Method method, Object[] parameters) {
        for (final Parameter parameter : method.getParameters()) {
            if (parameter.getAnnotation(Body.class) != null) {
                throw new RuntimeException("@Body not supported for GET request");
            }
        }
        return null;
    }
}
