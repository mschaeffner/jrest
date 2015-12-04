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
import com.scibee.freya.converter.GsonConverterFactory;
import com.scibee.freya.interceptor.domain.HttpApplicationCredentials;
import com.scibee.freya.interceptor.httpbasic.HttpBasicRequestInterceptor;
import com.scibee.freya.interceptor.httpbasic.HttpBasicRequestInterceptorFactory;
import com.scibee.freya.test.FreyaTestApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = FreyaTestApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:1337")
public class HttpBasicInterceptorTest {

	@Mapping(url = "http://localhost:1337/v1/auth/basic", converterFactory = GsonConverterFactory.class)
	interface Application {
		@Get
		HttpApplicationCredentials GET();

		@Post
		HttpApplicationCredentials POST();

		@Put
		HttpApplicationCredentials PUT();
	}

	@Mapping(url = "http://localhost:1337/v1/auth/basic", converterFactory = GsonConverterFactory.class)
	@Interceptor(factory = MyFactory.class)
	interface AnotherApplication {
		@Get
		HttpApplicationCredentials GET();

		@Post
		HttpApplicationCredentials POST();

		@Put
		HttpApplicationCredentials PUT();
	}

	public static class MyFactory extends HttpBasicRequestInterceptorFactory {
		public MyFactory() {
			this.credentials = new HttpApplicationCredentials("acme", "acmesecret");
		}
	}

	@Test
	public void testHttpBasicInterceptor() {
		final Freya freya = new Freya.Builder().addInterceptor(
				new HttpBasicRequestInterceptor(new HttpApplicationCredentials("acme", "acmesecret"))).build();
		Application app = freya.create(Application.class);
		assertCredentials(app.GET());
		assertCredentials(app.POST());
		assertCredentials(app.PUT());
	}

	@Test
	public void testHttpBasicWithCustomInterceptor() {
		final Freya freya = new Freya.Builder().build();
		AnotherApplication app = freya.create(AnotherApplication.class);
		assertCredentials(app.GET());
		assertCredentials(app.POST());
		assertCredentials(app.PUT());
	}

	private void assertCredentials(HttpApplicationCredentials cred) {
		Assert.assertThat(cred.getConsumerkey(), Matchers.is("acme"));
		Assert.assertThat(cred.getConsumerSecret(), Matchers.is("acmesecret"));
	}

}
