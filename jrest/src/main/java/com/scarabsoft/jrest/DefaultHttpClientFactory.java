package com.scarabsoft.jrest;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

public final class DefaultHttpClientFactory implements HttpClientFactory {
    @Override
    public HttpClient get() {
        return HttpClientBuilder.create().build();
    }
}
