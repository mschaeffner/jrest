package com.scarabsoft.jrest;

import com.scarabsoft.jrest.annotation.Header;
import org.apache.http.message.BasicHeader;

import java.util.Collection;
import java.util.LinkedList;

final class AnnotationUtil {

    private AnnotationUtil() {
        throw new RuntimeException("use static methods");
    }

    static Collection<org.apache.http.Header> getHeaderEntities(Header[] headers) {
        final Collection<org.apache.http.Header> result = new LinkedList<>();
        if (headers != null) {
            for (Header header : headers) {
                if (header.value().equals("")) {
                    throw new RuntimeException("header " + header.key() + " needs a value");
                }
                result.add(new BasicHeader(header.key(), header.value()));
            }
        }
        return result;
    }

}
