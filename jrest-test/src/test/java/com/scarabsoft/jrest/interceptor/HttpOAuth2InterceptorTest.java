package com.scarabsoft.jrest.interceptor;

import com.scarabsoft.jrest.annotation.*;
import com.scarabsoft.jrest.converter.StringConverterFactory;
import com.scarabsoft.jrest.interceptor.oauth2.HttpOAuth2RequestInterceptor;
import com.scarabsoft.jrest.interceptor.oauth2.HttpOAuth2RequestInterceptorFactory;
import com.scarabsoft.jrest.JRestTestApplication;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.scarabsoft.jrest.JRest;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = JRestTestApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:1337")
public class HttpOAuth2InterceptorTest {

	@Mapping(value = "http://localhost:1337/v1/auth/oauth2", converterFactory = StringConverterFactory.class)
	interface Application {
		@Get
		String GET();

		@Post
		String POST();

		@Put
		String PUT();

		@Delete
		String DELETE();
	}

	@Mapping(value = "http://localhost:1337/v1/auth/oauth2", converterFactory = StringConverterFactory.class)
	@Interceptor(MyFactory.class)
	interface AnotherApplication {
		@Get
		String GET();

		@Post
		String POST();

		@Put
		String PUT();

		@Delete
		String DELETE();
	}

	public static class MyFactory extends HttpOAuth2RequestInterceptorFactory {
		public MyFactory() {
			this.accessToken = "accesstoken";
		}
	}

	@Test
	public void testHttpBasicInterceptor() {
		final JRest jrest = new JRest.Builder().addInterceptor(new HttpOAuth2RequestInterceptor("accesstoken")).build();
		Application app = jrest.create(Application.class);
		assertion(app.GET());
		assertion(app.POST());
		assertion(app.PUT());
		assertion(app.DELETE());
	}

	@Test
	public void testHttpBasicWithCustomInterceptor() {
		final JRest jrest = new JRest.Builder().build();
		AnotherApplication app = jrest.create(AnotherApplication.class);
		assertion(app.GET());
		assertion(app.POST());
		assertion(app.PUT());
		assertion(app.DELETE());
	}

	private void assertion(String cred) {
		Assert.assertThat(cred, Matchers.is("Bearer accesstoken"));
	}

}
