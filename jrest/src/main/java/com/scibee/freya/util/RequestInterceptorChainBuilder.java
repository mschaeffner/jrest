package com.scibee.freya.util;

import com.scibee.freya.annotation.Interceptor;
import com.scibee.freya.annotation.Interceptors;
import com.scibee.freya.interceptor.RequestInterceptorChain;
import com.scibee.freya.interceptor.RequestInterceptorFactory;

public class RequestInterceptorChainBuilder {

	private RequestInterceptorChainBuilder(){
		throw new RuntimeException("use static methods");
	}

	public static RequestInterceptorChain create(RequestInterceptorChain exisitingChain, Interceptors interceptors,
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
