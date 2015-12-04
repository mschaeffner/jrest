package com.scibee.freya.request;

import com.scibee.freya.converter.Converter;
import com.scibee.freya.converter.exception.ExceptionConverter;
import com.scibee.freya.interceptor.HeaderEntity;
import com.scibee.freya.interceptor.ParamEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;

import java.util.Collection;

public class GetRequestEntity extends AbstractRequestEntity {

	public GetRequestEntity(String baseUrl, //
			Converter<?> converter, //
			ExceptionConverter<?> exceptionConverter, //
			Collection<HeaderEntity> headerEntities, //
			Collection<ParamEntity> requestParameterEntities, //
			RequestConfig requestConfig, //
			HttpClient httpClient, Class<?> responseClazz, //
			boolean isCollection,
							Class<? extends Collection> collectionClazz
							) {
		super(baseUrl, converter, exceptionConverter, headerEntities, requestParameterEntities, requestConfig,
				httpClient, responseClazz, isCollection,collectionClazz);
	}

	@Override
	protected HttpRequestBase buildRequest(String url) {
		final StringBuilder builder = new StringBuilder();
		builder.append("?");
		for (ParamEntity parameter : requestParameterEntities) {
			builder.append(parameter.getName());
			builder.append("=");
			builder.append(parameter.getValue().toString());
			builder.append(",");
			builder.deleteCharAt(builder.length() - 1);
			builder.append("&");
		}
		builder.deleteCharAt(builder.length() - 1);
		return new HttpGet(url + builder.toString());
	}

}
