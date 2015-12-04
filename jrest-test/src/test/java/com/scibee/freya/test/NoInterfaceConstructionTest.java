package com.scibee.freya.test;

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
import com.scibee.freya.test.domain.IP;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = FreyaTestApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:1337")
public class NoInterfaceConstructionTest {

	@Mapping(url = "http://localhost:1337/v1/simple/ip")
	class SimpleApplication {
		@Get
		IP GET() {
			return null;
		}

		@Post
		IP POST() {
			return null;
		}

		@Put
		IP PUT() {
			return null;
		}
	}

	@Mapping(url = "http://localhost:1337/v1/simple/ip")
	abstract class AbstractSimpleApplication {
		@Get
		abstract IP GET();

		@Post
		abstract IP POST();

		@Put
		abstract IP PUT();
	}

	@Test(expected = RuntimeException.class)
	public void GETclassImplementationTestTest() {
		final Freya freya = new Freya.Builder().build();
		final SimpleApplication app = freya.create(SimpleApplication.class);
		app.GET();
	}

	@Test(expected = RuntimeException.class)
	public void GETabstractClassImplementationTest() {
		final Freya freya = new Freya.Builder().build();
		final AbstractSimpleApplication app = freya.create(AbstractSimpleApplication.class);
		app.GET();
	}

	@Test(expected = RuntimeException.class)
	public void POSTclassImplementationTestTest() {
		final Freya freya = new Freya.Builder().build();
		final SimpleApplication app = freya.create(SimpleApplication.class);
		app.POST();
	}

	@Test(expected = RuntimeException.class)
	public void POSTabstractClassImplementationTest() {
		final Freya freya = new Freya.Builder().build();
		final AbstractSimpleApplication app = freya.create(AbstractSimpleApplication.class);
		app.POST();
	}

	@Test(expected = RuntimeException.class)
	public void PUTclassImplementationTestTest() {
		final Freya freya = new Freya.Builder().build();
		final SimpleApplication app = freya.create(SimpleApplication.class);
		app.PUT();
	}

	@Test(expected = RuntimeException.class)
	public void PUTabstractClassImplementationTest() {
		final Freya freya = new Freya.Builder().build();
		final AbstractSimpleApplication app = freya.create(AbstractSimpleApplication.class);
		app.PUT();
	}

}
