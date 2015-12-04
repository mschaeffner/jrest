package com.scarabsoft.jrest.interceptor;

import com.scarabsoft.jrest.interceptor.domain.RequestEntity;

public interface RequestInterceptor {

	void intercept(RequestEntity requestEntity);

}
