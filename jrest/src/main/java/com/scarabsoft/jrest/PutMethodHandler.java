package com.scarabsoft.jrest;

import com.scarabsoft.jrest.annotation.Put;

import java.lang.reflect.Method;

class PutMethodHandler extends AbstractMethodHandler {

	@Override
	protected String getUrl(Method method) {
		return method.getAnnotation(Put.class).url();
	}

}
