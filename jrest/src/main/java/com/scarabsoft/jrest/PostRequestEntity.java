package com.scarabsoft.jrest;

import com.scarabsoft.jrest.converter.Converter;
import com.scarabsoft.jrest.converter.exception.ExceptionConverter;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Collection;

class PostRequestEntity extends AbstractRequestEntity {

    protected final BodyEntity bodyEntity;

    PostRequestEntity(String baseUrl,
                      Converter<?> converter,
                      ExceptionConverter<?> exceptionConverter,
                      Collection<Header> header,
                      Collection<ParamEntity> requestParameterEntities,
                      BodyEntity bodyEntity,
                      RequestConfig requestConfig,
                      HttpClient httpClient,
                      Class<?> responseClazz,
                      Class<? extends Collection> collectionClazz) {
        super(baseUrl, converter, exceptionConverter, header, requestParameterEntities, requestConfig,
                httpClient, responseClazz, collectionClazz);
        this.bodyEntity = bodyEntity;
    }

    protected HttpEntity getHttpEntitiy() throws UnsupportedEncodingException {
        if (bodyEntity != null) {
            return new ByteArrayEntity(bodyEntity.getBytes(), ContentType.create(bodyEntity.getMimeType()));
        }
        final MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        for (ParamEntity requestParameter : requestParameterEntities) {
            if (requestParameter.getValue() instanceof File) {
                builder.addBinaryBody(requestParameter.getName(), (File) requestParameter.getValue());
            } else if (requestParameter.getValue() instanceof InputStream) {
                builder.addBinaryBody(requestParameter.getName(), (InputStream) requestParameter.getValue(), ContentType.APPLICATION_OCTET_STREAM, requestParameter.getFilename());
            } else if (requestParameter.getValue() instanceof Byte[] || requestParameter.getValue() instanceof byte[]) {
                builder.addBinaryBody(requestParameter.getName(), (byte[]) requestParameter.getValue(), ContentType.APPLICATION_OCTET_STREAM, requestParameter.getFilename());
            } else {
                builder.addTextBody(requestParameter.getName(), String.valueOf(requestParameter.getValue()));
            }
        }
        return builder.build();
    }
    
    @Override
    protected HttpRequestBase buildRequest(String url) {
        final HttpPost result = new HttpPost(url);
        try {
            result.setEntity(getHttpEntitiy());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

}
