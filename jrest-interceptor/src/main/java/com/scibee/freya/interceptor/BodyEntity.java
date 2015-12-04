package com.scibee.freya.interceptor;

public class BodyEntity {

	private final byte[] bytes;
	private final String mimeType;

	public BodyEntity(byte[] bytes, String mimeType) {
		this.bytes = bytes;
		this.mimeType = mimeType;
	}

	public byte[] getBytes() {
		return bytes;
	}

	public String getMimeType() {
		return mimeType;
	}

}
