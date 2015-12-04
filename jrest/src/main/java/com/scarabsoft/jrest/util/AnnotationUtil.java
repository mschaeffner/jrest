package com.scarabsoft.jrest.util;

import com.scarabsoft.jrest.annotation.Header;
import com.scarabsoft.jrest.annotation.Headers;
import com.scarabsoft.jrest.interceptor.HeaderEntity;

import java.util.Collection;
import java.util.LinkedList;

public class AnnotationUtil {

    private AnnotationUtil(){
        throw new RuntimeException("use static methods");
    }

    public static Collection<HeaderEntity> getHeaderEntities(Headers headers) {
        final Collection<HeaderEntity> result = new LinkedList<>();
        if (headers != null) {
            if (headers.headers() != null) {
                for (int i = 0; i < headers.headers().length; i++) {
                    final Header header = headers.headers()[i];
                    if (header.value().equals("")) {
                        throw new RuntimeException("header " + header.key() + " needs a value");
                    }
                    result.add(new HeaderEntity(header.key(), header.value()));
                }
            }
        }
        return result;
    }

}
