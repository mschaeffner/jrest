package com.scarabsoft.jrest;

import com.scarabsoft.jrest.annotation.Interceptor;
import com.scarabsoft.jrest.annotation.Interceptors;
import com.scarabsoft.jrest.converter.Converter;
import com.scarabsoft.jrest.converter.exception.ExceptionConverterFactory;
import com.scarabsoft.jrest.interceptor.HeaderEntity;
import com.scarabsoft.jrest.interceptor.RequestInterceptorChain;
import com.scarabsoft.jrest.interceptor.ResponseEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClientBuilder;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.*;

public final class JRestInvocationHandler implements java.lang.reflect.InvocationHandler {

    private final String baseUrl;
    private final Converter.ConverterFactory converterFactory;
    private final ExceptionConverterFactory exceptionConverterFactory;
    private final RequestInterceptorChain interceptorChain;
    private final RequestConfig requestConfig;
    private final Collection<HeaderEntity> headerEntities;

    public JRestInvocationHandler(String baseUrl, //
                                  Converter.ConverterFactory converterFactory, //
                                  ExceptionConverterFactory exceptionConverterFactory, //
                                  RequestInterceptorChain interceptorChain, //
                                  RequestConfig requestConfig, //
                                  Collection<HeaderEntity> headerEntities) {
        this.baseUrl = baseUrl;
        this.converterFactory = converterFactory;
        this.exceptionConverterFactory = exceptionConverterFactory;
        this.interceptorChain = interceptorChain;
        this.requestConfig = requestConfig;
        this.headerEntities = headerEntities;
    }

    // TODO request entities should not get interceptions out of annotation -->
    // create a more general interceptoro chain
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        final boolean isCollection;
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
                converterFactory.get(returnClazz),
                exceptionConverterFactory.get(),
                requestConfig,
                headerEntities, collectionClazz);
        final AbstractRequestEntity request = builder.build(method, args, HttpClientBuilder.create().build());
        request.execute(RequestInterceptorChainBuilder.create(
                interceptorChain,
                method.getAnnotation(Interceptors.class),
                method.getAnnotation(Interceptor.class)));
        if (request.getResponseClazz().equals(ResponseEntity.class)) {
            return request.getResponse();
        }
        return request.getResult();
    }
}
