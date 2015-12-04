package com.scarabsoft.jrest;

import com.scarabsoft.jrest.converter.Converter;
import com.scarabsoft.jrest.converter.exception.ExceptionConverter;
import com.scarabsoft.jrest.interceptor.ParamEntity;
import org.apache.http.Header;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpRequestBase;

import java.util.Collection;

public class DeleteRequestEntity extends AbstractRequestEntity {
    public DeleteRequestEntity(String baseUrl,
                               Converter<?> converter,
                               ExceptionConverter<?> exceptionConverter,
                               Collection<Header> headerEntities,
                               Collection<ParamEntity> requestParameterEntities,
                               RequestConfig requestConfig,
                               HttpClient httpClient, Class<?> responseClazz,
                               Class<? extends Collection> collectionClazz
    ) {
        super(baseUrl, converter, exceptionConverter, headerEntities, requestParameterEntities, requestConfig,
                httpClient, responseClazz, collectionClazz);
    }

    @Override
    protected HttpRequestBase buildRequest(String url) {
        return new HttpDelete(url);
    }

}
