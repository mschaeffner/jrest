package com.scarabsoft.jrest.test;

import com.scarabsoft.jrest.annotation.Header;
import com.scarabsoft.jrest.converter.GsonConverterFactory;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.scarabsoft.jrest.JRest;
import com.scarabsoft.jrest.annotation.Get;
import com.scarabsoft.jrest.annotation.Headers;
import com.scarabsoft.jrest.annotation.Post;
import com.scarabsoft.jrest.annotation.Put;
import com.scarabsoft.jrest.annotation.Mapping;
import com.scarabsoft.jrest.test.domain.UserGroup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = FreyaTestApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:1337")
public class SimpleHeaderTest {

	@Mapping(url = "http://localhost:1337/v1/header/simple", converterFactory = GsonConverterFactory.class)
	interface HeaderApp {

		@Headers(headers = { @Header(key = "userId", value = "2"), @Header(key = "groupId", value = "4") })
		@Get
		UserGroup GET();

		@Get
		UserGroup GET2(@Header(key = "userId") String userId, @Header(key = "groupId") int groupId);

		@Headers(headers = { @Header(key = "userId", value = "2"), @Header(key = "groupId", value = "4") })
		@Post
		UserGroup POST();

		@Put
		UserGroup POST2(@Header(key = "userId") String userId, @Header(key = "groupId") int groupId);

		@Headers(headers = { @Header(key = "userId", value = "2"), @Header(key = "groupId", value = "4") })
		@Put
		UserGroup PUT();

		@Put
		UserGroup PUT2(@Header(key = "userId") String userId, @Header(key = "groupId") int groupId);

	}

	@Mapping(url = "http://localhost:1337/v1/header/simple", converterFactory = GsonConverterFactory.class)
	interface InvalidHeaderApp {

		@Headers(headers = { @Header(key = "userId", value = "2"), @Header(key = "groupId") })
		@Get
		UserGroup GET();

		@Headers(headers = { @Header(key = "userId", value = "2") })
		@Get
		UserGroup GET2(@Header(key = "groupId") String groupId);

		@Headers(headers = { @Header(key = "userId", value = "2"), @Header(key = "groupId") })
		@Post
		UserGroup POST();

		@Headers(headers = { @Header(key = "userId", value = "2") })
		@Post
		UserGroup POST2(@Header(key = "groupId") String groupId);

		@Headers(headers = { @Header(key = "userId", value = "2"), @Header(key = "groupId") })
		@Put
		UserGroup PUT();

		@Headers(headers = { @Header(key = "userId", value = "2") })
		@Put
		UserGroup PUT2(@Header(key = "groupId") String groupId);

	}

	@Headers(headers = { @Header(key = "userId", value = "2"), @Header(key = "groupId", value = "4") })
	@Mapping(url = "http://localhost:1337/v1/header/simple", converterFactory = GsonConverterFactory.class)
	interface AnnotatedInterfaceApp {

		@Get
		UserGroup GET();

		@Post
		UserGroup POST();

		@Put
		UserGroup PUT();

	}

	@Headers(headers = { @Header(key = "userId", value = "2"), @Header(key = "groupId") })
	@Mapping(url = "http://localhost:1337/v1/header/simple", converterFactory = GsonConverterFactory.class)
	interface InvalidAnnotatedInterfaceApp {

		@Get
		UserGroup GET();

		@Post
		UserGroup POST();

		@Put
		UserGroup PUT();

	}

	private JRest freya;

	@Before
	public void before() {
		freya = new JRest.Builder().build();
	}

	@Test
	public void GETheaderAppTest() {
		HeaderApp app = freya.create(HeaderApp.class);
		UserGroup userGroup = app.GET();
		Assert.assertThat(userGroup.getUserId(), Matchers.is(2));
		Assert.assertThat(userGroup.getGroupId(), Matchers.is(4));

		userGroup = app.GET2("123", 10);
		Assert.assertThat(userGroup.getUserId(), Matchers.is(123));
		Assert.assertThat(userGroup.getGroupId(), Matchers.is(10));
	}

	@Test(expected = RuntimeException.class)
	public void GETinvalidHeaderAppTest() {
		InvalidHeaderApp app = freya.create(InvalidHeaderApp.class);
		app.GET();
	}

	@Test(expected = RuntimeException.class)
	public void GETanotherInvalidHeaderAppTest() {
		InvalidHeaderApp app = freya.create(InvalidHeaderApp.class);
		app.GET2("");
	}

	@Test
	public void GETannotatedInterfaceAppTest() {
		AnnotatedInterfaceApp app = freya.create(AnnotatedInterfaceApp.class);
		UserGroup userGroup = app.GET();

		Assert.assertThat(userGroup.getUserId(), Matchers.is(2));
		Assert.assertThat(userGroup.getGroupId(), Matchers.is(4));
	}

	@Test(expected = RuntimeException.class)
	public void GETinvalidAnnotatedInterfaceAppTest() {
		InvalidAnnotatedInterfaceApp app = freya.create(InvalidAnnotatedInterfaceApp.class);
		app.GET();
	}

	@Test
	public void POSTheaderAppTest() {
		HeaderApp app = freya.create(HeaderApp.class);
		UserGroup userGroup = app.POST();
		Assert.assertThat(userGroup.getUserId(), Matchers.is(2));
		Assert.assertThat(userGroup.getGroupId(), Matchers.is(4));

		userGroup = app.POST2("123", 10);
		Assert.assertThat(userGroup.getUserId(), Matchers.is(123));
		Assert.assertThat(userGroup.getGroupId(), Matchers.is(10));
	}

	@Test(expected = RuntimeException.class)
	public void POSTinvalidHeaderAppTest() {
		InvalidHeaderApp app = freya.create(InvalidHeaderApp.class);
		app.POST();
	}

	@Test(expected = RuntimeException.class)
	public void POSTanotherInvalidHeaderAppTest() {
		InvalidHeaderApp app = freya.create(InvalidHeaderApp.class);
		app.POST2("");
	}

	@Test
	public void POSTannotatedInterfaceAppTest() {
		AnnotatedInterfaceApp app = freya.create(AnnotatedInterfaceApp.class);
		UserGroup userGroup = app.POST();

		Assert.assertThat(userGroup.getUserId(), Matchers.is(2));
		Assert.assertThat(userGroup.getGroupId(), Matchers.is(4));
	}

	@Test(expected = RuntimeException.class)
	public void POSTinvalidAnnotatedInterfaceAppTest() {
		InvalidAnnotatedInterfaceApp app = freya.create(InvalidAnnotatedInterfaceApp.class);
		app.POST();
	}

	@Test
	public void PUTheaderAppTest() {
		HeaderApp app = freya.create(HeaderApp.class);
		UserGroup userGroup = app.PUT();
		Assert.assertThat(userGroup.getUserId(), Matchers.is(2));
		Assert.assertThat(userGroup.getGroupId(), Matchers.is(4));

		userGroup = app.POST2("123", 10);
		Assert.assertThat(userGroup.getUserId(), Matchers.is(123));
		Assert.assertThat(userGroup.getGroupId(), Matchers.is(10));
	}

	@Test(expected = RuntimeException.class)
	public void PUTinvalidHeaderAppTest() {
		InvalidHeaderApp app = freya.create(InvalidHeaderApp.class);
		app.PUT();
	}

	@Test(expected = RuntimeException.class)
	public void PUTanotherInvalidHeaderAppTest() {
		InvalidHeaderApp app = freya.create(InvalidHeaderApp.class);
		app.PUT2("");
	}

	@Test
	public void PUTannotatedInterfaceAppTest() {
		AnnotatedInterfaceApp app = freya.create(AnnotatedInterfaceApp.class);
		UserGroup userGroup = app.PUT();

		Assert.assertThat(userGroup.getUserId(), Matchers.is(2));
		Assert.assertThat(userGroup.getGroupId(), Matchers.is(4));
	}

	@Test(expected = RuntimeException.class)
	public void PUTinvalidAnnotatedInterfaceAppTest() {
		InvalidAnnotatedInterfaceApp app = freya.create(InvalidAnnotatedInterfaceApp.class);
		app.PUT();
	}

}
