package com.scarabsoft.jrest.interceptor.httpbasic;

import com.scarabsoft.jrest.interceptor.domain.HttpApplicationCredentials;
import org.apache.commons.codec.binary.Base64;

import com.scarabsoft.jrest.interceptor.HeaderEntity;
import com.scarabsoft.jrest.interceptor.RequestInterceptor;
import com.scarabsoft.jrest.interceptor.domain.RequestEntity;

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
