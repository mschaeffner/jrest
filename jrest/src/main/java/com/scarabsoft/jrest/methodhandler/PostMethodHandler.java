package com.scarabsoft.jrest.methodhandler;

import java.lang.reflect.Method;

import com.scarabsoft.jrest.annotation.Post;

public class PostMethodHandler extends AbstractMethodHandler {

	@Override
	protected String getUrl(Method method) {
		return method.getAnnotation(Post.class).url();
	}

}
