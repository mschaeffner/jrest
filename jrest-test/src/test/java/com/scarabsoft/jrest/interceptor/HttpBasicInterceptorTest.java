package com.scarabsoft.jrest.interceptor;

import com.scarabsoft.jrest.JRest;
import com.scarabsoft.jrest.annotation.*;
import com.scarabsoft.jrest.converter.GsonConverterFactory;
import com.scarabsoft.jrest.HttpApplicationCredentials;
import com.scarabsoft.jrest.interceptor.httpbasic.HttpBasicRequestInterceptor;
import com.scarabsoft.jrest.interceptor.httpbasic.HttpBasicRequestInterceptorFactory;
import com.scarabsoft.jrest.JRestTestApplication;
import org.hamcrest.Matchers;
import org.junit.Assert;
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
public class HttpBasicInterceptorTest {

    @Test
    public void testHttpBasicInterceptor() {
        final JRest jrest = new JRest.Builder().addInterceptor(
                new HttpBasicRequestInterceptor(new HttpApplicationCredentials("acme", "acmesecret"))).build();
        Application app = jrest.create(Application.class);
        assertCredentials(app.GET());
        assertCredentials(app.POST());
        assertCredentials(app.PUT());
        assertCredentials(app.DELETE());
    }

    @Test
    public void testHttpBasicWithCustomInterceptor() {
        final JRest jrest = new JRest.Builder().build();
        AnotherApplication app = jrest.create(AnotherApplication.class);
        assertCredentials(app.GET());
        assertCredentials(app.POST());
        assertCredentials(app.PUT());
        assertCredentials(app.DELETE());
    }

    private void assertCredentials(HttpApplicationCredentials cred) {
        Assert.assertThat(cred.getConsumerKey(), Matchers.is("acme"));
        Assert.assertThat(cred.getConsumerSecret(), Matchers.is("acmesecret"));
    }

    @Mapping(value = "http://localhost:1337/v1/auth/basic", converterFactory = GsonConverterFactory.class)
    interface Application {
        @Get
        HttpApplicationCredentials GET();

        @Post
        HttpApplicationCredentials POST();

        @Put
        HttpApplicationCredentials PUT();

        @Delete
        HttpApplicationCredentials DELETE();
    }

    @Mapping(value = "http://localhost:1337/v1/auth/basic", converterFactory = GsonConverterFactory.class)
    @Interceptor(value = MyFactory.class)
    interface AnotherApplication {
        @Get
        HttpApplicationCredentials GET();

        @Post
        HttpApplicationCredentials POST();

        @Put
        HttpApplicationCredentials PUT();

        @Delete
        HttpApplicationCredentials DELETE();
    }

    public static class MyFactory extends HttpBasicRequestInterceptorFactory {
        public MyFactory() {
            this.credentials = new HttpApplicationCredentials("acme", "acmesecret");
        }
    }

}
