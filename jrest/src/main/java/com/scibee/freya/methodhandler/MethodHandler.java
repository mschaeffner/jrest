package com.scibee.freya.methodhandler;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.Collection;

import com.scibee.freya.converter.Converter;
import com.scibee.freya.interceptor.BodyEntity;
import com.scibee.freya.interceptor.HeaderEntity;
import com.scibee.freya.interceptor.ParamEntity;

public interface MethodHandler {

	String getUrl(String baseUrl, Method method, Object[] parameter);

	Collection<ParamEntity> getParameterEntities(Method method, Object[] parameter);

	Collection<HeaderEntity> getHeaderEntities(Method method, Object[] parameters);

	BodyEntity getBodyEntity(Converter.BodyConverter converter, Method method, Object[] parameters)
			throws UnsupportedEncodingException;

}
