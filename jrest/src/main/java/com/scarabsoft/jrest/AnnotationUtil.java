package com.scarabsoft.jrest;

import com.scarabsoft.jrest.annotation.Header;
import com.scarabsoft.jrest.annotation.Headers;
import org.apache.http.message.BasicHeader;

import java.util.Collection;
import java.util.LinkedList;

final class AnnotationUtil {

    private AnnotationUtil() {
        throw new RuntimeException("use static methods");
    }

    static Collection<org.apache.http.Header> getHeaderEntities(Headers headers) {
        final Collection<org.apache.http.Header> result = new LinkedList<>();
        if (headers != null) {
            if (headers.headers() != null) {
                for (int i = 0; i < headers.headers().length; i++) {
                    final Header header = headers.headers()[i];
                    if (header.value().equals("")) {
                        throw new RuntimeException("header " + header.key() + " needs a value");
                    }
                    result.add(new BasicHeader(header.key(), header.value()));
                }
            }
        }
        return result;
    }

}
