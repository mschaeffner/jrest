package com.scarabsoft.jrest.test.interceptor;

import com.scarabsoft.jrest.converter.GsonConverterFactory;
import com.scarabsoft.jrest.interceptor.domain.HttpApplicationCredentials;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.scarabsoft.jrest.JRest;
import com.scarabsoft.jrest.annotation.Get;
import com.scarabsoft.jrest.annotation.Interceptor;
import com.scarabsoft.jrest.annotation.Post;
import com.scarabsoft.jrest.annotation.Put;
import com.scarabsoft.jrest.annotation.Mapping;
import com.scarabsoft.jrest.interceptor.httpbasic.HttpBasicRequestInterceptor;
import com.scarabsoft.jrest.interceptor.httpbasic.HttpBasicRequestInterceptorFactory;
import com.scarabsoft.jrest.test.JRestTestApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = JRestTestApplication.class)
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
		final JRest jrest = new JRest.Builder().addInterceptor(
				new HttpBasicRequestInterceptor(new HttpApplicationCredentials("acme", "acmesecret"))).build();
		Application app = jrest.create(Application.class);
		assertCredentials(app.GET());
		assertCredentials(app.POST());
		assertCredentials(app.PUT());
	}

	@Test
	public void testHttpBasicWithCustomInterceptor() {
		final JRest jrest = new JRest.Builder().build();
		AnotherApplication app = jrest.create(AnotherApplication.class);
		assertCredentials(app.GET());
		assertCredentials(app.POST());
		assertCredentials(app.PUT());
	}

	private void assertCredentials(HttpApplicationCredentials cred) {
		Assert.assertThat(cred.getConsumerkey(), Matchers.is("acme"));
		Assert.assertThat(cred.getConsumerSecret(), Matchers.is("acmesecret"));
	}

}
