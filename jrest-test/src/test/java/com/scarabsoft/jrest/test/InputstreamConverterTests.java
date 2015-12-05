package com.scarabsoft.jrest.test;

import com.scarabsoft.jrest.JRest;
import com.scarabsoft.jrest.annotation.*;
import com.scarabsoft.jrest.converter.GsonConverterFactory;
import com.scarabsoft.jrest.util.IOUtils;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.IOException;
import java.io.InputStream;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = JRestTestApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:1337")
public class InputstreamConverterTests {

    private App app;

    @Before
    public void before() {
        final JRest jrest = new JRest.Builder().build();
        app = jrest.create(App.class);
    }

    @Test
    public void GetTest() throws IOException {
        assertion(app.GET());
    }

    @Test
    public void DeleteTest() throws IOException {
        assertion(app.DELETE());
    }


    @Test
    public void PostTest() throws IOException {
        assertion(app.POST());
    }

    @Test
    public void PutTest() throws IOException {
        assertion(app.PUT());
    }

    private void assertion(InputStream stream) throws IOException {
        final byte[] data = IOUtils.convert(stream);
        Assert.assertThat(data.length, Matchers.is(10));
        for (int i = 0; i < 10; i++) {
            Assert.assertThat(data[i], Matchers.is((byte) 1));
        }
    }


    @Mapping(url = "http://localhost:1337/v1/download", converterFactory = GsonConverterFactory.class)
    interface App {
        @Get
        InputStream GET();

        @Post
        InputStream POST();

        @Put
        InputStream PUT();

        @Delete
        InputStream DELETE();

    }
}
