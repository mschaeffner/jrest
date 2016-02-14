package com.scarabsoft.jrest;

import com.scarabsoft.jrest.annotation.*;
import com.scarabsoft.jrest.converter.body.BodyConverter;
import org.apache.http.message.BasicHeader;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Collection;
import java.util.LinkedList;

abstract class AbstractMethodHandler {

    protected abstract String getUrl(Method method);

    String getUrl(String baseUrl, Method method, Object[] parameters) {
        String resultUrl = baseUrl + getUrl(method);
        final Parameter[] methodParameters = method.getParameters();
        for (int i = 0; i < methodParameters.length; i++) {
            final Parameter parameter = methodParameters[i];
            final Path pathVariable = parameter.getAnnotation(Path.class);
            if (pathVariable != null) {
                if (pathVariable.name().equals("")) {
                    throw new RuntimeException("name of Pathvariable is missing");
                } else {
                    resultUrl = replaceUrl(resultUrl, pathVariable.name(), String.valueOf(parameters[i]));
                }
            }
        }
        return resultUrl;
    }

    private String replaceUrl(final String url, final String name, final String value) {
        return url.replace("{" + name + "}", value);
    }

    Collection<ParamEntity> getParameterEntities(Method method, Object[] parameters) {
        final Collection<ParamEntity> result = new LinkedList<ParamEntity>();
        final Parameter[] methodParameters = method.getParameters();
        for (int i = 0; i < methodParameters.length; i++) {
            final Parameter parameter = methodParameters[i];
            final Param requestParam = parameter.getAnnotation(Param.class);
            if (requestParam != null) {
                if (requestParam.name().equals("")) {
                    throw new RuntimeException("name of RequestParam is missing");
                } else {
                    result.add(new ParamEntity(requestParam.name(), parameters[i], requestParam.filename()));
                }
            }
        }
        return result;
    }

    Collection<org.apache.http.Header> getHeaderEntities(Method method, Object[] parameters) {
        final Collection<org.apache.http.Header> result = AnnotationUtil.getHeaderEntities(method.getAnnotationsByType(Header.class));

        final Parameter[] methodParameters = method.getParameters();
        for (int i = 0; i < methodParameters.length; i++) {
            final Parameter parameter = methodParameters[i];
            final Header header = parameter.getAnnotation(Header.class);
            if (header != null) {
                final String value = String.valueOf(parameters[i]);
                if (value.equals("")) {
                    throw new RuntimeException("header " + header.key() + " needs a value");
                }
                result.add(new BasicHeader(header.key(), value));
            }
        }
        return result;
    }

    BodyEntity getBodyEntity(BodyConverter bodyConverter, Method method, Object[] parameters)
            throws UnsupportedEncodingException {
        for (int i = 0; i < method.getParameters().length; i++) {
            Body body = method.getParameters()[i].getAnnotation(Body.class);
            if (body != null) {
                return new BodyEntity(bodyConverter.toBody(parameters[i]), bodyConverter.getMimetype());
            }
        }
        return null;
    }
}
