package com.scarabsoft.jrest;

import com.scarabsoft.jrest.converter.Converter;
import com.scarabsoft.jrest.converter.exception.ExceptionConverter;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

class PostRequestEntity extends AbstractRequestEntity {

    protected final BodyEntity bodyEntity;
    protected final boolean isMultipart;

    PostRequestEntity(String baseUrl,
                      Converter<?> converter,
                      ExceptionConverter<?> exceptionConverter,
                      Collection<Header> header,
                      boolean isMultipart,
                      Collection<ParamEntity> requestParameterEntities,
                      BodyEntity bodyEntity,
                      RequestConfig requestConfig,
                      HttpClient httpClient,
                      Class<?> responseClazz,
                      Class<? extends Collection> collectionClazz) {
        super(baseUrl, converter, exceptionConverter, header, requestParameterEntities, requestConfig,
                httpClient, responseClazz, collectionClazz);
        this.bodyEntity = bodyEntity;
        this.isMultipart = isMultipart;
    }

    protected HttpEntity bodyEntity(){
        return new ByteArrayEntity(bodyEntity.getBytes(), ContentType.create(bodyEntity.getMimeType()));
    }

    protected HttpEntity multipPartEntity(){
        final MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        for (ParamEntity requestParameter : requestParameterEntities) {
            if (requestParameter.getValue() instanceof File) {
                builder.addBinaryBody(requestParameter.getName(), (File) requestParameter.getValue(),ContentType.create(requestParameter.getContentType()), requestParameter.getFilename());
            } else if (requestParameter.getValue() instanceof InputStream) {
                builder.addBinaryBody(requestParameter.getName(), (InputStream) requestParameter.getValue(),ContentType.create(requestParameter.getContentType()),requestParameter.getFilename());
            } else if (requestParameter.getValue() instanceof Byte[] || requestParameter.getValue() instanceof byte[]) {
                builder.addBinaryBody(requestParameter.getName(), (byte[]) requestParameter.getValue(), ContentType.create(requestParameter.getContentType()), requestParameter.getFilename());
            } else {
                builder.addTextBody(requestParameter.getName(), String.valueOf(requestParameter.getValue()),ContentType.create(HTTP.PLAIN_TEXT_TYPE, HTTP.UTF_8));
            }
        }
        return builder.build();
    }

    protected  HttpEntity basicEntity() throws UnsupportedEncodingException {
        final List<NameValuePair> nameValuePairs = new LinkedList<>();
        for(ParamEntity requestParameter: requestParameterEntities) {
            nameValuePairs.add(new BasicNameValuePair(requestParameter.getName(), String.valueOf(requestParameter.getValue())));
        }
        return new UrlEncodedFormEntity(nameValuePairs,"UTF-8");
    }

    protected HttpEntity getHttpEntitiy() throws UnsupportedEncodingException {
        if(bodyEntity != null){
            return bodyEntity();
        }

        if(isMultipart){
            return multipPartEntity();
        }

        return basicEntity();
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
