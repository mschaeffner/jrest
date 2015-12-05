package com.scarabsoft.jrest.converter;

import com.scarabsoft.jrest.util.IOUtil;

import java.io.InputStream;

public class StringConverter implements Converter<String> {

	@Override
	public String convert(InputStream stream) {
		return IOUtil.streamToString(stream);
	}


}
