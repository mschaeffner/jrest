package com.scarabsoft.jrest.interceptor.oauth2;

import com.scarabsoft.jrest.interceptor.RequestInterceptorFactory;
import com.scarabsoft.jrest.interceptor.RequestInterceptor;

public class HttpOAuth2RequestInterceptorFactory implements RequestInterceptorFactory {

	protected String accessToken;

	@Override
	public RequestInterceptor get() {
		return new HttpOAuth2RequestInterceptor(accessToken);
	}

}
