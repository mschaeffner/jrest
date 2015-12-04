package com.scibee.freya.test.exception;

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
import com.scibee.freya.annotation.Mapping;
import com.scibee.freya.converter.StringConverterFactory;
import com.scibee.freya.test.FreyaTestApplication;
import com.scibee.freya.test.converter.SimpleExceptionConverterFactory;
import com.scibee.freya.test.converter.SpringDefaultExceptionConverterFactory;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = FreyaTestApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:1337")
public class InternalServerExceptionTest {

	@Mapping(url = "http://localhost:1337/v1/error/500", converterFactory = StringConverterFactory.class, exceptionFactory = SpringDefaultExceptionConverterFactory.class)
	interface ExceptionAppViaInterface {
		@Get
		String getException();
	}

	@Mapping(url = "http://localhost:1337/v1/error/500", converterFactory = StringConverterFactory.class)
	interface ExceptionAppViaFreya {
		@Get
		String getException();
	}

	@Test
	public void internalServerExceptionTest() {
		final Freya freya = new Freya.Builder().build();
		final ExceptionAppViaInterface app = freya.create(ExceptionAppViaInterface.class);
		try {
			app.getException();
		} catch (SpringDefaultException e) {
			Assert.assertThat(e.getPath(), Matchers.is("/v1/error/500"));
			Assert.assertThat(e.getStatus(), Matchers.is(500));
			Assert.assertThat(e.getError(), Matchers.is("Internal Server Error"));
			Assert.assertThat(e.getException(), Matchers.is("java.lang.RuntimeException"));
			Assert.assertThat(e.getMessage(), Matchers.is("test"));
		}
	}

	@Test
	public void internalServerNestedExceptionTest() {
		final Freya freya = new Freya.Builder().exceptionFactory(new SimpleExceptionConverterFactory()).build();
		final ExceptionAppViaInterface app = freya.create(ExceptionAppViaInterface.class);
		try {
			app.getException();
		} catch (SpringDefaultException e) {
			Assert.assertThat(e.getPath(), Matchers.is("/v1/error/500"));
			Assert.assertThat(e.getStatus(), Matchers.is(500));
			Assert.assertThat(e.getError(), Matchers.is("Internal Server Error"));
			Assert.assertThat(e.getException(), Matchers.is("java.lang.RuntimeException"));
			Assert.assertThat(e.getMessage(), Matchers.is("test"));
		}
	}

	@Test
	public void internalServerExceptionFreyaTest() {
		final Freya freya = new Freya.Builder().exceptionFactory(new SimpleExceptionConverterFactory()).build();
		final ExceptionAppViaFreya app = freya.create(ExceptionAppViaFreya.class);
		try {
			app.getException();
		} catch (SimpleException e) {
			Assert.assertThat(e.getPath(), Matchers.is("/v1/error/500"));
			Assert.assertThat(e.getMessage(), Matchers.is("test"));
		}
	}

}
