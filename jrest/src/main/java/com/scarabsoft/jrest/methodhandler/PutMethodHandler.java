package com.scarabsoft.jrest.methodhandler;

import java.lang.reflect.Method;

import com.scarabsoft.jrest.annotation.Put;

public class PutMethodHandler extends AbstractMethodHandler {

	@Override
	protected String getUrl(Method method) {
		return method.getAnnotation(Put.class).url();
	}

}
