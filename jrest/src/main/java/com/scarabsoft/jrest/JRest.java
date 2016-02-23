package com.scarabsoft.jrest;

import com.scarabsoft.jrest.annotation.Headers;
import com.scarabsoft.jrest.annotation.Interceptor;
import com.scarabsoft.jrest.annotation.Interceptors;
import com.scarabsoft.jrest.annotation.Mapping;
import com.scarabsoft.jrest.converter.ConverterFactory;
import com.scarabsoft.jrest.converter.LazyConverterFactory;
import com.scarabsoft.jrest.converter.exception.ExceptionConverter;
import com.scarabsoft.jrest.converter.exception.StringExceptionConverterFactory;
import com.scarabsoft.jrest.interceptor.RequestInterceptor;
import com.scarabsoft.jrest.interceptor.RequestInterceptorChain;
import org.apache.http.Header;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.message.BasicHeader;

import java.lang.annotation.Annotation;
import java.lang.reflect.Proxy;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public final class JRest {

    private String baseUrl;
    private ConverterFactory converterFactory;
    private ExceptionConverter.ExceptionConverterFactory exceptionFactory;
    private RequestConfig requestConfig;
    private RequestInterceptorChain requestInterceptorChain = new RequestInterceptorChain();
    private HttpClientFactory httpClientFactory;

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
            ConverterFactory factory = this.converterFactory;
            final Mapping requestMapping = clazz.getAnnotation(Mapping.class);
            if (requestMapping != null) {
                baseUrl += requestMapping.url();
                if (!requestMapping.converterFactory().equals(LazyConverterFactory.class)) {
                    factory = requestMapping.converterFactory().newInstance();
                }
                if (!requestMapping.exceptionFactory().equals(StringExceptionConverterFactory.class)) {
                    exceptionConverterFactory = requestMapping.exceptionFactory().newInstance();
                }

                if (!requestMapping.httpClientFactory().equals(DefaultHttpClientFactory.class)) {
                    httpClientFactory = requestMapping.httpClientFactory().newInstance();
                }
            }

            if (factory == null || factory.equals(LazyConverterFactory.class)) {
                throw new RuntimeException("no ConverterFactory specified");
            }

            if (exceptionConverterFactory == null) {
                exceptionConverterFactory = new StringExceptionConverterFactory();
            }

            if (httpClientFactory == null) {
                httpClientFactory = new DefaultHttpClientFactory();
            }

            final Collection<Header> headers = new LinkedList<>(); //AnnotationUtil.getHeaderEntities(clazz.getAnnotation(Headers.class));

            for (Annotation annotation : clazz.getAnnotations()) {
                if (annotation instanceof Headers == false) {
                    continue;
                }

                Headers headersAnnotation = (Headers) annotation;

                for (com.scarabsoft.jrest.annotation.Header header : headersAnnotation.value()) {

                    if (header.value().equals("")) {
                        throw new RuntimeException("header " + header.key() + " needs a value");
                    }
                    headers.add(new BasicHeader(header.key(), header.value()));

                }

            }


            List<Interceptor> interceptorList = new LinkedList<>();
            Interceptors interceptorsAnotation = clazz.getAnnotation(Interceptors.class);
            if (interceptorsAnotation != null) {
                for (Interceptor in : interceptorsAnotation.value()) {
                    interceptorList.add(in);
                }
            }

            Interceptor interceptor = clazz.getAnnotation(Interceptor.class);
            if (interceptor != null) {
                interceptorList.add(interceptor);
            }

            final RequestInterceptorChain chain = RequestInterceptorChainBuilder.create(requestInterceptorChain, interceptorList);

            return (T) Proxy.newProxyInstance(clazz.getClassLoader(),
                    new Class<?>[]{clazz},
                    new JRestInvocationHandler(baseUrl,
                            factory,
                            exceptionConverterFactory,
                            chain,
                            requestConfig,
                            headers,
                            httpClientFactory));

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

        public Builder httpClientFactory(HttpClientFactory factory) {
            buildResult.httpClientFactory = factory;
            return this;
        }

        public Builder addInterceptor(RequestInterceptor requestInterceptor) {
            buildResult.requestInterceptorChain.addInterceptor(requestInterceptor);
            return this;
        }

        public Builder converterFactory(ConverterFactory converterFactory) {
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
