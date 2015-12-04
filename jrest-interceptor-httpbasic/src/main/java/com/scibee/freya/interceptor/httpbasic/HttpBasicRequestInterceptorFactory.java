package com.scibee.freya.interceptor.httpbasic;

import com.scibee.freya.interceptor.RequestInterceptor;
import com.scibee.freya.interceptor.RequestInterceptorFactory;
import com.scibee.freya.interceptor.domain.HttpApplicationCredentials;

public abstract class HttpBasicRequestInterceptorFactory implements RequestInterceptorFactory {

	protected HttpApplicationCredentials credentials;

	@Override
	public RequestInterceptor get() {
		return new HttpBasicRequestInterceptor(credentials);
	}

}
