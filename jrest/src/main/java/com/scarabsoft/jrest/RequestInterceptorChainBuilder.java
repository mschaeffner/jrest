package com.scarabsoft.jrest;

import com.scarabsoft.jrest.annotation.Interceptor;
import com.scarabsoft.jrest.interceptor.RequestInterceptorChain;

final class RequestInterceptorChainBuilder {

    private RequestInterceptorChainBuilder() {
        throw new RuntimeException("use static methods");
    }

    static RequestInterceptorChain create(RequestInterceptorChain exisitingChain, Interceptor[] interceptors) throws InstantiationException, IllegalAccessException {
        final RequestInterceptorChain result = exisitingChain.deepClone();
        if (interceptors != null) {
            for (final Interceptor interceptor : interceptors) {
                result.addInterceptor(interceptor.factory().newInstance().get());
            }
        }
        return result;
    }

}
