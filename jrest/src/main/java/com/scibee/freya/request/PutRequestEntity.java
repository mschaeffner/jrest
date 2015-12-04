package com.scibee.freya.request;

import com.scibee.freya.converter.Converter;
import com.scibee.freya.converter.exception.ExceptionConverter;
import com.scibee.freya.interceptor.BodyEntity;
import com.scibee.freya.interceptor.HeaderEntity;
import com.scibee.freya.interceptor.ParamEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;

import java.io.UnsupportedEncodingException;
import java.util.Collection;

public class PutRequestEntity extends PostRequestEntity {

    public PutRequestEntity(String baseUrl, //
                            Converter<?> converter, //
                            ExceptionConverter<?> exceptionConverter, //
                            Collection<HeaderEntity> headerEntities, //
                            Collection<ParamEntity> requestParameterEntities, //
                            BodyEntity bodyEntity, //
                            RequestConfig requestConfig, //
                            HttpClient httpClient, Class<?> responseClazz, boolean isCollection, Class<? extends Collection> collectionClazz) {
        super(baseUrl, converter, exceptionConverter, headerEntities, requestParameterEntities, bodyEntity,
                requestConfig, httpClient, responseClazz, isCollection, collectionClazz);
    }

    @Override
    protected HttpRequestBase buildRequest(String url) {
        final HttpPut result = new HttpPut(url);
        try {
            result.setEntity(getHttpEntitiy());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

}
