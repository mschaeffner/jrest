package com.scarabsoft.jrest;

import com.scarabsoft.jrest.annotation.Get;
import com.scarabsoft.jrest.converter.body.BodyConverter;

import java.lang.reflect.Method;

final class GetMethodHandler extends AbstractMethodHandler {

    @Override
    protected String getUrl(Method method) {
        return method.getAnnotation(Get.class).url();
    }

    @Override
    public BodyEntity getBodyEntity(BodyConverter converter, Method method, Object[] parameters) {
        //TODO
//        for (final Parameter parameter : method.getParameters()) {
//            if (parameter.getAnnotation(Body.class) != null) {
//                throw new RuntimeException("@Body not supported for GET request");
//            }
//        }
        return null;
    }

}
