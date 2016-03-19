package com.scarabsoft.jrest;

import com.scarabsoft.jrest.converter.*;
import com.scarabsoft.jrest.converter.exception.ExceptionConverter;
import com.scarabsoft.jrest.interceptor.RequestInterceptorChain;
import org.apache.http.Header;
import org.apache.http.client.config.RequestConfig;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.*;

final class JRestInvocationHandler implements java.lang.reflect.InvocationHandler {

    private final String baseUrl;
    private final ConverterFactory converterFactory;
    private final ExceptionConverter.ExceptionConverterFactory exceptionConverterFactory;
    private final RequestInterceptorChain interceptorChain;
    private final RequestConfig requestConfig;
    private final Collection<Header> headers;
    private final HttpClientFactory httpClientFactory;

    JRestInvocationHandler(String baseUrl,
                           ConverterFactory converterFactory,
                           ExceptionConverter.ExceptionConverterFactory exceptionConverterFactory,
                           RequestInterceptorChain interceptorChain,
                           RequestConfig requestConfig,
                           Collection<Header> headers,
                           HttpClientFactory httpClientFactory) {
        this.baseUrl = baseUrl;
        this.converterFactory = converterFactory;
        this.exceptionConverterFactory = exceptionConverterFactory;
        this.interceptorChain = interceptorChain;
        this.requestConfig = requestConfig;
        this.headers = headers;
        this.httpClientFactory = httpClientFactory;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        final Class<?> returnClazz;

        Class<? extends Collection> collectionClazz = null;

        if (method.getReturnType().equals(ResponseEntity.class)) {
            ParameterizedType genericType = (ParameterizedType) method.getGenericReturnType();
            if (genericType.getActualTypeArguments()[0] instanceof ParameterizedType) {
                ParameterizedType collectionType = (ParameterizedType) genericType.getActualTypeArguments()[0];
                if (collectionType.getRawType().equals(Collection.class)) {
                    returnClazz = (Class<?>) collectionType.getActualTypeArguments()[0];
                    collectionClazz = LinkedList.class;
                } else if (collectionType.getRawType().equals(List.class)) {
                    returnClazz = (Class<?>) collectionType.getActualTypeArguments()[0];
                    collectionClazz = LinkedList.class;
                } else if (collectionType.getRawType().equals(Set.class)) {
                    returnClazz = (Class<?>) collectionType.getActualTypeArguments()[0];
                    collectionClazz = HashSet.class;
                } else {
                    returnClazz = (Class<?>) genericType.getActualTypeArguments()[0];
                }
            } else {
                returnClazz = (Class<?>) genericType.getActualTypeArguments()[0];
            }
        } else if (method.getReturnType().isAssignableFrom(Collection.class)) {
            final ParameterizedType genericType = (ParameterizedType) method.getGenericReturnType();
            returnClazz = (Class<?>) genericType.getActualTypeArguments()[0];
            collectionClazz = LinkedList.class;
        } else if (method.getReturnType().isAssignableFrom(List.class)) {
            final ParameterizedType genericType = (ParameterizedType) method.getGenericReturnType();
            returnClazz = (Class<?>) genericType.getActualTypeArguments()[0];
            collectionClazz = LinkedList.class;
        } else if (method.getReturnType().isAssignableFrom(Set.class)) {
            final ParameterizedType genericType = (ParameterizedType) method.getGenericReturnType();
            returnClazz = (Class<?>) genericType.getActualTypeArguments()[0];
            collectionClazz = HashSet.class;
        } else {
            returnClazz = method.getReturnType();
        }


        final RequestBuilder builder = new RequestBuilder(baseUrl,
                resolveConverter(returnClazz),
                converterFactory.getBodyConverter(),
                exceptionConverterFactory.get(),
                requestConfig,
                headers, collectionClazz);


        final AbstractRequestEntity request = builder
                .build(method,
                        args,
                        httpClientFactory.get());

        request.execute(RequestInterceptorChainBuilder.create(
                interceptorChain,
                Util.getInterceptors(method)));

        if (request.getResponseClazz().equals(ResponseEntity.class)) {
            return request.getResponse();
        }
        return request.getResult();
    }

    private Converter<?> resolveConverter(Class<?> returnClazz) {
        if (returnClazz.equals(void.class) || returnClazz.equals(Void.class)) {
            return new VoidConverter();
        }

        if (returnClazz.equals(byte[].class) || returnClazz.equals(Byte[].class)) {
            return new ByteArrayConverter();
        }

        if (returnClazz.equals(InputStream.class)) {
            return new InputStreamConverter();
        }

        return converterFactory.getConverter(returnClazz);
    }
}
