package com.scibee.freya.interceptor.httpbasic;

import org.apache.commons.codec.binary.Base64;

import com.scibee.freya.interceptor.HeaderEntity;
import com.scibee.freya.interceptor.RequestInterceptor;
import com.scibee.freya.interceptor.domain.HttpApplicationCredentials;
import com.scibee.freya.interceptor.domain.RequestEntity;

public class HttpBasicRequestInterceptor implements RequestInterceptor {

	protected final HttpApplicationCredentials credentials;

	public HttpBasicRequestInterceptor(HttpApplicationCredentials credentials) {
		this.credentials = credentials;
	}

	@Override
	public void intercept(RequestEntity requestEntity) {
		final String base64 = Base64.encodeBase64String((credentials.getConsumerkey() + ":" + credentials
				.getConsumerSecret()).getBytes());
		requestEntity.addHeader(new HeaderEntity("Authorization", "Basic " + base64));
	}
}
