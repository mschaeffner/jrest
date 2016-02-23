package com.scarabsoft.jrest;

import com.scarabsoft.jrest.annotation.Delete;
import com.scarabsoft.jrest.converter.body.BodyConverter;

import java.lang.reflect.Method;

final class DeleteMethodHandler extends AbstractMethodHandler {
    @Override
    protected String getUrl(Method method) {
        return method.getAnnotation(Delete.class).url();
    }

    @Override
    BodyEntity getBodyEntity(BodyConverter converter, Method method, Object[] parameters) {
        //TODO
//        for (final Parameter parameter : method.getParameters()) {
//            if (parameter.getAnnotation(Body.class) != null) {
//                throw new RuntimeException("@Body not supported for GET request");
//            }
//        }
        return null;
    }
}
