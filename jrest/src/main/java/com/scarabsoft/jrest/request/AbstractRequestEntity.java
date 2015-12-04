package com.scarabsoft.jrest.request;

import com.scarabsoft.jrest.converter.Converter;
import com.scarabsoft.jrest.interceptor.*;
import com.scarabsoft.jrest.converter.exception.ExceptionConverter;
import com.scarabsoft.jrest.interceptor.domain.RequestEntity;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpRequestBase;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

public abstract class AbstractRequestEntity implements RequestEntity {

    protected final Converter<?> converter;
    protected final Collection<HeaderEntity> headerEntities;
    protected final Collection<ParamEntity> requestParameterEntities;
    protected final RequestConfig requestConfig;
    protected final HttpClient httpClient;
    protected final ExceptionConverter<?> exceptionConverter;
    private final Class<?> responseClazz;
    protected String url;
    protected InputStream inputStream;
    protected int httpStatusCode;
    protected Object cachedResult;
    private RuntimeException exception;
    private Header[] responseHeader;

    private boolean isCollection;
    private Class<? extends Collection> collectionClazz;

    public AbstractRequestEntity(String baseUrl, Converter<?> converter, ExceptionConverter<?> exceptionConverter,
                                 Collection<HeaderEntity> headerEntities, Collection<ParamEntity> requestParameterEntities,
                                 RequestConfig requestConfig, HttpClient httpClient, Class<?> responseClazz, boolean isCollection,Class<? extends Collection> collectionClazz) {
        this.url = baseUrl;
        this.converter = converter;
        this.exceptionConverter = exceptionConverter;
        this.headerEntities = headerEntities;
        this.requestParameterEntities = requestParameterEntities;
        this.requestConfig = requestConfig;
        this.httpClient = httpClient;
        this.responseClazz = responseClazz;
        this.isCollection = isCollection;
        this.collectionClazz = collectionClazz;
    }

    protected abstract HttpRequestBase buildRequest(String url);

    protected String getRequestUrl() {
        return url;
    }

    @Override
    public Object getResult() throws IOException {
        if (exception != null) {
            throw exception;
        }

        if (cachedResult == null) {
            if (isCollection) {
                cachedResult = converter.convertCollection(inputStream, collectionClazz);
            } else {
                cachedResult = converter.convert(inputStream);
            }
        }
        return cachedResult;
    }

    @Override
    public void addHeader(HeaderEntity header) {
        headerEntities.add(header);
    }

    public RuntimeException getException() {
        return exception;
    }

    @Override
    public void execute(RequestInterceptorChain chain) {
        final HttpRequestBase request = buildRequest(getRequestUrl());
        // intercept
        for (RequestInterceptor interceptor : chain) {
            interceptor.intercept(this);
        }

        for (HeaderEntity headerEnitity : headerEntities) {
            request.addHeader(headerEnitity.getKey(), headerEnitity.getValue());
        }

        if (requestConfig != null) {
            request.setConfig(requestConfig);
        }

        try {
            final HttpResponse result = httpClient.execute(request);
            inputStream = result.getEntity().getContent();
            httpStatusCode = result.getStatusLine().getStatusCode();
            if (httpStatusCode >= 400) {
                exception = exceptionConverter.convert(inputStream);
                throw exception;
            }
            responseHeader = result.getAllHeaders();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Class<?> getResponseClazz() {
        return responseClazz;
    }

    @Override
    public ResponseEntity<Object> getResponse() throws IOException {
        final ResponseEntity<Object> result = new ResponseEntity<>(httpStatusCode, getResult());
        if (responseHeader != null) {
            for (Header header : responseHeader) {
                result.addHeader(header.getName(), header.getValue());
            }
        }
        return result;
    }
}
