package com.scibee.freya.test.interceptor;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.scibee.freya.Freya;
import com.scibee.freya.annotation.Get;
import com.scibee.freya.annotation.Interceptor;
import com.scibee.freya.annotation.Post;
import com.scibee.freya.annotation.Put;
import com.scibee.freya.annotation.Mapping;
import com.scibee.freya.converter.StringConverterFactory;
import com.scibee.freya.interceptor.oauth2.HttpOAuth2RequestInterceptor;
import com.scibee.freya.interceptor.oauth2.HttpOAuth2RequestInterceptorFactory;
import com.scibee.freya.test.FreyaTestApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = FreyaTestApplication.class)
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
		final Freya freya = new Freya.Builder().addInterceptor(new HttpOAuth2RequestInterceptor("accesstoken")).build();
		Application app = freya.create(Application.class);
		assertMethod(app.GET());
		assertMethod(app.POST());
		assertMethod(app.PUT());
	}

	@Test
	public void testHttpBasicWithCustomInterceptor() {
		final Freya freya = new Freya.Builder().build();
		AnotherApplication app = freya.create(AnotherApplication.class);
		assertMethod(app.GET());
		assertMethod(app.POST());
		assertMethod(app.PUT());
	}

	private void assertMethod(String cred) {
		Assert.assertThat(cred, Matchers.is("Bearer accesstoken"));
	}

}
