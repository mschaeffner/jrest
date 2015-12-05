package com.scarabsoft.jrest;

import com.scarabsoft.jrest.annotation.Get;
import com.scarabsoft.jrest.annotation.Mapping;
import com.scarabsoft.jrest.annotation.Post;
import com.scarabsoft.jrest.annotation.Put;
import com.scarabsoft.jrest.converter.StringConverterFactory;
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
public class UrlConcatenationTest {

    private void assertion(String str) {
        Assert.assertThat(str, Matchers.is("HelloWorld"));
    }

    @Test
    public void GETjrestOnlyUrlTest() {
        final JRest jrest = new JRest.Builder().baseUrl("http://localhost:1337/v1/url")
                .coverterFactory(new StringConverterFactory()).build();
        AppWithoutUrl app = jrest.create(AppWithoutUrl.class);
        assertion(app.GET());
    }

    @Test
    public void GETjrestAndInterfaceUrlTest() {
        final JRest jrest = new JRest.Builder().baseUrl("http://localhost:1337/")
                .coverterFactory(new StringConverterFactory()).build();
        AppWithInterfaceUrlApp app = jrest.create(AppWithInterfaceUrlApp.class);
        assertion(app.GET());
    }

    @Test
    public void GETjrestNestedUrlTest() {
        final JRest jrest = new JRest.Builder().baseUrl("http://localhost:1337/")
                .coverterFactory(new StringConverterFactory()).build();
        AppWithFullUrl app = jrest.create(AppWithFullUrl.class);
        assertion(app.GET());
    }

    @Test
    public void POSTjrestOnlyUrlTest() {
        final JRest jrest = new JRest.Builder().baseUrl("http://localhost:1337/v1/url")
                .coverterFactory(new StringConverterFactory()).build();
        AppWithoutUrl app = jrest.create(AppWithoutUrl.class);
        assertion(app.POST());
    }

    @Test
    public void POSTjrestAndInterfaceUrlTest() {
        final JRest jrest = new JRest.Builder().baseUrl("http://localhost:1337/")
                .coverterFactory(new StringConverterFactory()).build();
        AppWithInterfaceUrlApp app = jrest.create(AppWithInterfaceUrlApp.class);
        assertion(app.POST());
    }

    @Test
    public void POSTjrestNestedUrlTest() {
        final JRest jrest = new JRest.Builder().baseUrl("http://localhost:1337/")
                .coverterFactory(new StringConverterFactory()).build();
        AppWithFullUrl app = jrest.create(AppWithFullUrl.class);
        String str = app.POST();
        Assert.assertThat(str, Matchers.is("HelloWorld"));
    }

    @Test
    public void PUTjrestOnlyUrlTest() {
        final JRest jrest = new JRest.Builder().baseUrl("http://localhost:1337/v1/url")
                .coverterFactory(new StringConverterFactory()).build();
        AppWithoutUrl app = jrest.create(AppWithoutUrl.class);
        assertion(app.PUT());
    }

    @Test
    public void PUTjrestAndInterfaceUrlTest() {
        final JRest jrest = new JRest.Builder().baseUrl("http://localhost:1337/")
                .coverterFactory(new StringConverterFactory()).build();
        AppWithInterfaceUrlApp app = jrest.create(AppWithInterfaceUrlApp.class);
        assertion(app.PUT());
    }

    @Test
    public void PUTjrestNestedUrlTest() {
        final JRest jrest = new JRest.Builder().baseUrl("http://localhost:1337/")
                .coverterFactory(new StringConverterFactory()).build();
        AppWithFullUrl app = jrest.create(AppWithFullUrl.class);
        assertion(app.PUT());
    }

    @Mapping(url = "/v1")
    public interface AppWithFullUrl {
        @Get(url = "/url")
        String GET();

        @Post(url = "/url")
        String POST();

        @Put(url = "/url")
        String PUT();
    }

    @Mapping(url = "/v1/url")
    public interface AppWithInterfaceUrlApp {
        @Get
        String GET();

        @Post
        String POST();

        @Put
        String PUT();
    }

    public interface AppWithoutUrl {
        @Get
        String GET();

        @Post
        String POST();

        @Put
        String PUT();
    }
}
