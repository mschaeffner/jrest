package com.scarabsoft.jrest.test.interceptor;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import com.scarabsoft.jrest.interceptor.RequestInterceptor;
import com.scarabsoft.jrest.interceptor.RequestInterceptorChain;
import com.scarabsoft.jrest.interceptor.domain.RequestEntity;

public class RequestInterceptorChainTest {

	class DummyInterceptor implements RequestInterceptor {
		@Override
		public void intercept(RequestEntity requestEntity) {

		}
	}

	@Test
	public void test() {
		final RequestInterceptorChain chain1 = new RequestInterceptorChain();
		for (int i = 0; i < 3; i++) {
			chain1.addInterceptor(new DummyInterceptor());
		}
		final RequestInterceptorChain chain2 = chain1.deepClone();
		Assert.assertThat(chain2.size(), Matchers.is(3));
		chain1.clear();
		Assert.assertThat(chain2.size(), Matchers.is(3));
	}

}
