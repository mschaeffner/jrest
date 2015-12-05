package com.scarabsoft.jrest;

import com.scarabsoft.jrest.annotation.Interceptor;
import com.scarabsoft.jrest.annotation.Interceptors;
import com.scarabsoft.jrest.interceptor.RequestInterceptorChain;
import com.scarabsoft.jrest.interceptor.RequestInterceptorFactory;

final class RequestInterceptorChainBuilder {

    private RequestInterceptorChainBuilder() {
        throw new RuntimeException("use static methods");
    }

    static RequestInterceptorChain create(RequestInterceptorChain exisitingChain, Interceptors interceptors,
                                          Interceptor interceptor) throws InstantiationException, IllegalAccessException {
        final RequestInterceptorChain result = exisitingChain.deepClone();
        if (interceptors != null) {
            for (int i = 0; i < interceptors.interceptors().length; i++) {
                final RequestInterceptorFactory interceptorFactory = interceptors.interceptors()[i].factory()
                        .newInstance();
                result.addInterceptor(interceptorFactory.get());
            }
        }
        if (interceptor != null) {
            result.addInterceptor(interceptor.factory().newInstance().get());
        }
        return result;
    }

}
