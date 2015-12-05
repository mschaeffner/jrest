package com.scarabsoft.jrest;

import com.scarabsoft.jrest.annotation.*;
import com.scarabsoft.jrest.converter.GsonConverterFactory;
import com.scarabsoft.jrest.domain.IP;
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
public class BodyTest {

    private App app;

    @Before
    public void before() {
        final JRest jrest = new JRest.Builder().build();
        app = jrest.create(App.class);
    }

    @Test(expected = RuntimeException.class)
    public void GetTest() {
        final IP ip = new IP.Builder().ip("127.0.0.2").requestDate(System.currentTimeMillis()).getCountry("de").build();
        app.GET(ip);
    }

    @Test(expected = RuntimeException.class)
    public void DeleteTest() {
        final IP ip = new IP.Builder().ip("127.0.0.2").requestDate(System.currentTimeMillis()).getCountry("de").build();
        app.DELETE(ip);
    }


    @Test
    public void PostTest() {
        final IP ip = new IP.Builder().ip("127.0.0.2").requestDate(System.currentTimeMillis()).getCountry("de").build();
        IP result = app.POST(ip);

        Assert.assertThat(result.getIp(), Matchers.is("127.0.0.2"));
        Assert.assertThat(result.getRequestDate(), Matchers.is(ip.getRequestDate()));
        Assert.assertThat(result.getCountry(), Matchers.is("de"));

    }

    @Test
    public void PutTest() {
        final IP ip = new IP.Builder().ip("127.0.0.2").requestDate(System.currentTimeMillis()).getCountry("de").build();
        app.PUT(ip);
    }

    @Mapping(url = "http://localhost:1337/v1/body", converterFactory = GsonConverterFactory.class)
    interface App {
        @Get
        IP GET(@Body IP ip);

        @Post
        IP POST(@Body IP ip);

        @Put
        void PUT(@Body IP ip);

        @Delete
        IP DELETE(@Body IP ip);

    }
}
