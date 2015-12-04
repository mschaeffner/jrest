package com.scibee.freya.test;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.scibee.freya.Freya;
import com.scibee.freya.annotation.Body;
import com.scibee.freya.annotation.Get;
import com.scibee.freya.annotation.Mapping;
import com.scibee.freya.annotation.Post;
import com.scibee.freya.annotation.Put;
import com.scibee.freya.converter.GsonConverterFactory;
import com.scibee.freya.test.domain.IP;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = FreyaTestApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:1337")
public class BodyTest {

	@Mapping(url = "http://localhost:1337/v1/body", converterFactory = GsonConverterFactory.class)
	interface App {
		@Get
		IP GET(@Body IP ip);

		@Post
		IP POST(@Body IP ip);

		@Put
		void PUT(@Body IP ip);

	}

	private Freya freya;

	@Before
	public void before() {
		freya = new Freya.Builder().build();
	}

	@Test(expected = RuntimeException.class)
	public void GetTest() {
		App app = freya.create(App.class);
		final IP ip = new IP.Builder().ip("127.0.0.2").requestDate(System.currentTimeMillis()).getCountry("de").build();
		app.GET(ip);
	}

	@Test
	public void PostTest() {
		App app = freya.create(App.class);
		final IP ip = new IP.Builder().ip("127.0.0.2").requestDate(System.currentTimeMillis()).getCountry("de").build();
		IP result = app.POST(ip);

		Assert.assertThat(result.getIp(), Matchers.is("127.0.0.2"));
		Assert.assertThat(result.getRequestDate(), Matchers.is(ip.getRequestDate()));
		Assert.assertThat(result.getCountry(), Matchers.is("de"));

	}

	@Test
	public void PutTest() {
		App app = freya.create(App.class);
		final IP ip = new IP.Builder().ip("127.0.0.2").requestDate(System.currentTimeMillis()).getCountry("de").build();
		app.PUT(ip);
	}
}
