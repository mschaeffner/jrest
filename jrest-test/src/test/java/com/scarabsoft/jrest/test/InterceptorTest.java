package com.scarabsoft.jrest.test;

import com.scarabsoft.jrest.JRest;
import com.scarabsoft.jrest.annotation.*;
import com.scarabsoft.jrest.converter.GsonConverterFactory;
import com.scarabsoft.jrest.interceptor.RequestInterceptor;
import com.scarabsoft.jrest.interceptor.RequestInterceptorFactory;
import com.scarabsoft.jrest.interceptor.domain.RequestEntity;
import com.scarabsoft.jrest.test.domain.UserGroup;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = JRestTestApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:1337")
public class InterceptorTest {

    @Ignore
    @Test
    public void appWithoutInterceptorTest() {
        final JRest jrest = new JRest.Builder().baseUrl("http://localhost:1337/v1/header/simple")
                .addInterceptor(new UserInterceptor("99")).addInterceptor(new GroupInterceptor("1234"))
                .coverterFactory(new GsonConverterFactory()).build();
        AppwithoutInterceptors app = jrest.create(AppwithoutInterceptors.class);
        assertUserGroup(app.GET());
        assertUserGroup(app.POST());
        assertUserGroup(app.POST());
    }

    @Test
    public void appWithInterfaceInterceptorTest() {
        final JRest jrest = new JRest.Builder().baseUrl("http://localhost:1337/v1/header/simple")
                .addInterceptor(new GroupInterceptor("1234")).coverterFactory(new GsonConverterFactory()).build();
        AppWithInterfaceInterceptors app = jrest.create(AppWithInterfaceInterceptors.class);
        assertUserGroup(app.GET());
        assertUserGroup(app.POST());
        assertUserGroup(app.POST());
    }

    @Test
    public void appWithInterfaceAnotherInterceptorTest() {
        final JRest jrest = new JRest.Builder().baseUrl("http://localhost:1337/v1/header/simple")
                .addInterceptor(new GroupInterceptor("1234")).coverterFactory(new GsonConverterFactory()).build();
        AppWithAnotherInterfaceInterceptor app = jrest.create(AppWithAnotherInterfaceInterceptor.class);
        assertUserGroup(app.GET());
        assertUserGroup(app.POST());
        assertUserGroup(app.POST());
    }

    @Test
    public void appWithInterfaceFullInterceptorTest() {
        final JRest jrest = new JRest.Builder().baseUrl("http://localhost:1337/v1/header/simple")
                .coverterFactory(new GsonConverterFactory()).build();
        AppWithFullInterfaceInterceptor app = jrest.create(AppWithFullInterfaceInterceptor.class);
        assertUserGroup(app.GET());
        assertUserGroup(app.POST());
        assertUserGroup(app.POST());
    }

    @Test
    public void appMethodInterfaceInterceptor() {
        final JRest jrest = new JRest.Builder().baseUrl("http://localhost:1337/v1/header/simple")
                .coverterFactory(new GsonConverterFactory()).build();
        AppMethodInterfaceInterceptor app = jrest.create(AppMethodInterfaceInterceptor.class);
        assertUserGroup(app.GET());
        assertUserGroup(app.POST());
        assertUserGroup(app.POST());
    }

    private void assertUserGroup(UserGroup ug) {
        Assert.assertThat(ug.getUserId(), Matchers.is(99));
        Assert.assertThat(ug.getGroupId(), Matchers.is(1234));
    }

    interface AppwithoutInterceptors {
        @Get
        UserGroup GET();

        @Post
        UserGroup POST();

        @Put
        UserGroup PUT();
    }

    @Interceptors(interceptors = {@Interceptor(factory = UserInterceptorFactory.class)})
    interface AppWithInterfaceInterceptors {
        @Get
        UserGroup GET();

        @Post
        UserGroup POST();

        @Put
        UserGroup PUT();
    }

    @Interceptor(factory = UserInterceptorFactory.class)
    interface AppWithAnotherInterfaceInterceptor {
        @Get
        UserGroup GET();

        @Post
        UserGroup POST();

        @Put
        UserGroup PUT();
    }

    @Interceptor(factory = UserInterceptorFactory.class)
    @Interceptors(interceptors = {@Interceptor(factory = GroupInterceptorFactory.class)})
    interface AppWithFullInterfaceInterceptor {
        @Get
        UserGroup GET();

        @Post
        UserGroup POST();

        @Put
        UserGroup PUT();
    }

    interface AppMethodInterfaceInterceptor {

        @Interceptor(factory = UserInterceptorFactory.class)
        @Interceptors(interceptors = {@Interceptor(factory = GroupInterceptorFactory.class)})
        @Get
        UserGroup GET();

        @Interceptor(factory = UserInterceptorFactory.class)
        @Interceptors(interceptors = {@Interceptor(factory = GroupInterceptorFactory.class)})
        @Post
        UserGroup POST();

        @Interceptors(interceptors = {@Interceptor(factory = GroupInterceptorFactory.class),
                @Interceptor(factory = UserInterceptorFactory.class)})
        @Put
        UserGroup PUT();
    }

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
            requestEntity.addHeader("userId", userId);
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
            requestEntity.addHeader("groupId", groupId);
        }
    }

}
