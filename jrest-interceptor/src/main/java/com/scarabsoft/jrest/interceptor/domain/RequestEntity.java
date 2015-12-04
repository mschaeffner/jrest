package com.scarabsoft.jrest.interceptor.domain;

import com.scarabsoft.jrest.interceptor.HeaderEntity;
import com.scarabsoft.jrest.interceptor.RequestInterceptorChain;
import com.scarabsoft.jrest.interceptor.ResponseEntity;

import java.io.IOException;

public interface RequestEntity {

    Object getResult() throws IOException;

    void execute(RequestInterceptorChain chain);

    void addHeader(HeaderEntity header);

    ResponseEntity<Object> getResponse() throws IOException;

}
