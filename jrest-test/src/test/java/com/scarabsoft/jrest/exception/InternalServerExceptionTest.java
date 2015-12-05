package com.scarabsoft.jrest.exception;

import com.scarabsoft.jrest.JRest;
import com.scarabsoft.jrest.annotation.*;
import com.scarabsoft.jrest.converter.StringConverterFactory;
import com.scarabsoft.jrest.JRestTestApplication;
import com.scarabsoft.jrest.converter.SpringDefaultExceptionConverterFactory;
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
public class InternalServerExceptionTest {

    private ExceptionAppViaInterface internal;
    private ExceptionAppViaNested nested;

    @Before
    public void before() {
        final JRest jrest = new JRest.Builder().build();
        internal = jrest.create(ExceptionAppViaInterface.class);

        final JRest jrest2 = new JRest.Builder().exceptionFactory(new SpringDefaultExceptionConverterFactory()).build();
        nested = jrest2.create(ExceptionAppViaNested.class);
    }

    @Test
    public void GETinternalServerExceptionTest() {
        try {
            internal.GET();
        } catch (SpringDefaultException e) {
            assertion(e);
        }
    }


    @Test
    public void POSTinternalServerExceptionTest() {
        try {
            internal.POST();
        } catch (SpringDefaultException e) {
            assertion(e);
        }
    }

    @Test
    public void PUTinternalServerExceptionTest() {
        try {
            internal.PUT();
        } catch (SpringDefaultException e) {
            assertion(e);
        }
    }

    @Test
    public void DELETEinternalServerExceptionTest() {
        try {
            internal.DELETE();
        } catch (SpringDefaultException e) {
            assertion(e);
        }
    }


    @Test
    public void GETNestedServerExceptionTest() {
        try {
            nested.GET();
        } catch (SpringDefaultException e) {
            assertion(e);
        }
    }


    @Test
    public void POSTNestedServerExceptionTest() {
        try {
            nested.POST();
        } catch (SpringDefaultException e) {
            assertion(e);
        }
    }

    @Test
    public void PUTNestedServerExceptionTest() {
        try {
            nested.PUT();
        } catch (SpringDefaultException e) {
            assertion(e);
        }
    }

    @Test
    public void DELETENestedServerExceptionTest() {
        try {
            nested.DELETE();
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


    @Mapping(url = "http://localhost:1337/v1/error/500", converterFactory = StringConverterFactory.class, exceptionFactory = SpringDefaultExceptionConverterFactory.class)
    interface ExceptionAppViaInterface {
        @Get
        void GET();

        @Post
        void POST();

        @Put
        void PUT();

        @Delete
        void DELETE();

    }

    @Mapping(url = "http://localhost:1337/v1/error/500", converterFactory = StringConverterFactory.class)
    interface ExceptionAppViaNested {
        @Get
        void GET();

        @Post
        void POST();

        @Put
        void PUT();

        @Delete
        void DELETE();
    }

}
