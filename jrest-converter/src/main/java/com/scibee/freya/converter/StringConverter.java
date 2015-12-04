package com.scibee.freya.converter;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import com.scibee.freya.util.IOUtil;

public class StringConverter implements Converter<String> {

	@Override
	public String convert(InputStream stream) {
		return IOUtil.streamToString(stream);
	}

	@Override
	public BodyConverter getBodyConverter() {
		return new BodyConverter() {
			@Override
			public byte[] toBody(Object obj) throws UnsupportedEncodingException {
				return obj.toString().getBytes("UTF-8");
			}

			@Override
			public String getMimetype() {
				return "text/plain";
			}
		};
	}

}
