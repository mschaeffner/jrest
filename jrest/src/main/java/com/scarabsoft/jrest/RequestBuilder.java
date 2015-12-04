package com.scarabsoft.jrest;

import com.scarabsoft.jrest.annotation.Get;
import com.scarabsoft.jrest.annotation.Post;
import com.scarabsoft.jrest.annotation.Put;
import com.scarabsoft.jrest.converter.Converter;
import com.scarabsoft.jrest.converter.exception.ExceptionConverter;
import com.scarabsoft.jrest.interceptor.BodyEntity;
import com.scarabsoft.jrest.interceptor.HeaderEntity;
import com.scarabsoft.jrest.interceptor.ParamEntity;
import com.scarabsoft.jrest.methodhandler.GetMethodHandler;
import com.scarabsoft.jrest.methodhandler.MethodHandler;
import com.scarabsoft.jrest.methodhandler.PostMethodHandler;
import com.scarabsoft.jrest.methodhandler.PutMethodHandler;
import com.scarabsoft.jrest.request.AbstractRequestEntity;
import com.scarabsoft.jrest.request.GetRequestEntity;
import com.scarabsoft.jrest.request.PostRequestEntity;
import com.scarabsoft.jrest.request.PutRequestEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.Collection;

public class RequestBuilder {

    private final String baseUrl;
    private final Converter<?> converter;
    private final ExceptionConverter<?> exceptionConverter;
    private final RequestConfig requestConfig;
    private final Collection<HeaderEntity> headerEntities;

    private Class<? extends Collection> collectionClazz;

    public RequestBuilder(String baseUrl,
                          Converter<?> converter,
                          ExceptionConverter<?> exceptionConverter,
                          RequestConfig requestConfig,
                          Collection<HeaderEntity> headerEntities,
                          Class<? extends Collection> collectionClazz) {
        this.baseUrl = baseUrl;
        this.converter = converter;
        this.exceptionConverter = exceptionConverter;
        this.requestConfig = requestConfig;
        this.headerEntities = headerEntities;
        this.collectionClazz = collectionClazz;
    }

    public AbstractRequestEntity build(Method method, Object[] parameters, HttpClient httpClient) {
        final MethodHandler methodHandler = resolveMethodHandler(method);
        final String url = methodHandler.getUrl(baseUrl, method, parameters);
        final Collection<ParamEntity> requestParameterEntities = methodHandler.getParameterEntities(method, parameters);
        final Collection<HeaderEntity> headerEntities = methodHandler.getHeaderEntities(method, parameters);
        headerEntities.addAll(this.headerEntities);

        BodyEntity body = null;
        try {
            body = methodHandler.getBodyEntity(converter.getBodyConverter(), method, parameters);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("could not convert object to byte[]");
        }

        if (body != null && requestParameterEntities.size() > 0) {
            throw new RuntimeException("could not use request parameter together with request body");
        }

        AbstractRequestEntity result = null;
        if (method.getAnnotation(Get.class) != null) {

            result = new GetRequestEntity(
                    url,
                    converter,
                    exceptionConverter,
                    headerEntities,
                    requestParameterEntities,
                    requestConfig,
                    httpClient,
                    method.getReturnType(),
                    collectionClazz);

        } else if (method.getAnnotation(Post.class) != null) {

            result = new PostRequestEntity(
                    url,
                    converter,
                    exceptionConverter,
                    headerEntities,
                    requestParameterEntities,
                    body,
                    requestConfig,
                    httpClient,
                    method.getReturnType(),
                    collectionClazz);

        } else if (method.getAnnotation(Put.class) != null) {

            result = new PutRequestEntity(
                    url,
                    converter,
                    exceptionConverter,
                    headerEntities,
                    requestParameterEntities,
                    body,
                    requestConfig,
                    httpClient,
                    method.getReturnType(),
                    collectionClazz);

        } else {
            throw new RuntimeException("could not build request");
        }
        return result;
    }

    private MethodHandler resolveMethodHandler(Method method) {
        if (method.getAnnotation(Get.class) != null) {
            return new GetMethodHandler();
        }
        if (method.getAnnotation(Post.class) != null) {
            return new PostMethodHandler();
        }
        if (method.getAnnotation(Put.class) != null) {
            return new PutMethodHandler();
        }
        throw new RuntimeException("not method handler found");
    }

}
