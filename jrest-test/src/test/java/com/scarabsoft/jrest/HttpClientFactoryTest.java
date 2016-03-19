package com.scarabsoft.jrest;

import com.scarabsoft.jrest.annotation.Get;
import com.scarabsoft.jrest.annotation.Mapping;
import com.scarabsoft.jrest.converter.GsonConverterFactory;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
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
public class HttpClientFactoryTest {


    @Test
    public void UserAgentInBuilderTest() {
        final JRest jrest = new JRest
                .Builder()
                .httpClientFactory(new Factory())
                .build();

        final App app = jrest.create(App.class);
        assertion(app.GET());
    }

    @Test
    public void UserAgentInInterfaceTest() {
        final JRest jrest = new JRest
                .Builder()
                .build();

        final AnnotatedApp app = jrest.create(AnnotatedApp.class);
        assertion(app.GET());
    }

    @Test
    public void UserAgentNestedTest() {
        final JRest jrest = new JRest
                .Builder()
                .httpClientFactory(new Factory())
                .build();

        final NestedApp app = jrest.create(NestedApp.class);
        ResponseEntity<Void> responseEntity = app.GET();
        Assert.assertThat(responseEntity.getStatusCode(), Matchers.is(200));
        Assert.assertThat(responseEntity.getHeader("user-agent").get(), Matchers.is("current"));
    }

    private void assertion(ResponseEntity<Void> responseEntity) {
        Assert.assertThat(responseEntity.getStatusCode(), Matchers.is(200));
        Assert.assertThat(responseEntity.getHeader("user-agent").get(), Matchers.is("Hallo1234"));
    }

    @Mapping(value = "http://localhost:1337/v1/useragent", converterFactory = GsonConverterFactory.class)
    interface App {
        @Get
        ResponseEntity<Void> GET();

    }

    @Mapping(value = "http://localhost:1337/v1/useragent", converterFactory = GsonConverterFactory.class, httpClientFactory = Factory.class)
    interface AnnotatedApp {
        @Get
        ResponseEntity<Void> GET();
    }

    @Mapping(value = "http://localhost:1337/v1/useragent", converterFactory = GsonConverterFactory.class, httpClientFactory = CurrentFactory.class)
    interface NestedApp {
        @Get
        ResponseEntity<Void> GET();
    }

    static class Factory implements HttpClientFactory {

        @Override
        public HttpClient get() {
            return HttpClientBuilder
                    .create()
                    .setUserAgent("Hallo1234")
                    .build();
        }
    }

    static class CurrentFactory implements HttpClientFactory {

        @Override
        public HttpClient get() {
            return HttpClientBuilder
                    .create()
                    .setUserAgent("current")
                    .build();
        }
    }
}
