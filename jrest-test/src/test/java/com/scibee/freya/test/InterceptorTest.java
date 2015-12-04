package com.scibee.freya.test;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.scibee.freya.Freya;
import com.scibee.freya.annotation.Get;
import com.scibee.freya.annotation.Interceptor;
import com.scibee.freya.annotation.Interceptors;
import com.scibee.freya.converter.GsonConverterFactory;
import com.scibee.freya.interceptor.HeaderEntity;
import com.scibee.freya.interceptor.RequestInterceptor;
import com.scibee.freya.interceptor.RequestInterceptorFactory;
import com.scibee.freya.interceptor.domain.RequestEntity;
import com.scibee.freya.test.domain.UserGroup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = FreyaTestApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:1337")
public class InterceptorTest {

	public static class UserInterceptorFactory implements RequestInterceptorFactory {
		@Override
		public RequestInterceptor get() {
			return new UserInterceptor("99");
		}
	}

	public static class AnotherUserInterceptorFactory implements RequestInterceptorFactory {
		@Override
		public RequestInterceptor get() {
			return new UserInterceptor("-1");
		}
	}

	public static class UserInterceptor implements RequestInterceptor {
		String userId;

		public UserInterceptor(String userId) {
			this.userId = userId;
		}

		@Override
		public void intercept(RequestEntity requestEntity) {
			requestEntity.addHeader(new HeaderEntity("userId", userId));
		}
	}

	public static class GroupInterceptorFactory implements RequestInterceptorFactory {
		@Override
		public RequestInterceptor get() {
			return new GroupInterceptor("1234");
		}
	}

	public static class AnotherGroupInterceptorFactory implements RequestInterceptorFactory {
		@Override
		public RequestInterceptor get() {
			return new GroupInterceptor("-1");
		}
	}

	public static class GroupInterceptor implements RequestInterceptor {
		String groupId;

		public GroupInterceptor(String groupId) {
			this.groupId = groupId;
		}

		@Override
		public void intercept(RequestEntity requestEntity) {
			requestEntity.addHeader(new HeaderEntity("groupId", groupId));
		}
	}

	interface AppwithoutInterceptors {
		@Get
		UserGroup GET();

		@com.scibee.freya.annotation.Post
		UserGroup POST();

		@com.scibee.freya.annotation.Put
		UserGroup PUT();
	}

	@Interceptors(interceptors = { @Interceptor(factory = UserInterceptorFactory.class) })
	interface AppWithInterfaceInterceptors {
		@Get
		UserGroup GET();

		@com.scibee.freya.annotation.Post
		UserGroup POST();

		@com.scibee.freya.annotation.Put
		UserGroup PUT();
	}

	@Interceptor(factory = UserInterceptorFactory.class)
	interface AppWithAnotherInterfaceInterceptor {
		@Get
		UserGroup GET();

		@com.scibee.freya.annotation.Post
		UserGroup POST();

		@com.scibee.freya.annotation.Put
		UserGroup PUT();
	}

	@Interceptor(factory = UserInterceptorFactory.class)
	@Interceptors(interceptors = { @Interceptor(factory = GroupInterceptorFactory.class) })
	interface AppWithFullInterfaceInterceptor {
		@Get
		UserGroup GET();

		@com.scibee.freya.annotation.Post
		UserGroup POST();

		@com.scibee.freya.annotation.Put
		UserGroup PUT();
	}

	interface AppMethodInterfaceInterceptor {

		@Interceptor(factory = UserInterceptorFactory.class)
		@Interceptors(interceptors = { @Interceptor(factory = GroupInterceptorFactory.class) })
		@Get
		UserGroup GET();

		@Interceptor(factory = UserInterceptorFactory.class)
		@Interceptors(interceptors = { @Interceptor(factory = GroupInterceptorFactory.class) })
		@com.scibee.freya.annotation.Post
		UserGroup POST();

		@Interceptors(interceptors = { @Interceptor(factory = GroupInterceptorFactory.class),
				@Interceptor(factory = UserInterceptorFactory.class) })
		@com.scibee.freya.annotation.Put
		UserGroup PUT();
	}

	@Ignore
	@Test
	public void appWithoutInterceptorTest() {
		final Freya freya = new Freya.Builder().baseUrl("http://localhost:1337/v1/header/simple")
				.addInterceptor(new UserInterceptor("99")).addInterceptor(new GroupInterceptor("1234"))
				.coverterFactory(new GsonConverterFactory()).build();
		AppwithoutInterceptors app = freya.create(AppwithoutInterceptors.class);
		assertUserGroup(app.GET());
		assertUserGroup(app.POST());
		assertUserGroup(app.POST());
	}

	@Test
	public void appWithInterfaceInterceptorTest() {
		final Freya freya = new Freya.Builder().baseUrl("http://localhost:1337/v1/header/simple")
				.addInterceptor(new GroupInterceptor("1234")).coverterFactory(new GsonConverterFactory()).build();
		AppWithInterfaceInterceptors app = freya.create(AppWithInterfaceInterceptors.class);
		assertUserGroup(app.GET());
		assertUserGroup(app.POST());
		assertUserGroup(app.POST());
	}

	@Test
	public void appWithInterfaceAnotherInterceptorTest() {
		final Freya freya = new Freya.Builder().baseUrl("http://localhost:1337/v1/header/simple")
				.addInterceptor(new GroupInterceptor("1234")).coverterFactory(new GsonConverterFactory()).build();
		AppWithAnotherInterfaceInterceptor app = freya.create(AppWithAnotherInterfaceInterceptor.class);
		assertUserGroup(app.GET());
		assertUserGroup(app.POST());
		assertUserGroup(app.POST());
	}

	@Test
	public void appWithInterfaceFullInterceptorTest() {
		final Freya freya = new Freya.Builder().baseUrl("http://localhost:1337/v1/header/simple")
				.coverterFactory(new GsonConverterFactory()).build();
		AppWithFullInterfaceInterceptor app = freya.create(AppWithFullInterfaceInterceptor.class);
		assertUserGroup(app.GET());
		assertUserGroup(app.POST());
		assertUserGroup(app.POST());
	}

	@Test
	public void appMethodInterfaceInterceptor() {
		final Freya freya = new Freya.Builder().baseUrl("http://localhost:1337/v1/header/simple")
				.coverterFactory(new GsonConverterFactory()).build();
		AppMethodInterfaceInterceptor app = freya.create(AppMethodInterfaceInterceptor.class);
		assertUserGroup(app.GET());
		assertUserGroup(app.POST());
		assertUserGroup(app.POST());
	}

	private void assertUserGroup(UserGroup ug) {
		Assert.assertThat(ug.getUserId(), Matchers.is(99));
		Assert.assertThat(ug.getGroupId(), Matchers.is(1234));
	}

}
