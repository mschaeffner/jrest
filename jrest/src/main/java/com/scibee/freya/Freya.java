package com.scibee.freya;

import com.scibee.freya.annotation.*;
import com.scibee.freya.converter.Converter;
import com.scibee.freya.converter.LazyConverterFactory;
import com.scibee.freya.converter.exception.ExceptionConverterFactory;
import com.scibee.freya.converter.exception.LazyExceptionConverterFactory;
import com.scibee.freya.interceptor.HeaderEntity;
import com.scibee.freya.interceptor.RequestInterceptor;
import com.scibee.freya.interceptor.RequestInterceptorChain;
import com.scibee.freya.util.RequestInterceptorChainBuilder;
import org.apache.http.client.config.RequestConfig;

import java.lang.reflect.Proxy;
import java.util.Collection;
import java.util.LinkedList;

public final class Freya {

    private String baseUrl;
    private Converter.ConverterFactory converterFactory;
    private ExceptionConverterFactory exceptionFactory;
    private RequestConfig requestConfig;
    private RequestInterceptorChain requestInterceptorChain = new RequestInterceptorChain();

    private Freya() {
    }

    private void assertIsInterface(Class<?> clazz) {
        if (!clazz.isInterface()) {
            throw new RuntimeException("Can only create service out of an interface");
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T create(Class<?> clazz) {
        assertIsInterface(clazz);
        try {
            String baseUrl = (this.baseUrl != null) ? this.baseUrl : "";
            ExceptionConverterFactory exceptionConverterFactory = this.exceptionFactory;
            Converter.ConverterFactory factory = this.converterFactory;
            final Mapping requestMapping = clazz.getAnnotation(Mapping.class);
            if (requestMapping != null) {
                baseUrl += requestMapping.url();
                if (!requestMapping.converterFactory().equals(LazyConverterFactory.class)) {
                    factory = requestMapping.converterFactory().newInstance();
                }
                if (!requestMapping.exceptionFactory().equals(LazyExceptionConverterFactory.class)) {
                    exceptionConverterFactory = requestMapping.exceptionFactory().newInstance();
                }
            }

            if (factory == null || factory.equals(LazyConverterFactory.class)) {
                throw new RuntimeException("no ConverterFactory specified");
            }

            if (exceptionConverterFactory == null) {
                exceptionConverterFactory = new LazyExceptionConverterFactory();
            }

            final Collection<HeaderEntity> headerEntities = new LinkedList<HeaderEntity>();
            final Headers headers = clazz.getAnnotation(Headers.class);
            if (headers != null) {
                if (headers.headers() != null) {
                    for (int i = 0; i < headers.headers().length; i++) {
                        final Header header = headers.headers()[i];
                        //TODO remove if statement below
                        if (header.value().equals("")) {
                            throw new RuntimeException(header.key() + " needs a value");
                        }
                        headerEntities.add(new HeaderEntity(header.key(), header.value()));
                    }
                }
            }

            final RequestInterceptorChain chain = RequestInterceptorChainBuilder.create(requestInterceptorChain,
                    clazz.getAnnotation(Interceptors.class), clazz.getAnnotation(Interceptor.class));

            return (T) Proxy.newProxyInstance(clazz.getClassLoader(), //
                    new Class<?>[]{clazz},//
                    new FreyaInvocationHandler(baseUrl,//
                            factory, //
                            exceptionConverterFactory,//
                            chain,//
                            requestConfig,//
                            headerEntities));

        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public final static class Builder {
        private final Freya buildResult = new Freya();

        public Builder baseUrl(String baseUrl) {
            buildResult.baseUrl = baseUrl;
            return this;
        }

        public Builder addInterceptor(RequestInterceptor requestInterceptor) {
            buildResult.requestInterceptorChain.addInterceptor(requestInterceptor);
            return this;
        }

        public Builder coverterFactory(Converter.ConverterFactory converterFactory) {
            buildResult.converterFactory = converterFactory;
            return this;
        }

        public Builder exceptionFactory(ExceptionConverterFactory exceptionFactory) {
            buildResult.exceptionFactory = exceptionFactory;
            return this;
        }

        public Builder requestConfig(RequestConfig requestConfig) {
            buildResult.requestConfig = requestConfig;
            return this;
        }

        public Freya build() {
            return buildResult;
        }
    }

}
