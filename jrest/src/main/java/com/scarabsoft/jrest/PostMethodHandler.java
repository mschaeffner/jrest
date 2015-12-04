package com.scarabsoft.jrest;

import com.scarabsoft.jrest.annotation.Post;

import java.lang.reflect.Method;

class PostMethodHandler extends AbstractMethodHandler {

	@Override
	protected String getUrl(Method method) {
		return method.getAnnotation(Post.class).url();
	}

}
