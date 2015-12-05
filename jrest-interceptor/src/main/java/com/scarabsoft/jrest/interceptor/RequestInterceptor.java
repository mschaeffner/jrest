package com.scarabsoft.jrest.interceptor;

import com.scarabsoft.jrest.RequestEntity;

public interface RequestInterceptor {

	void intercept(RequestEntity requestEntity);

}
