package com.scarabsoft.jrest.interceptor.cookie;

import com.scarabsoft.jrest.interceptor.RequestInterceptorFactory;
import com.scarabsoft.jrest.interceptor.RequestInterceptor;

public class HttpCookieRequestInterceptorFactory implements RequestInterceptorFactory {

	protected String cookie;

	@Override
	public RequestInterceptor get() {
		return new HttpCookieRequestInterceptor(cookie);
	}

}
