package com.scarabsoft.jrest.interceptor.httpbasic;

import com.scarabsoft.jrest.interceptor.RequestInterceptor;
import com.scarabsoft.jrest.HttpApplicationCredentials;
import com.scarabsoft.jrest.RequestEntity;
import org.apache.commons.codec.binary.Base64;

public class HttpBasicRequestInterceptor implements RequestInterceptor {

    protected final HttpApplicationCredentials credentials;

    public HttpBasicRequestInterceptor(HttpApplicationCredentials credentials) {
        this.credentials = credentials;
    }

    @Override
    public void intercept(RequestEntity requestEntity) {
        final String base64 = Base64.encodeBase64String((credentials.getConsumerKey() + ":" + credentials
                .getConsumerSecret()).getBytes());
        requestEntity.addHeader("Authorization", "Basic " + base64);
    }
}
