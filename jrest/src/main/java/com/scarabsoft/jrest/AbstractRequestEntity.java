package com.scarabsoft.jrest;

import com.scarabsoft.jrest.converter.Converter;
import com.scarabsoft.jrest.converter.exception.ExceptionConverter;
import com.scarabsoft.jrest.interceptor.ParamEntity;
import com.scarabsoft.jrest.interceptor.RequestInterceptor;
import com.scarabsoft.jrest.interceptor.RequestInterceptorChain;
import com.scarabsoft.jrest.interceptor.ResponseEntity;
import com.scarabsoft.jrest.interceptor.domain.RequestEntity;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.message.BasicHeader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

abstract class AbstractRequestEntity implements RequestEntity {

    protected final Collection<Header> headers;
    protected final Collection<ParamEntity> requestParameterEntities;
    protected final RequestConfig requestConfig;
    protected final HttpClient httpClient;
    protected final ExceptionConverter<?> exceptionConverter;
    private final Converter<?> converter;
    private final Class<?> responseClazz;
    protected String url;
    private Class<? extends Collection> collectionClazz;

    //Response
    private int httpStatusCode;
    private InputStream inputStream;
    private Header[] responseHeader;
    private Object cachedResult;
    private RuntimeException exception;

    public AbstractRequestEntity(String baseUrl, Converter<?> converter, ExceptionConverter<?> exceptionConverter,
                                 Collection<Header> headers, Collection<ParamEntity> requestParameterEntities,
                                 RequestConfig requestConfig, HttpClient httpClient, Class<?> responseClazz, Class<? extends Collection> collectionClazz) {
        this.url = baseUrl;
        this.converter = converter;
        this.exceptionConverter = exceptionConverter;
        this.headers = headers;
        this.requestParameterEntities = requestParameterEntities;
        this.requestConfig = requestConfig;
        this.httpClient = httpClient;
        this.responseClazz = responseClazz;
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
            if (collectionClazz != null) {
                cachedResult = converter.convertCollection(inputStream, collectionClazz);
            } else {
                cachedResult = converter.convert(inputStream);
            }
        }
        return cachedResult;
    }


    @Override
    public void addHeader(String name, String value) {
        headers.add(new BasicHeader(name, value));
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

        for (Header header : headers) {
            request.addHeader(header);
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
