package com.scarabsoft.jrest;

import com.scarabsoft.jrest.converter.Converter;
import com.scarabsoft.jrest.converter.exception.ExceptionConverter;
import org.apache.http.Header;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;

import java.io.UnsupportedEncodingException;
import java.util.Collection;

class PutRequestEntity extends PostRequestEntity {

    PutRequestEntity(String baseUrl,
                     Converter<?> converter,
                     ExceptionConverter<?> exceptionConverter,
                     Collection<Header> headers,
                     Collection<ParamEntity> requestParameterEntities,
                     BodyEntity bodyEntity,
                     RequestConfig requestConfig,
                     HttpClient httpClient,
                     Class<?> responseClazz,
                     Class<? extends Collection> collectionClazz) {
        super(baseUrl, converter, exceptionConverter, headers,true, requestParameterEntities, bodyEntity,
                requestConfig, httpClient, responseClazz, collectionClazz);
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
