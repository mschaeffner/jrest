package com.scibee.freya.interceptor.cookie;

import com.scibee.freya.interceptor.HeaderEntity;
import com.scibee.freya.interceptor.RequestInterceptor;
import com.scibee.freya.interceptor.domain.RequestEntity;

public class HttpCookieRequestInterceptor implements RequestInterceptor {

	protected final String cookie;

	public HttpCookieRequestInterceptor(String cookie) {
		this.cookie = cookie;
	}

	@Override
	public void intercept(RequestEntity requestEntity) {
		requestEntity.addHeader(new HeaderEntity("Cookie", cookie));
	}

}
