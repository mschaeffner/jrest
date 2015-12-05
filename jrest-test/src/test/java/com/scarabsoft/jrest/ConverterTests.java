package com.scarabsoft.jrest;

import com.scarabsoft.jrest.annotation.*;
import com.scarabsoft.jrest.converter.GsonConverterFactory;
import com.scarabsoft.jrest.domain.IP;
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

    @Test
    public void GETConverterJRestTest() {
        final JRest jrest2 = new JRest.Builder().coverterFactory(new GsonConverterFactory()).build();
        final ApplicationWithConverterFactory app = jrest2.create(ApplicationWithConverterFactory.class);
        assertion(app.GET());
    }

    @Test
    public void POSTConverterJRestTest() {
        final JRest jrest2 = new JRest.Builder().coverterFactory(new GsonConverterFactory()).build();
        final ApplicationWithConverterFactory app = jrest2.create(ApplicationWithConverterFactory.class);
        assertion(app.POST());
    }

    @Test
    public void PUTTConverterJRestTest() {
        final JRest jrest2 = new JRest.Builder().coverterFactory(new GsonConverterFactory()).build();
        final ApplicationWithConverterFactory app = jrest2.create(ApplicationWithConverterFactory.class);
        assertion(app.PUT());
    }

    @Test
    public void DELETEConverterJRestTest() {
        final JRest jrest2 = new JRest.Builder().coverterFactory(new GsonConverterFactory()).build();
        final ApplicationWithConverterFactory app = jrest2.create(ApplicationWithConverterFactory.class);
        assertion(app.DELETE());
    }

    @Test
    public void GETConverterInterfaceTest() {
        final JRest jrest2 = new JRest.Builder().build();
        final ApplicationWithConverterFactory app = jrest2.create(ApplicationWithConverterFactory.class);
        assertion(app.GET());
    }

    @Test
    public void POSTConverterInterfaceTest() {
        final JRest jrest2 = new JRest.Builder().build();
        final ApplicationWithConverterFactory app = jrest2.create(ApplicationWithConverterFactory.class);
        assertion(app.POST());
    }

    @Test
    public void PUTConverterInterfaceTest() {
        final JRest jrest2 = new JRest.Builder().build();
        final ApplicationWithConverterFactory app = jrest2.create(ApplicationWithConverterFactory.class);
        assertion(app.PUT());
    }

    @Test
    public void DELETEConverterInterfaceTest() {
        final JRest jrest2 = new JRest.Builder().build();
        final ApplicationWithConverterFactory app = jrest2.create(ApplicationWithConverterFactory.class);
        assertion(app.DELETE());
    }

    @Test(expected = RuntimeException.class)
    public void POSTmissingConverterTest() {
        final JRest jrest = new JRest.Builder().build();
        final SimpleApplication missingApp = jrest.create(SimpleApplication.class);
        missingApp.POST();
    }

    @Test(expected = RuntimeException.class)
    public void GETmissingConverterTest() {
        final JRest jrest = new JRest.Builder().build();
        final SimpleApplication missingApp = jrest.create(SimpleApplication.class);
        missingApp.GET();
    }

    @Test(expected = RuntimeException.class)
    public void PUTmissingConverterTest() {
        final JRest jrest = new JRest.Builder().build();
        final SimpleApplication missingApp = jrest.create(SimpleApplication.class);
        missingApp.PUT();
    }

    @Test(expected = RuntimeException.class)
    public void DELETEmissingConverterTest() {
        final JRest jrest = new JRest.Builder().build();
        final SimpleApplication missingApp = jrest.create(SimpleApplication.class);
        missingApp.DELETE();
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

        @Delete
        IP DELETE();
    }

    @Mapping(url = "http://localhost:1337/v1/simple/ip", converterFactory = GsonConverterFactory.class)
    interface ApplicationWithConverterFactory {
        @Get
        IP GET();

        @Post
        IP POST();

        @Put
        IP PUT();

        @Delete
        IP DELETE();

    }
}
