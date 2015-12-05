package com.scarabsoft.jrest;

import com.scarabsoft.jrest.annotation.Body;
import com.scarabsoft.jrest.annotation.Get;
import com.scarabsoft.jrest.converter.body.BodyConverter;
import com.scarabsoft.jrest.interceptor.BodyEntity;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

class GetMethodHandler extends AbstractMethodHandler {

    @Override
    protected String getUrl(Method method) {
        return method.getAnnotation(Get.class).url();
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
