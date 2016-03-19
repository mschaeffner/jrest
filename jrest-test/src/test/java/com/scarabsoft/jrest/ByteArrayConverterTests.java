package com.scarabsoft.jrest;

import com.scarabsoft.jrest.annotation.*;
import com.scarabsoft.jrest.converter.GsonConverterFactory;
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
public class ByteArrayConverterTests {

    private App app;

    @Before
    public void before() {
        final JRest jrest = new JRest.Builder().build();
        app = jrest.create(App.class);
    }

    @Test
    public void GetTest() {
        assertion(app.GET());
    }

    @Test
    public void DeleteTest() {
        assertion(app.DELETE());
    }


    @Test
    public void PostTest() {
        assertion(app.POST());
    }

    @Test
    public void PutTest() {
        assertion(app.PUT());
    }

    private void assertion(byte[] data) {
        Assert.assertThat(data.length, Matchers.is(10));
        for (int i = 0; i < 10; i++) {
            Assert.assertThat(data[i], Matchers.is((byte) 1));
        }
    }

    @Mapping(value = "http://localhost:1337/v1/download", converterFactory = GsonConverterFactory.class)
    interface App {
        @Get
        byte[] GET();

        @Post
        byte[] POST();

        @Put
        byte[] PUT();

        @Delete
        byte[] DELETE();

    }
}
