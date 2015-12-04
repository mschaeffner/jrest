package com.scarabsoft.jrest.interceptor.cookie;

import com.scarabsoft.jrest.interceptor.RequestInterceptor;
import com.scarabsoft.jrest.interceptor.domain.RequestEntity;

public class HttpCookieRequestInterceptor implements RequestInterceptor {

    protected final String cookie;

    public HttpCookieRequestInterceptor(String cookie) {
        this.cookie = cookie;
    }

    @Override
    public void intercept(RequestEntity requestEntity) {
        requestEntity.addHeader("Cookie", cookie);
    }

}
