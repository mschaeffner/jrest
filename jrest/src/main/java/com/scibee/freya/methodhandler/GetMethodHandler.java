package com.scibee.freya.methodhandler;

import java.lang.reflect.Method;

import com.scibee.freya.annotation.Body;
import com.scibee.freya.annotation.Get;
import com.scibee.freya.converter.Converter;
import com.scibee.freya.interceptor.BodyEntity;

public class GetMethodHandler extends AbstractMethodHandler implements MethodHandler {

	@Override
	protected String getUrl(Method method) {
		return method.getAnnotation(Get.class).url();
	}

	@Override
	public BodyEntity getBodyEntity(Converter.BodyConverter converter, Method method, Object[] parameters) {
		for (int i = 0; i < method.getParameters().length; i++) {
			if (method.getParameters()[i].getAnnotation(Body.class) != null) {
				throw new RuntimeException("@Body not supported for GET request");
			}
		}
		return null;
	}

}
