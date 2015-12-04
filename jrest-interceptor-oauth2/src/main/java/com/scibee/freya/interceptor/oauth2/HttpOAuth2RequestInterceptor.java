package com.scibee.freya.interceptor.oauth2;

import com.scibee.freya.interceptor.HeaderEntity;
import com.scibee.freya.interceptor.RequestInterceptor;
import com.scibee.freya.interceptor.domain.RequestEntity;

public class HttpOAuth2RequestInterceptor implements RequestInterceptor {

	protected final String accessToken;

	public HttpOAuth2RequestInterceptor(String accessToken) {
		this.accessToken = accessToken;
	}

	@Override
	public void intercept(RequestEntity requestEntity) {
		requestEntity.addHeader(new HeaderEntity("Authorization", "Bearer " + accessToken));
	}

}
