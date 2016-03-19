package com.scarabsoft.jrest;

import com.scarabsoft.jrest.annotation.Put;

import java.lang.reflect.Method;

final class PutMethodHandler extends AbstractMethodHandler {

	@Override
	protected String getUrl(Method method) {
		return method.getAnnotation(Put.class).value();
	}

	public boolean isMultipart(Method method) { return method.getAnnotation(Put.class).multipart(); }

}
