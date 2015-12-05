package com.scarabsoft.jrest;

import java.util.Map;
import java.util.Optional;

public final class ResponseEntity<T> {

    private final int statusCode;
    private final Map<String, String> headerMap;
    private final T object;

    public ResponseEntity(int statusCode, T object, Map<String, String> headerMap) {
        this.statusCode = statusCode;
        this.object = object;
        this.headerMap = headerMap;
    }

    public T getObject() {
        return object;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public Optional<String> getHeader(String key) {
        return Optional.ofNullable(headerMap.get(key));
    }

}
