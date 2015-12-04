package com.scarabsoft.jrest.test;

import com.scarabsoft.jrest.JRest;
import com.scarabsoft.jrest.annotation.Get;
import com.scarabsoft.jrest.annotation.Mapping;
import com.scarabsoft.jrest.annotation.Post;
import com.scarabsoft.jrest.annotation.Put;
import com.scarabsoft.jrest.converter.GsonConverterFactory;
import com.scarabsoft.jrest.test.domain.IP;
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
public class ConverterTests {

    @Test(expected = RuntimeException.class)
    public void GETmissingConverterTest() {
        final JRest jrest = new JRest.Builder().build();
        final SimpleApplication app = jrest.create(SimpleApplication.class);
        app.GET();
    }

    @Test
    public void GETConverterInjrestTest() {
        final JRest jrest = new JRest.Builder().coverterFactory(new GsonConverterFactory()).build();
        final SimpleApplication app = jrest.create(SimpleApplication.class);
        assertion(app.GET());
    }

    @Test
    public void GETConverterInApplicationTest() {
        final JRest jrest = new JRest.Builder().build();
        final ApplicationWithConverterFactory app = jrest.create(ApplicationWithConverterFactory.class);
        assertion(app.GET());
    }

    @Test(expected = RuntimeException.class)
    public void POSTmissingConverterTest() {
        final JRest jrest = new JRest.Builder().build();
        final SimpleApplication app = jrest.create(SimpleApplication.class);
        app.POST();
    }

    @Test
    public void POSTConverterInjrestTest() {
        final JRest jrest = new JRest.Builder().coverterFactory(new GsonConverterFactory()).build();
        final SimpleApplication app = jrest.create(SimpleApplication.class);
        assertion(app.POST());
    }

    @Test
    public void POSTConverterInApplicationTest() {
        final JRest jrest = new JRest.Builder().build();
        final ApplicationWithConverterFactory app = jrest.create(ApplicationWithConverterFactory.class);
        assertion(app.POST());
    }

    @Test(expected = RuntimeException.class)
    public void PUTmissingConverterTest() {
        final JRest jrest = new JRest.Builder().build();
        final SimpleApplication app = jrest.create(SimpleApplication.class);
        app.PUT();
    }

    @Test
    public void PUTConverterInjrestTest() {
        final JRest jrest = new JRest.Builder().coverterFactory(new GsonConverterFactory()).build();
        final SimpleApplication app = jrest.create(SimpleApplication.class);
        assertion(app.PUT());
    }

    @Test
    public void PUTConverterInApplicationTest() {
        final JRest jrest = new JRest.Builder().build();
        final ApplicationWithConverterFactory app = jrest.create(ApplicationWithConverterFactory.class);
        assertion(app.PUT());

    }

    private void assertion(IP ip) {
        Assert.assertThat(ip.getCountry(), Matchers.is("DE"));
        Assert.assertThat(ip.getIp(), Matchers.is("127.0.0.1"));
    }

    @Mapping(url = "http://localhost:1337/v1/simple/ip")
    interface SimpleApplication {
        @Get
        IP GET();

        @Post
        IP POST();

        @Put
        IP PUT();
    }

    @Mapping(url = "http://localhost:1337/v1/simple/ip", converterFactory = GsonConverterFactory.class)
    interface ApplicationWithConverterFactory {
        @Get
        IP GET();

        @Post
        IP POST();

        @Put
        IP PUT();

    }
}
