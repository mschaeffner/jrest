package com.scibee.freya.methodhandler;

import java.lang.reflect.Method;

import com.scibee.freya.annotation.Post;

public class PostMethodHandler extends AbstractMethodHandler {

	@Override
	protected String getUrl(Method method) {
		return method.getAnnotation(Post.class).url();
	}

}
