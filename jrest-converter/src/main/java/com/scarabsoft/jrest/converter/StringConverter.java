package com.scarabsoft.jrest.converter;

import com.scarabsoft.jrest.util.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

public class StringConverter implements Converter<String> {

	@Override
	public String convert(InputStream stream) {
		return IOUtils.streamToString(stream);
	}

	@Override
	public Collection<String> convertCollection(InputStream inputStream, Class<? extends Collection> collectionClazz) throws IOException {
		throw new RuntimeException("not supported yet.");
	}


}
