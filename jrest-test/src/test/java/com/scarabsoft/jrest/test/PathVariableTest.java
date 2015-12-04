package com.scarabsoft.jrest.test;

import com.scarabsoft.jrest.annotation.Put;
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
import com.scarabsoft.jrest.annotation.Path;
import com.scarabsoft.jrest.annotation.Mapping;
import com.scarabsoft.jrest.test.domain.UserGroup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = JRestTestApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:1337")
public class PathVariableTest {

	@Mapping(url = "http://localhost:1337/v1", converterFactory = GsonConverterFactory.class)
	static interface Application {

		@Get(url = "/path/{userId}/{groupId}")
		UserGroup GET(@Path(name = "userId") int userId, @Path(name = "groupId") int groupId);

		@Get(url = "/path/4/5")
		UserGroup GETWithout();

		@Post(url = "/path/{userId}/{groupId}")
		UserGroup POST(@Path(name = "userId") int userId, @Path(name = "groupId") int groupId);

		@Post(url = "/path/4/5")
		UserGroup POSTWithout();

		@Put(url = "/path/{userId}/{groupId}")
		UserGroup PUT(@Path(name = "userId") int userId, @Path(name = "groupId") int groupId);

		@Put(url = "/path/4/5")
		UserGroup PUTWithout();

	}

	@Test
	public void GETtest() {
		JRest jrest = new JRest.Builder().build();
		Application app = jrest.create(Application.class);
		UserGroup obj = app.GET(1, 2);
		Assert.assertThat(obj.getUserId(), Matchers.is(1));
		Assert.assertThat(obj.getGroupId(), Matchers.is(2));

		obj = app.GETWithout();
		Assert.assertThat(obj.getUserId(), Matchers.is(4));
		Assert.assertThat(obj.getGroupId(), Matchers.is(5));
	}

	@Test
	public void POSTtest() {
		JRest jrest = new JRest.Builder().build();
		Application app = jrest.create(Application.class);
		UserGroup obj = app.POST(1, 2);
		Assert.assertThat(obj.getUserId(), Matchers.is(1));
		Assert.assertThat(obj.getGroupId(), Matchers.is(2));

		obj = app.POSTWithout();
		Assert.assertThat(obj.getUserId(), Matchers.is(4));
		Assert.assertThat(obj.getGroupId(), Matchers.is(5));
	}

	@Test
	public void PUTtest() {
		JRest jrest = new JRest.Builder().build();
		Application app = jrest.create(Application.class);
		UserGroup obj = app.PUT(1, 2);
		Assert.assertThat(obj.getUserId(), Matchers.is(1));
		Assert.assertThat(obj.getGroupId(), Matchers.is(2));

		obj = app.PUTWithout();
		Assert.assertThat(obj.getUserId(), Matchers.is(4));
		Assert.assertThat(obj.getGroupId(), Matchers.is(5));
	}

}
