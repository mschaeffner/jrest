package com.scarabsoft.jrest.test;

import com.scarabsoft.jrest.HttpClientFactory;
import com.scarabsoft.jrest.JRest;
import com.scarabsoft.jrest.annotation.Get;
import com.scarabsoft.jrest.annotation.Mapping;
import com.scarabsoft.jrest.converter.GsonConverterFactory;
import com.scarabsoft.jrest.interceptor.ResponseEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
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

    private App app;

    @Before
    public void before() {
        final JRest jrest = new JRest
                .Builder()
                .httpClientFactory(new Factory())
                .build();

        app = jrest.create(App.class);
    }

    @Test
    public void UserAgentTest() {
        assertion(app.GET());
    }

    private void assertion(ResponseEntity<Void> responseEntity) {
        Assert.assertThat(responseEntity.getStatusCode(), Matchers.is(200));
        Assert.assertThat(responseEntity.getHeader("user-agent").get(), Matchers.is("Hallo1234"));
    }

    @Mapping(url = "http://localhost:1337/v1/useragent", converterFactory = GsonConverterFactory.class)
    interface App {
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
}
