package com.scarabsoft.jrest.test;

import com.scarabsoft.jrest.converter.GsonConverterFactory;
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
import com.scarabsoft.jrest.annotation.Post;
import com.scarabsoft.jrest.annotation.Put;
import com.scarabsoft.jrest.annotation.Mapping;
import com.scarabsoft.jrest.test.domain.IP;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = FreyaTestApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:1337")
public class ConverterTests {

	@Mapping(url = "http://localhost:1337/v1/simple/ip")
	interface SimpleApplication {
		@Get
		IP GET();

		@Post
		IP POST();

		@Put
		IP PUT();
	}

	@Mapping(url = "http://localhost:1337/v1/simple/ip", converterFactory = GsonConverterFactory.class)
	interface ApplicationWithConverterFactory {
		@Get
		IP GET();

		@Post
		IP POST();

		@Put
		IP PUT();

	}

	@Mapping(url = "http://localhost:1337/v1/simple/ip", converterFactory = GsonConverterFactory.class)
	interface VoidInterface {

		@Get
		void GET();

		@Get
		Void VGET();

		@Post
		void POST();

		@Post
		Void VPOST();

		@Put
		void PUT();

		@Put
		Void VPUT();

	}

	@Test(expected = RuntimeException.class)
	public void GETmissingConverterTest() {
		final JRest freya = new JRest.Builder().build();
		final SimpleApplication app = freya.create(SimpleApplication.class);
		app.GET();
	}

	@Test
	public void GETConverterInFreyaTest() {
		final JRest freya = new JRest.Builder().coverterFactory(new GsonConverterFactory()).build();
		final SimpleApplication app = freya.create(SimpleApplication.class);
		final IP ip = app.GET();
		Assert.assertThat(ip.getCountry(), Matchers.is("DE"));
		Assert.assertThat(ip.getIp(), Matchers.is("127.0.0.1"));
	}

	@Test
	public void GETConverterInApplicationTest() {
		final JRest freya = new JRest.Builder().build();
		final ApplicationWithConverterFactory app = freya.create(ApplicationWithConverterFactory.class);
		final IP ip = app.GET();
		Assert.assertThat(ip.getCountry(), Matchers.is("DE"));
		Assert.assertThat(ip.getIp(), Matchers.is("127.0.0.1"));
	}

	@Test(expected = RuntimeException.class)
	public void POSTmissingConverterTest() {
		final JRest freya = new JRest.Builder().build();
		final SimpleApplication app = freya.create(SimpleApplication.class);
		app.POST();
	}

	@Test
	public void POSTConverterInFreyaTest() {
		final JRest freya = new JRest.Builder().coverterFactory(new GsonConverterFactory()).build();
		final SimpleApplication app = freya.create(SimpleApplication.class);
		final IP ip = app.POST();
		Assert.assertThat(ip.getCountry(), Matchers.is("DE"));
		Assert.assertThat(ip.getIp(), Matchers.is("127.0.0.1"));
	}

	@Test
	public void POSTConverterInApplicationTest() {
		final JRest freya = new JRest.Builder().build();
		final ApplicationWithConverterFactory app = freya.create(ApplicationWithConverterFactory.class);
		final IP ip = app.POST();
		Assert.assertThat(ip.getCountry(), Matchers.is("DE"));
		Assert.assertThat(ip.getIp(), Matchers.is("127.0.0.1"));
	}

	@Test(expected = RuntimeException.class)
	public void PUTmissingConverterTest() {
		final JRest freya = new JRest.Builder().build();
		final SimpleApplication app = freya.create(SimpleApplication.class);
		app.PUT();
	}

	@Test
	public void PUTConverterInFreyaTest() {
		final JRest freya = new JRest.Builder().coverterFactory(new GsonConverterFactory()).build();
		final SimpleApplication app = freya.create(SimpleApplication.class);
		final IP ip = app.PUT();
		Assert.assertThat(ip.getCountry(), Matchers.is("DE"));
		Assert.assertThat(ip.getIp(), Matchers.is("127.0.0.1"));
	}

	@Test
	public void PUTConverterInApplicationTest() {
		final JRest freya = new JRest.Builder().build();
		final ApplicationWithConverterFactory app = freya.create(ApplicationWithConverterFactory.class);
		final IP ip = app.PUT();
		Assert.assertThat(ip.getCountry(), Matchers.is("DE"));
		Assert.assertThat(ip.getIp(), Matchers.is("127.0.0.1"));
	}

	@Test
	public void voidTest() {
		final JRest freya = new JRest.Builder().build();
		VoidInterface app = freya.create(VoidInterface.class);
		app.GET();
		app.VGET();
		app.POST();
		app.VPOST();
		app.PUT();
		app.VPUT();
	}

}
