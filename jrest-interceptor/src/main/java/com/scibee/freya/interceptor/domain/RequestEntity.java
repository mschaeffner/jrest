package com.scibee.freya.interceptor.domain;

import com.scibee.freya.interceptor.HeaderEntity;
import com.scibee.freya.interceptor.RequestInterceptorChain;
import com.scibee.freya.interceptor.ResponseEntity;

import java.io.IOException;

public interface RequestEntity {

    Object getResult() throws IOException;

    void execute(RequestInterceptorChain chain);

    void addHeader(HeaderEntity header);

    ResponseEntity<Object> getResponse() throws IOException;

}
