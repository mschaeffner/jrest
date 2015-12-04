package com.scibee.freya.test;

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
import com.scibee.freya.annotation.Post;
import com.scibee.freya.annotation.Put;
import com.scibee.freya.annotation.Mapping;
import com.scibee.freya.converter.StringConverterFactory;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = FreyaTestApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:1337")
public class UrlConcatenationTest {

	@Mapping(url = "/v1")
	public interface AppWithFullUrl {
		@Get(url = "/url")
		String GET();

		@Post(url = "/url")
		String POST();

		@Put(url = "/url")
		String PUT();
	}

	@Mapping(url = "/v1/url")
	public interface AppWithInterfaceUrlApp {
		@Get
		String GET();

		@Post
		String POST();

		@Put
		String PUT();
	}

	public interface AppWithoutUrl {
		@Get
		String GET();

		@Post
		String POST();

		@Put
		String PUT();
	}

	@Test
	public void GETFreyaOnlyUrlTest() {
		final Freya freya = new Freya.Builder().baseUrl("http://localhost:1337/v1/url")
				.coverterFactory(new StringConverterFactory()).build();
		AppWithoutUrl app = freya.create(AppWithoutUrl.class);
		String str = app.GET();
		Assert.assertThat(str, Matchers.is("HelloWorld"));
	}

	@Test
	public void GETFreyaAndInterfaceUrlTest() {
		final Freya freya = new Freya.Builder().baseUrl("http://localhost:1337/")
				.coverterFactory(new StringConverterFactory()).build();
		AppWithInterfaceUrlApp app = freya.create(AppWithInterfaceUrlApp.class);
		String str = app.GET();
		Assert.assertThat(str, Matchers.is("HelloWorld"));
	}

	@Test
	public void GETFreyaNestedUrlTest() {
		final Freya freya = new Freya.Builder().baseUrl("http://localhost:1337/")
				.coverterFactory(new StringConverterFactory()).build();
		AppWithFullUrl app = freya.create(AppWithFullUrl.class);
		String str = app.GET();
		Assert.assertThat(str, Matchers.is("HelloWorld"));
	}

	@Test
	public void POSTFreyaOnlyUrlTest() {
		final Freya freya = new Freya.Builder().baseUrl("http://localhost:1337/v1/url")
				.coverterFactory(new StringConverterFactory()).build();
		AppWithoutUrl app = freya.create(AppWithoutUrl.class);
		String str = app.POST();
		Assert.assertThat(str, Matchers.is("HelloWorld"));
	}

	@Test
	public void POSTFreyaAndInterfaceUrlTest() {
		final Freya freya = new Freya.Builder().baseUrl("http://localhost:1337/")
				.coverterFactory(new StringConverterFactory()).build();
		AppWithInterfaceUrlApp app = freya.create(AppWithInterfaceUrlApp.class);
		String str = app.POST();
		Assert.assertThat(str, Matchers.is("HelloWorld"));
	}

	@Test
	public void POSTFreyaNestedUrlTest() {
		final Freya freya = new Freya.Builder().baseUrl("http://localhost:1337/")
				.coverterFactory(new StringConverterFactory()).build();
		AppWithFullUrl app = freya.create(AppWithFullUrl.class);
		String str = app.POST();
		Assert.assertThat(str, Matchers.is("HelloWorld"));
	}

	@Test
	public void PUTFreyaOnlyUrlTest() {
		final Freya freya = new Freya.Builder().baseUrl("http://localhost:1337/v1/url")
				.coverterFactory(new StringConverterFactory()).build();
		AppWithoutUrl app = freya.create(AppWithoutUrl.class);
		String str = app.PUT();
		Assert.assertThat(str, Matchers.is("HelloWorld"));
	}

	@Test
	public void PUTFreyaAndInterfaceUrlTest() {
		final Freya freya = new Freya.Builder().baseUrl("http://localhost:1337/")
				.coverterFactory(new StringConverterFactory()).build();
		AppWithInterfaceUrlApp app = freya.create(AppWithInterfaceUrlApp.class);
		String str = app.PUT();
		Assert.assertThat(str, Matchers.is("HelloWorld"));
	}

	@Test
	public void PUTFreyaNestedUrlTest() {
		final Freya freya = new Freya.Builder().baseUrl("http://localhost:1337/")
				.coverterFactory(new StringConverterFactory()).build();
		AppWithFullUrl app = freya.create(AppWithFullUrl.class);
		String str = app.PUT();
		Assert.assertThat(str, Matchers.is("HelloWorld"));
	}
}
