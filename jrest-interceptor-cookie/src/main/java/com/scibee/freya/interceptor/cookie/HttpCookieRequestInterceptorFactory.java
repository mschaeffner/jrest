package com.scibee.freya.interceptor.cookie;

import com.scibee.freya.interceptor.RequestInterceptor;
import com.scibee.freya.interceptor.RequestInterceptorFactory;

public class HttpCookieRequestInterceptorFactory implements RequestInterceptorFactory {

	protected String cookie;

	@Override
	public RequestInterceptor get() {
		return new HttpCookieRequestInterceptor(cookie);
	}

}
