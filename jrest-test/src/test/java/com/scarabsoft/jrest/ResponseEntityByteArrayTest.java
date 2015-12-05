package com.scarabsoft.jrest;

import com.scarabsoft.jrest.annotation.Delete;
import com.scarabsoft.jrest.annotation.Get;
import com.scarabsoft.jrest.annotation.Post;
import com.scarabsoft.jrest.annotation.Put;
import com.scarabsoft.jrest.converter.GsonConverterFactory;
import com.scarabsoft.jrest.converter.exception.StringExceptionConverterFactory;
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
public class ResponseEntityByteArrayTest {

    private Application app;

    @Before
    public void before() {
        final JRest jrest = new JRest.Builder()
                .baseUrl("http://localhost:1337/v1/download")
                .coverterFactory(new GsonConverterFactory())
                .exceptionFactory(new StringExceptionConverterFactory())
                .build();
        app = jrest.create(Application.class);
    }

    @Test
    public void GETTest() {
        assertion(app.GET());
    }

    @Test
    public void POSTTest() {
        assertion(app.POST());
    }

    @Test
    public void PUTTest() {
        assertion(app.PUT());
    }

    @Test
    public void DELETETest() {
        assertion(app.DELETE());
    }

    @Test
    public void InputStreamGETTest() {
        assertionStream(app.iGET());
    }

    @Test
    public void InputStreamPOSTTest() {
        assertionStream(app.iPOST());
    }

    @Test
    public void InputStreamPUTTest() {
        assertionStream(app.iPUT());
    }

    @Test
    public void InputStreamDELETETest() {
        assertionStream(app.iDELETE());
    }

    private void assertion(ResponseEntity<byte[]> res) {
        Assert.assertThat(res.getStatusCode(), Matchers.is(200));
        Assert.assertThat(res.getObject().length, Matchers.is(10));
        for (int i = 0; i < 10; i++) {
            Assert.assertThat(res.getObject()[i], Matchers.is((byte) 1));
        }
    }

    private void assertionStream(ResponseEntity<InputStream> res) {
        byte[] data = new byte[0];
        try {
            data = IOUtils.convert(res.getObject());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assert.assertThat(res.getStatusCode(), Matchers.is(200));
        Assert.assertThat(data.length, Matchers.is(10));
        for (int i = 0; i < 10; i++) {
            Assert.assertThat(data[i], Matchers.is((byte) 1));
        }
    }


    interface Application {

        @Get
        ResponseEntity<byte[]> GET();

        @Post
        ResponseEntity<byte[]> POST();

        @Put
        ResponseEntity<byte[]> PUT();

        @Delete
        ResponseEntity<byte[]> DELETE();

        @Get
        ResponseEntity<InputStream> iGET();

        @Post
        ResponseEntity<InputStream> iPOST();

        @Put
        ResponseEntity<InputStream> iPUT();

        @Delete
        ResponseEntity<InputStream> iDELETE();
    }

}
