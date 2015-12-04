package com.scibee.freya;

import com.scibee.freya.annotation.Get;
import com.scibee.freya.annotation.Post;
import com.scibee.freya.annotation.Put;
import com.scibee.freya.converter.Converter;
import com.scibee.freya.converter.exception.ExceptionConverter;
import com.scibee.freya.interceptor.BodyEntity;
import com.scibee.freya.interceptor.HeaderEntity;
import com.scibee.freya.interceptor.ParamEntity;
import com.scibee.freya.methodhandler.GetMethodHandler;
import com.scibee.freya.methodhandler.MethodHandler;
import com.scibee.freya.methodhandler.PostMethodHandler;
import com.scibee.freya.methodhandler.PutMethodHandler;
import com.scibee.freya.request.AbstractRequestEntity;
import com.scibee.freya.request.GetRequestEntity;
import com.scibee.freya.request.PostRequestEntity;
import com.scibee.freya.request.PutRequestEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.Collection;

public class RequestBuilder {

	private final String baseUrl;
	private final Converter<?> converter;
	private final ExceptionConverter<?> exceptionConverter;
	private final RequestConfig requestConfig;
	private final Collection<HeaderEntity> headerEntities;

	private boolean isCollection;
	private Class<? extends Collection> collectionClazz;

	public RequestBuilder(String baseUrl, //
			Converter<?> converter, //
			ExceptionConverter<?> exceptionConverter, //
			RequestConfig requestConfig, //
			Collection<HeaderEntity> headerEntities, boolean isCollection, Class<? extends Collection> collectionClazz) {
		this.baseUrl = baseUrl;
		this.converter = converter;
		this.exceptionConverter = exceptionConverter;
		this.requestConfig = requestConfig;
		this.headerEntities = headerEntities;
		this.isCollection = isCollection;
		this.collectionClazz = collectionClazz;
	}

	public AbstractRequestEntity build(Method method, Object[] parameters, HttpClient httpClient) {
		final MethodHandler methodHandler = resolveMethodHandler(method);
		final String url = methodHandler.getUrl(baseUrl, method, parameters);
		final Collection<ParamEntity> requestParameterEntities = methodHandler.getParameterEntities(method, parameters);
		final Collection<HeaderEntity> headerEntities = methodHandler.getHeaderEntities(method, parameters);
		headerEntities.addAll(this.headerEntities);

		BodyEntity body = null;
		try {
			body = methodHandler.getBodyEntity(converter.getBodyConverter(), method, parameters);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("could not convert object to byte[]");
		}

		if (body != null && requestParameterEntities.size() > 0) {
			throw new RuntimeException("could not use request parameter together with request body");
		}

		AbstractRequestEntity result = null;
		if (method.getAnnotation(Get.class) != null) {

			result = new GetRequestEntity(//
					url, //
					converter, //
					exceptionConverter, //
					headerEntities, //
					requestParameterEntities, //
					requestConfig, //
					httpClient, method.getReturnType(), isCollection,collectionClazz);

		} else if (method.getAnnotation(Post.class) != null) {

			result = new PostRequestEntity(//
					url, //
					converter, //
					exceptionConverter, //
					headerEntities, //
					requestParameterEntities, //
					body, //
					requestConfig, //
					httpClient, method.getReturnType(), isCollection, collectionClazz);

		} else if (method.getAnnotation(Put.class) != null) {

			result = new PutRequestEntity(//
					url, //
					converter, //
					exceptionConverter, //
					headerEntities, //
					requestParameterEntities, //
					body, //
					requestConfig, //
					httpClient, method.getReturnType(), isCollection,collectionClazz);

		} else {
			throw new RuntimeException("could not build request");
		}
		return result;
	}

	private MethodHandler resolveMethodHandler(Method method) {
		if (method.getAnnotation(Get.class) != null) {
			return new GetMethodHandler();
		}
		if (method.getAnnotation(Post.class) != null) {
			return new PostMethodHandler();
		}
		if (method.getAnnotation(Put.class) != null) {
			return new PutMethodHandler();
		}
		throw new RuntimeException("not method handler found");
	}

}
