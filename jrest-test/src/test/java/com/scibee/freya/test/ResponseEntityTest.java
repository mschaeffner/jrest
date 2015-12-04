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
import com.scibee.freya.annotation.Get;
import com.scibee.freya.annotation.Param;
import com.scibee.freya.annotation.Post;
import com.scibee.freya.annotation.Put;
import com.scibee.freya.converter.GsonConverterFactory;
import com.scibee.freya.converter.exception.StringExceptionConverterFactory;
import com.scibee.freya.interceptor.ResponseEntity;
import com.scibee.freya.test.domain.UserGroup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = FreyaTestApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:1337")
public class ResponseEntityTest {

	private Freya freya;

	private ResApp resApp;

	@Before
	public void before() {
		freya = new Freya.Builder().baseUrl("http://localhost:1337/v1/response")
				.coverterFactory(new GsonConverterFactory()).exceptionFactory(new StringExceptionConverterFactory())
				.build();
		resApp = freya.create(ResApp.class);
	}

	@Test
	public void GETTest() {
		test(resApp.GET(1, 1));
	}

	@Test
	public void POSTTest() {
		test(resApp.GET(1, 1));
	}

	@Test
	public void PUTTest() {
		test(resApp.GET(1, 1));
	}

	private void test(ResponseEntity<UserGroup> res) {
		Assert.assertThat(res.getObject().getUserId(), Matchers.is(1));
		Assert.assertThat(res.getObject().getGroupId(), Matchers.is(1));
		Assert.assertThat(res.getHeader("userId").get(), Matchers.is("1"));
		Assert.assertThat(res.getHeader("groupId").get(), Matchers.is("1"));
		Assert.assertThat(res.getHeader("Cookie").get(), Matchers.is("cookie"));
		Assert.assertThat(res.getStatusCode(), Matchers.is(200));
	}

	interface ResApp {

		@Get
		ResponseEntity<UserGroup> GET(@Param(name = "userId") int userId, @Param(name = "groupId") int groupId);

		@Post
		ResponseEntity<UserGroup> POST(@Param(name = "userId") int userId, @Param(name = "groupId") int groupId);

		@Put
		ResponseEntity<UserGroup> PUT(@Param(name = "userId") int userId, @Param(name = "groupId") int groupId);
	}

}
