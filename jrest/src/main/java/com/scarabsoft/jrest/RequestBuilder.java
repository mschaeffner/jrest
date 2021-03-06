package com.scarabsoft.jrest;

import com.scarabsoft.jrest.annotation.Delete;
import com.scarabsoft.jrest.annotation.Get;
import com.scarabsoft.jrest.annotation.Post;
import com.scarabsoft.jrest.annotation.Put;
import com.scarabsoft.jrest.converter.Converter;
import com.scarabsoft.jrest.converter.body.BodyConverter;
import com.scarabsoft.jrest.converter.exception.ExceptionConverter;
import org.apache.http.Header;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.Collection;

final class RequestBuilder {

    private final String baseUrl;
    private final Converter<?> converter;
    private final BodyConverter bodyConverter;
    private final ExceptionConverter<?> exceptionConverter;
    private final RequestConfig requestConfig;
    private final Collection<Header> headers;
    private final Class<? extends Collection> collectionClazz;

    RequestBuilder(String baseUrl,
                   Converter<?> converter,
                   BodyConverter bodyConverter,
                   ExceptionConverter<?> exceptionConverter,
                   RequestConfig requestConfig,
                   Collection<Header> headers,
                   Class<? extends Collection> collectionClazz) {
        this.baseUrl = baseUrl;
        this.converter = converter;
        this.bodyConverter = bodyConverter;
        this.exceptionConverter = exceptionConverter;
        this.requestConfig = requestConfig;
        this.headers = headers;
        this.collectionClazz = collectionClazz;
    }

    AbstractRequestEntity build(Method method, Object[] parameters, HttpClient httpClient) {
        final AbstractMethodHandler methodHandler = resolveMethodHandler(method);
        final String url = methodHandler.getUrl(baseUrl, method, parameters);
        final Collection<ParamEntity> requestParameterEntities = methodHandler.getParameterEntities(method, parameters);
        final Collection<Header> headers = methodHandler.getHeaderEntities(method, parameters);
        headers.addAll(this.headers);

        BodyEntity body;
        try {
            body = methodHandler.getBodyEntity(bodyConverter, method, parameters);
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
                    headers,
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
                    headers,
                    ((PostMethodHandler)methodHandler).isMultipart(method),
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
                    headers,
                    ((PutMethodHandler)methodHandler).isMultipart(method),
                    requestParameterEntities,
                    body,
                    requestConfig,
                    httpClient,
                    method.getReturnType(),
                    collectionClazz);

        } else if (method.getAnnotation(Delete.class) != null) {

            if (requestParameterEntities.size() != 0) {
                throw new RuntimeException("delete request is not allowed to have request parameters");
            }

            result = new DeleteRequestEntity(
                    url,
                    converter,
                    exceptionConverter,
                    headers,
                    requestConfig,
                    httpClient,
                    method.getReturnType(),
                    collectionClazz);
        } else {
            throw new RuntimeException("could not build " + method.getName() + " request");
        }
        return result;
    }

    private AbstractMethodHandler resolveMethodHandler(Method method) {
        if (method.getAnnotation(Get.class) != null) {
            return new GetMethodHandler();
        }
        if (method.getAnnotation(Post.class) != null) {
            return new PostMethodHandler();
        }
        if (method.getAnnotation(Put.class) != null) {
            return new PutMethodHandler();
        }

        if (method.getAnnotation(Delete.class) != null) {
            return new DeleteMethodHandler();
        }

        throw new RuntimeException("no method handler found");
    }

}
