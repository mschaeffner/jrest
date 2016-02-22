package com.scarabsoft.jrest.interceptor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class RequestInterceptorChain implements Iterable<RequestInterceptor> {

    private final List<RequestInterceptor> interceptors = new ArrayList<>();

    public void addInterceptor(RequestInterceptor interceptor) {
        interceptors.add(interceptor);
    }

    public int size() {
        return interceptors.size();
    }

    public void clear() {
        interceptors.clear();
    }

    @Override
    public Iterator<RequestInterceptor> iterator() {
        return interceptors.iterator();
    }

    public RequestInterceptorChain deepClone() {
        final RequestInterceptorChain result = new RequestInterceptorChain();
        for(RequestInterceptor interceptor: interceptors){
            result.addInterceptor(interceptor);
        }
        return result;
    }

}
