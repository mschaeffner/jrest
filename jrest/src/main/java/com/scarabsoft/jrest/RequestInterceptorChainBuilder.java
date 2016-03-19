package com.scarabsoft.jrest;

import com.scarabsoft.jrest.annotation.Interceptor;
import com.scarabsoft.jrest.interceptor.RequestInterceptorChain;

import java.util.List;

final class RequestInterceptorChainBuilder {

    private RequestInterceptorChainBuilder() {
        throw new RuntimeException("use static methods");
    }

    static RequestInterceptorChain create(RequestInterceptorChain exisitingChain, List<Interceptor> interceptors) throws InstantiationException, IllegalAccessException {
        final RequestInterceptorChain result = exisitingChain.deepClone();
        if (interceptors != null) {
            for (final Interceptor interceptor : interceptors) {
                result.addInterceptor(interceptor.value().newInstance().get());
            }
        }
        return result;
    }

}
