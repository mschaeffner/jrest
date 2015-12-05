package com.scarabsoft.jrest;

import java.util.Map;
import java.util.Optional;

class ResponseEntity<T> {

    private final int statusCode;
    private final Map<String, String> headerMap;
    private final T object;

    ResponseEntity(int statusCode, T object, Map<String, String> headerMap) {
        this.statusCode = statusCode;
        this.object = object;
        this.headerMap = headerMap;
    }

    T getObject() {
        return object;
    }

    int getStatusCode() {
        return statusCode;
    }

    Optional<String> getHeader(String key) {
        return Optional.ofNullable(headerMap.get(key));
    }

}
