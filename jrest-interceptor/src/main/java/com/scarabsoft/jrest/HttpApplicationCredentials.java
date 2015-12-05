package com.scarabsoft.jrest;

public class HttpApplicationCredentials {

	private final String consumerKey;
	private final String consumerSecret;

	public HttpApplicationCredentials(String consumerKey, String consumerSecret) {
		this.consumerKey = consumerKey;
		this.consumerSecret = consumerSecret;
	}

	public String getConsumerKey() {
		return consumerKey;
	}

	public String getConsumerSecret() {
		return consumerSecret;
	}

}
