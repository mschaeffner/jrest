package com.scibee.freya;

import com.scibee.freya.annotation.Interceptor;
import com.scibee.freya.annotation.Interceptors;
import com.scibee.freya.converter.Converter;
import com.scibee.freya.converter.exception.ExceptionConverterFactory;
import com.scibee.freya.interceptor.HeaderEntity;
import com.scibee.freya.interceptor.RequestInterceptorChain;
import com.scibee.freya.interceptor.ResponseEntity;
import com.scibee.freya.request.AbstractRequestEntity;
import com.scibee.freya.util.RequestInterceptorChainBuilder;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClientBuilder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.*;

public final class FreyaInvocationHandler implements InvocationHandler {

    private final String baseUrl;
    private final Converter.ConverterFactory converterFactory;
    private final ExceptionConverterFactory exceptionConverterFactory;
    private final RequestInterceptorChain interceptorChain;
    private final RequestConfig requestConfig;
    private final Collection<HeaderEntity> headerEntities;

    public FreyaInvocationHandler(String baseUrl, //
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
                    isCollection = true;
                    collectionClazz = LinkedList.class;
                } else if (collectionType.getRawType().equals(List.class)) {
                    returnClazz = (Class<?>) collectionType.getActualTypeArguments()[0];
                    isCollection = true;
                    collectionClazz = LinkedList.class;
                } else if (collectionType.getRawType().equals(Set.class)) {
                    returnClazz = (Class<?>) collectionType.getActualTypeArguments()[0];
                    isCollection = true;
                    collectionClazz = HashSet.class;
                } else {
                    returnClazz = (Class<?>) genericType.getActualTypeArguments()[0];
                    isCollection = false;
                }
            } else {
                returnClazz = (Class<?>) genericType.getActualTypeArguments()[0];
                isCollection = false;
            }
        } else if (method.getReturnType().isAssignableFrom(Collection.class)) {
            final ParameterizedType genericType = (ParameterizedType) method.getGenericReturnType();
            returnClazz = (Class<?>) genericType.getActualTypeArguments()[0];
            isCollection = true;
            collectionClazz = LinkedList.class;

        } else if (method.getReturnType().isAssignableFrom(List.class)) {
            final ParameterizedType genericType = (ParameterizedType) method.getGenericReturnType();
            returnClazz = (Class<?>) genericType.getActualTypeArguments()[0];
            isCollection = true;
            collectionClazz = LinkedList.class;
        } else if (method.getReturnType().isAssignableFrom(Set.class)) {
            final ParameterizedType genericType = (ParameterizedType) method.getGenericReturnType();
            returnClazz = (Class<?>) genericType.getActualTypeArguments()[0];
            isCollection = true;
            collectionClazz = HashSet.class;
        } else {
            returnClazz = method.getReturnType();
            isCollection = false;
        }

        final RequestBuilder builder = new RequestBuilder(baseUrl, //
                converterFactory.get(returnClazz), //
                exceptionConverterFactory.get(), //
                requestConfig, //
                headerEntities, isCollection, collectionClazz);
        final AbstractRequestEntity request = builder.build(method, args, HttpClientBuilder.create().build());
        request.execute(RequestInterceptorChainBuilder.create(//
                interceptorChain, //
                method.getAnnotation(Interceptors.class), //
                method.getAnnotation(Interceptor.class)));
        if (request.getResponseClazz().equals(ResponseEntity.class)) {
            return request.getResponse();
        }
        return request.getResult();
    }
}
