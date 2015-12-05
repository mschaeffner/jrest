package com.scarabsoft.jrest.interceptor.httpbasic;

import com.scarabsoft.jrest.interceptor.RequestInterceptorFactory;
import com.scarabsoft.jrest.HttpApplicationCredentials;
import com.scarabsoft.jrest.interceptor.RequestInterceptor;

public abstract class HttpBasicRequestInterceptorFactory implements RequestInterceptorFactory {

	protected HttpApplicationCredentials credentials;

	@Override
	public RequestInterceptor get() {
		return new HttpBasicRequestInterceptor(credentials);
	}

}
