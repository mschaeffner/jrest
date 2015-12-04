package com.scarabsoft.jrest.test.interceptor;

import com.scarabsoft.jrest.annotation.Get;
import com.scarabsoft.jrest.annotation.Put;
import com.scarabsoft.jrest.converter.StringConverterFactory;
import com.scarabsoft.jrest.interceptor.oauth2.HttpOAuth2RequestInterceptor;
import com.scarabsoft.jrest.interceptor.oauth2.HttpOAuth2RequestInterceptorFactory;
import com.scarabsoft.jrest.test.JRestTestApplication;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.scarabsoft.jrest.JRest;
import com.scarabsoft.jrest.annotation.Interceptor;
import com.scarabsoft.jrest.annotation.Post;
import com.scarabsoft.jrest.annotation.Mapping;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = JRestTestApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:1337")
public class HttpOAuth2InterceptorTest {

	@Mapping(url = "http://localhost:1337/v1/auth/oauth2", converterFactory = StringConverterFactory.class)
	interface Application {
		@Get
		String GET();

		@Post
		String POST();

		@Put
		String PUT();
	}

	@Mapping(url = "http://localhost:1337/v1/auth/oauth2", converterFactory = StringConverterFactory.class)
	@Interceptor(factory = MyFactory.class)
	interface AnotherApplication {
		@Get
		String GET();

		@Post
		String POST();

		@Put
		String PUT();
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
		assertMethod(app.GET());
		assertMethod(app.POST());
		assertMethod(app.PUT());
	}

	@Test
	public void testHttpBasicWithCustomInterceptor() {
		final JRest jrest = new JRest.Builder().build();
		AnotherApplication app = jrest.create(AnotherApplication.class);
		assertMethod(app.GET());
		assertMethod(app.POST());
		assertMethod(app.PUT());
	}

	private void assertMethod(String cred) {
		Assert.assertThat(cred, Matchers.is("Bearer accesstoken"));
	}

}
