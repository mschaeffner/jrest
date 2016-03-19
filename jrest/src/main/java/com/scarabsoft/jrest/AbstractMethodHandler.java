package com.scarabsoft.jrest;

import com.scarabsoft.jrest.annotation.*;
import com.scarabsoft.jrest.converter.body.BodyConverter;
import org.apache.http.message.BasicHeader;

import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

abstract class AbstractMethodHandler {

    protected abstract String getUrl(Method method);

    String getUrl(String baseUrl, Method method, Object[] parameters) {
        String resultUrl = baseUrl + getUrl(method);

        int counter = 0;
        final List<Annotation> annotations = Util.getAnnotations(method.getParameterAnnotations());
        for (Annotation annotation : annotations) {
            if (annotation instanceof Path == false) {
                counter++;
                continue;
            }
            final Path pathVariable = (Path) annotation;
            if (pathVariable.value().equals("")) {
                throw new RuntimeException("value of Pathvariable is missing");
            } else {
                resultUrl = replaceUrl(resultUrl, pathVariable.value(), String.valueOf(parameters[counter++]));
            }
        }
        return resultUrl;
    }

    private String replaceUrl(final String url, final String name, final String value) {
        return url.replace("{" + name + "}", value);
    }

    Collection<ParamEntity> getParameterEntities(Method method, Object[] parameters) {
        final Collection<ParamEntity> result = new LinkedList<ParamEntity>();

        List<Annotation> annotations = Util.getAnnotations(method.getParameterAnnotations());
        int counter = 0;
        for (Annotation annotation : annotations) {
            if (annotation instanceof Param == false) {
                counter++;
                continue;
            }

            final Param requestParam = (Param) annotation;
            if (requestParam.value().equals("")) {
                throw new RuntimeException("value of RequestParam is missing");
            } else {
                result.add(new ParamEntity(requestParam.value(), parameters[counter++], requestParam.filename(), requestParam.contentType()));
            }
        }

        return result;
    }

    Collection<org.apache.http.Header> getHeaderEntities(Method method, Object[] parameters) {
        final Collection<org.apache.http.Header> result = new LinkedList<>();



        for (Annotation annotation : method.getAnnotations()) {
            if (annotation instanceof Headers == false) {
                continue;
            }
            final Headers headers = (Headers) annotation;
            for (Header header : headers.value()) {

//                final String value = String.valueOf(parameters[temp + counter]);
                if (header.value().equals("")) {
                    throw new RuntimeException("header " + header.key() + " needs a value");
                }
                result.add(new BasicHeader(header.key(), header.value()));
            }


        }

        int counter = 0;

        for (Annotation annotation : method.getAnnotations()) {
            if (annotation instanceof Header == false) {
                counter++;
                continue;
            }
            final Header header = (Header) annotation;
            final String value = String.valueOf(parameters[counter++]);
            if (value.equals("")) {
                throw new RuntimeException("header " + header.key() + " needs a value");
            }
            result.add(new BasicHeader(header.key(), value));
        }


        List<Annotation> annotations = Util.getAnnotations(method.getParameterAnnotations());
        counter = 0;
        for (Annotation annotation : annotations) {
            if (annotation instanceof Header == false) {
                counter++;
                continue;
            }

            final Header header = (Header) annotation;
            final String value = String.valueOf(parameters[counter++]);
            if (value.equals("")) {
                throw new RuntimeException("header " + header.key() + " needs a value");
            }
            result.add(new BasicHeader(header.key(), value));
        }

        return result;
    }

    BodyEntity getBodyEntity(BodyConverter bodyConverter, Method method, Object[] parameters)
            throws UnsupportedEncodingException {

        int counter = 0;
        List<Annotation> annotations = Util.getAnnotations(method.getParameterAnnotations());
        for (Annotation annotation : annotations) {
            if (annotation instanceof Body == false) {
                counter++;
                continue;
            }

            Body body = (Body) annotation;
            return new BodyEntity(bodyConverter.toBody(parameters[counter++]), bodyConverter.getMimetype());
        }
        return null;
    }
}
