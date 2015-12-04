package com.scibee.freya.interceptor.domain;

public class HttpApplicationCredentials {

	private final String consumerkey;
	private final String consumerSecret;

	public HttpApplicationCredentials(String consumerKey, String consumerSecret) {
		this.consumerkey = consumerKey;
		this.consumerSecret = consumerSecret;
	}

	public String getConsumerkey() {
		return consumerkey;
	}

	public String getConsumerSecret() {
		return consumerSecret;
	}

}
