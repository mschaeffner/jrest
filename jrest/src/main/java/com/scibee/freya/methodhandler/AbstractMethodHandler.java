package com.scibee.freya.methodhandler;

import com.scibee.freya.annotation.*;
import com.scibee.freya.converter.Converter;
import com.scibee.freya.interceptor.BodyEntity;
import com.scibee.freya.interceptor.HeaderEntity;
import com.scibee.freya.interceptor.ParamEntity;
import com.scibee.freya.util.AnnotationUtil;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Collection;
import java.util.LinkedList;

public abstract class AbstractMethodHandler implements MethodHandler {

	protected abstract String getUrl(Method method);

	@Override
	public String getUrl(String baseUrl, Method method, Object[] parameters) {
		String resultUrl = baseUrl + getUrl(method);
		final Parameter[] methodParameters = method.getParameters();
		for (int i = 0; i < methodParameters.length; i++) {
			final Parameter parameter = methodParameters[i];
			final Path pathVariable = parameter.getAnnotation(Path.class);
			if (pathVariable != null) {
				if (pathVariable.name().equals("")) {
					throw new RuntimeException("name of Pathvariable is missing");
				} else {
					resultUrl = replaceUrl(resultUrl, pathVariable.name(), String.valueOf(parameters[i]));
				}
			}
		}
		return resultUrl;
	}

	protected String replaceUrl(final String url, final String name, final String value) {
		return url.replace("{" + name + "}", value);
	}

	@Override
	public Collection<ParamEntity> getParameterEntities(Method method, Object[] parameters) {
		final Collection<ParamEntity> result = new LinkedList<ParamEntity>();
		final Parameter[] methodParameters = method.getParameters();
		for (int i = 0; i < methodParameters.length; i++) {
			final Parameter parameter = methodParameters[i];
			final Param requestParam = parameter.getAnnotation(Param.class);
			if (requestParam != null) {
				if (requestParam.name().equals("")) {
					throw new RuntimeException("name of RequestParam is missing");
				} else {
					result.add(new ParamEntity(requestParam.name(), parameters[i]));
				}
			}
		}
		return result;
	}

	@Override
	public Collection<HeaderEntity> getHeaderEntities(Method method, Object[] parameters) {
		final Collection<HeaderEntity> result = AnnotationUtil.getHeaderEntities(method.getAnnotation(Headers.class));

		final Parameter[] methodParameters = method.getParameters();
		for (int i = 0; i < methodParameters.length; i++) {
			final Parameter parameter = methodParameters[i];
			final Header header = parameter.getAnnotation(Header.class);
			if (header != null) {
				final String value = String.valueOf(parameters[i]);
				if (value.equals("")) {
					throw new RuntimeException("header " + header.key() + " needs a value");
				}
				result.add(new HeaderEntity(header.key(), value));
			}
		}
		return result;
	}

	@Override
	public BodyEntity getBodyEntity(Converter.BodyConverter converter, Method method, Object[] parameters)
			throws UnsupportedEncodingException {
		for (int i = 0; i < method.getParameters().length; i++) {
			Body body = method.getParameters()[i].getAnnotation(Body.class);
			if (body != null) {
				return new BodyEntity(converter.toBody(parameters[i]), converter.getMimetype());
			}
		}
		return null;
	}
}
