package com.scarabsoft.jrest.methodhandler;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.Collection;

import com.scarabsoft.jrest.converter.Converter;
import com.scarabsoft.jrest.interceptor.BodyEntity;
import com.scarabsoft.jrest.interceptor.HeaderEntity;
import com.scarabsoft.jrest.interceptor.ParamEntity;

public interface MethodHandler {

	String getUrl(String baseUrl, Method method, Object[] parameter);

	Collection<ParamEntity> getParameterEntities(Method method, Object[] parameter);

	Collection<HeaderEntity> getHeaderEntities(Method method, Object[] parameters);

	BodyEntity getBodyEntity(Converter.BodyConverter converter, Method method, Object[] parameters)
			throws UnsupportedEncodingException;

}
