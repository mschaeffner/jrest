package com.scarabsoft.jrest.test;

import com.scarabsoft.jrest.JRest;
import com.scarabsoft.jrest.annotation.Get;
import com.scarabsoft.jrest.annotation.Mapping;
import com.scarabsoft.jrest.annotation.Put;
import com.scarabsoft.jrest.test.domain.IP;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.scarabsoft.jrest.annotation.Post;

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
		final JRest freya = new JRest.Builder().build();
		final SimpleApplication app = freya.create(SimpleApplication.class);
		app.GET();
	}

	@Test(expected = RuntimeException.class)
	public void GETabstractClassImplementationTest() {
		final JRest freya = new JRest.Builder().build();
		final AbstractSimpleApplication app = freya.create(AbstractSimpleApplication.class);
		app.GET();
	}

	@Test(expected = RuntimeException.class)
	public void POSTclassImplementationTestTest() {
		final JRest freya = new JRest.Builder().build();
		final SimpleApplication app = freya.create(SimpleApplication.class);
		app.POST();
	}

	@Test(expected = RuntimeException.class)
	public void POSTabstractClassImplementationTest() {
		final JRest freya = new JRest.Builder().build();
		final AbstractSimpleApplication app = freya.create(AbstractSimpleApplication.class);
		app.POST();
	}

	@Test(expected = RuntimeException.class)
	public void PUTclassImplementationTestTest() {
		final JRest freya = new JRest.Builder().build();
		final SimpleApplication app = freya.create(SimpleApplication.class);
		app.PUT();
	}

	@Test(expected = RuntimeException.class)
	public void PUTabstractClassImplementationTest() {
		final JRest freya = new JRest.Builder().build();
		final AbstractSimpleApplication app = freya.create(AbstractSimpleApplication.class);
		app.PUT();
	}

}
