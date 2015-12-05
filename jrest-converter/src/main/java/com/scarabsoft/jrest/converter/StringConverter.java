package com.scarabsoft.jrest.converter;

import com.scarabsoft.jrest.util.IOUtils;

import java.io.InputStream;

public class StringConverter implements Converter<String> {

	@Override
	public String convert(InputStream stream) {
		return IOUtils.streamToString(stream);
	}


}
