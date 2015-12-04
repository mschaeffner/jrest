package com.scibee.freya.interceptor.oauth2;

import com.scibee.freya.interceptor.RequestInterceptor;
import com.scibee.freya.interceptor.RequestInterceptorFactory;

public class HttpOAuth2RequestInterceptorFactory implements RequestInterceptorFactory {

	protected String accessToken;

	@Override
	public RequestInterceptor get() {
		return new HttpOAuth2RequestInterceptor(accessToken);
	}

}
