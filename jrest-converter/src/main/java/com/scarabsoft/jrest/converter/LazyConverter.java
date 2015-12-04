package com.scarabsoft.jrest.converter;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;

public class LazyConverter implements Converter<Void> {

	@Override
	public Void convert(InputStream stream) {
		return null;
	}

	@Override
	public BodyConverter getBodyConverter() {
		return new BodyConverter() {

			@Override
			public byte[] toBody(Object obj) throws UnsupportedEncodingException {
				return null;
			}

			@Override
			public String getMimetype() {
				return null;
			}
		};
	}

}
