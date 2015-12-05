package com.scarabsoft.jrest;

import com.scarabsoft.jrest.interceptor.RequestInterceptorChain;

import java.io.IOException;

public interface RequestEntity {

    Object getResult() throws IOException;

    void execute(RequestInterceptorChain chain);

    ResponseEntity<Object> getResponse() throws IOException;

    void addHeader(String name, String value);

}
