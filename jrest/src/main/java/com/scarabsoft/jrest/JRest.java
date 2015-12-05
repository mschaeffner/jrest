package com.scarabsoft.jrest;

import com.scarabsoft.jrest.annotation.Headers;
import com.scarabsoft.jrest.annotation.Interceptor;
import com.scarabsoft.jrest.annotation.Interceptors;
import com.scarabsoft.jrest.annotation.Mapping;
import com.scarabsoft.jrest.converter.Converter;
import com.scarabsoft.jrest.converter.LazyConverterFactory;
import com.scarabsoft.jrest.converter.exception.ExceptionConverter;
import com.scarabsoft.jrest.converter.exception.StringExceptionConverterFactory;
import com.scarabsoft.jrest.interceptor.RequestInterceptor;
import com.scarabsoft.jrest.interceptor.RequestInterceptorChain;
import org.apache.http.Header;
import org.apache.http.client.config.RequestConfig;

import java.lang.reflect.Proxy;
import java.util.Collection;

public final class JRest {

    private String baseUrl;
    private Converter.ConverterFactory converterFactory;
    private ExceptionConverter.ExceptionConverterFactory exceptionFactory;
    private RequestConfig requestConfig;
    private RequestInterceptorChain requestInterceptorChain = new RequestInterceptorChain();

    private JRest() {
    }

    private void assertIsInterface(Class<?> clazz) {
        if (!clazz.isInterface()) {
            throw new RuntimeException("JRest can only be used with interfaces");
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T create(Class<?> clazz) {
        assertIsInterface(clazz);
        try {
            String baseUrl = (this.baseUrl != null) ? this.baseUrl : "";
            ExceptionConverter.ExceptionConverterFactory exceptionConverterFactory = this.exceptionFactory;
            Converter.ConverterFactory factory = this.converterFactory;
            final Mapping requestMapping = clazz.getAnnotation(Mapping.class);
            if (requestMapping != null) {
                baseUrl += requestMapping.url();
                if (!requestMapping.converterFactory().equals(LazyConverterFactory.class)) {
                    factory = requestMapping.converterFactory().newInstance();
                }
                if (!requestMapping.exceptionFactory().equals(StringExceptionConverterFactory.class)) {
                    exceptionConverterFactory = requestMapping.exceptionFactory().newInstance();
                }
            }

            if (factory == null || factory.equals(LazyConverterFactory.class)) {
                throw new RuntimeException("no ConverterFactory specified");
            }

            if (exceptionConverterFactory == null) {
                exceptionConverterFactory = new StringExceptionConverterFactory();
            }

            final Collection<Header> headerEntities = AnnotationUtil.getHeaderEntities(clazz.getAnnotation(Headers.class));

            final RequestInterceptorChain chain = RequestInterceptorChainBuilder.create(requestInterceptorChain,
                    clazz.getAnnotation(Interceptors.class), clazz.getAnnotation(Interceptor.class));

            return (T) Proxy.newProxyInstance(clazz.getClassLoader(),
                    new Class<?>[]{clazz},
                    new JRestInvocationHandler(baseUrl,
                            factory,
                            exceptionConverterFactory,
                            chain,
                            requestConfig,
                            headerEntities));

        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public final static class Builder {
        private final JRest buildResult = new JRest();

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

        public Builder exceptionFactory(ExceptionConverter.ExceptionConverterFactory exceptionFactory) {
            buildResult.exceptionFactory = exceptionFactory;
            return this;
        }

        public Builder requestConfig(RequestConfig requestConfig) {
            buildResult.requestConfig = requestConfig;
            return this;
        }

        public JRest build() {
            return buildResult;
        }
    }

}
