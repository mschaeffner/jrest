package com.scibee.freya.interceptor;

import com.scibee.freya.interceptor.domain.RequestEntity;

public interface RequestInterceptor {

	void intercept(RequestEntity requestEntity);

}
