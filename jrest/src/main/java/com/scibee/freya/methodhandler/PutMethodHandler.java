package com.scibee.freya.methodhandler;

import java.lang.reflect.Method;

import com.scibee.freya.annotation.Put;

public class PutMethodHandler extends AbstractMethodHandler {

	@Override
	protected String getUrl(Method method) {
		return method.getAnnotation(Put.class).url();
	}

}
