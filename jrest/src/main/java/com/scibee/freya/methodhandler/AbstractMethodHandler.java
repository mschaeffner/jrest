package com.scibee.freya.methodhandler;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import com.scibee.freya.annotation.Body;
import com.scibee.freya.annotation.Header;
import com.scibee.freya.annotation.Headers;
import com.scibee.freya.annotation.Param;
import com.scibee.freya.annotation.Path;
import com.scibee.freya.converter.Converter;
import com.scibee.freya.interceptor.BodyEntity;
import com.scibee.freya.interceptor.HeaderEntity;
import com.scibee.freya.interceptor.ParamEntity;

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

	private String replaceUrl(final String url, final String name, final String value) {
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
		final Collection<HeaderEntity> result = new ArrayList<HeaderEntity>();
		final Headers headers = method.getAnnotation(Headers.class);
		if (headers != null) {
			if (headers.headers() != null) {
				for (int i = 0; i < headers.headers().length; i++) {
					final Header header = headers.headers()[i];
					if (header.value().equals("")) {
						throw new RuntimeException("header " + header.key() + " needs a value");
					}
					result.add(new HeaderEntity(header.key(), header.value()));
				}
			}
		}

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
