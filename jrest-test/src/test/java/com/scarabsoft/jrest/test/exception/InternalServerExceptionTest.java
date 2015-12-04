package com.scarabsoft.jrest.test.exception;

import com.scarabsoft.jrest.JRest;
import com.scarabsoft.jrest.annotation.Get;
import com.scarabsoft.jrest.annotation.Mapping;
import com.scarabsoft.jrest.converter.StringConverterFactory;
import com.scarabsoft.jrest.test.JRestTestApplication;
import com.scarabsoft.jrest.test.converter.SimpleExceptionConverterFactory;
import com.scarabsoft.jrest.test.converter.SpringDefaultExceptionConverterFactory;
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
public class InternalServerExceptionTest {

    @Test
    public void internalServerExceptionTest() {
        final JRest jrest = new JRest.Builder().build();
        final ExceptionAppViaInterface app = jrest.create(ExceptionAppViaInterface.class);
        try {
            app.getException();
        } catch (SpringDefaultException e) {
            assertion(e);
        }
    }

    @Test
    public void internalServerNestedExceptionTest() {
        final JRest jrest = new JRest.Builder().exceptionFactory(new SimpleExceptionConverterFactory()).build();
        final ExceptionAppViaInterface app = jrest.create(ExceptionAppViaInterface.class);
        try {
            app.getException();
        } catch (SpringDefaultException e) {
            assertion(e);
        }
    }

    private void assertion(SpringDefaultException e) {
        Assert.assertThat(e.getPath(), Matchers.is("/v1/error/500"));
        Assert.assertThat(e.getStatus(), Matchers.is(500));
        Assert.assertThat(e.getError(), Matchers.is("Internal Server Error"));
        Assert.assertThat(e.getException(), Matchers.is("java.lang.RuntimeException"));
        Assert.assertThat(e.getMessage(), Matchers.is("test"));
    }

    @Test
    public void internalServerExceptionjrestTest() {
        final JRest jrest = new JRest.Builder().exceptionFactory(new SimpleExceptionConverterFactory()).build();
        final ExceptionAppViajrest app = jrest.create(ExceptionAppViajrest.class);
        try {
            app.getException();
        } catch (SimpleException e) {
            Assert.assertThat(e.getPath(), Matchers.is("/v1/error/500"));
            Assert.assertThat(e.getMessage(), Matchers.is("test"));
        }
    }

    @Mapping(url = "http://localhost:1337/v1/error/500", converterFactory = StringConverterFactory.class, exceptionFactory = SpringDefaultExceptionConverterFactory.class)
    interface ExceptionAppViaInterface {
        @Get
        String getException();
    }

    @Mapping(url = "http://localhost:1337/v1/error/500", converterFactory = StringConverterFactory.class)
    interface ExceptionAppViajrest {
        @Get
        String getException();
    }

}
