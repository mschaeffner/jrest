package com.scarabsoft.jrest;

import com.scarabsoft.jrest.converter.Converter;
import com.scarabsoft.jrest.converter.exception.ExceptionConverter;
import org.apache.http.Header;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;

import java.util.Collection;

class GetRequestEntity extends AbstractRequestEntity {

    GetRequestEntity(String baseUrl,
                     Converter<?> converter,
                     ExceptionConverter<?> exceptionConverter,
                     Collection<Header> headerEntities,
                     Collection<ParamEntity> requestParameterEntities,
                     RequestConfig requestConfig,
                     HttpClient httpClient, Class<?> responseClazz,
                     Class<? extends Collection> collectionClazz) {
        super(baseUrl, converter, exceptionConverter, headerEntities, requestParameterEntities, requestConfig,
                httpClient, responseClazz, collectionClazz);
    }

    @Override
    protected HttpRequestBase buildRequest(String url) {
        final StringBuilder builder = new StringBuilder();
        builder.append("?");
        for (ParamEntity parameter : requestParameterEntities) {
            builder.append(parameter.getName());
            builder.append("=");
            builder.append(parameter.getValue().toString());
            builder.append(",");
            builder.deleteCharAt(builder.length() - 1);
            builder.append("&");
        }
        builder.deleteCharAt(builder.length() - 1);
        return new HttpGet(url + builder.toString());
    }

}
