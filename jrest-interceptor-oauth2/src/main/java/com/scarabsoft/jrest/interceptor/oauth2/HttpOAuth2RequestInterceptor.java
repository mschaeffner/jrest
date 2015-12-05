package com.scarabsoft.jrest.interceptor.oauth2;

import com.scarabsoft.jrest.interceptor.RequestInterceptor;
import com.scarabsoft.jrest.RequestEntity;

public class HttpOAuth2RequestInterceptor implements RequestInterceptor {

    protected final String accessToken;

    public HttpOAuth2RequestInterceptor(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public void intercept(RequestEntity requestEntity) {
        requestEntity.addHeader("Authorization", "Bearer " + accessToken);
    }

}
