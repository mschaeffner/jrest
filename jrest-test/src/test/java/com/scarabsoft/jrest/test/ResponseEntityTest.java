package com.scarabsoft.jrest.test;

import com.scarabsoft.jrest.JRest;
import com.scarabsoft.jrest.annotation.*;
import com.scarabsoft.jrest.converter.GsonConverterFactory;
import com.scarabsoft.jrest.converter.exception.StringExceptionConverterFactory;
import com.scarabsoft.jrest.interceptor.ResponseEntity;
import com.scarabsoft.jrest.test.domain.UserGroup;
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
public class ResponseEntityTest {


    private Application app;

    @Before
    public void before() {
        final JRest jrest = new JRest.Builder().baseUrl("http://localhost:1337/v1/response")
                .coverterFactory(new GsonConverterFactory()).exceptionFactory(new StringExceptionConverterFactory())
                .build();
        app = jrest.create(Application.class);
    }

    @Test
    public void GETTest() {
        test(app.GET(1, 1));
    }

    @Test
    public void POSTTest() {
        test(app.POST(1, 1));
    }

    @Test
    public void PUTTest() {
        test(app.PUT(1, 1));
    }

    public void DELETETest() {
        test(app.DELETE());
    }

    private void test(ResponseEntity<UserGroup> res) {
        Assert.assertThat(res.getObject().getUserId(), Matchers.is(1));
        Assert.assertThat(res.getObject().getGroupId(), Matchers.is(1));
        Assert.assertThat(res.getHeader("userId").get(), Matchers.is("1"));
        Assert.assertThat(res.getHeader("groupId").get(), Matchers.is("1"));
        Assert.assertThat(res.getHeader("Cookie").get(), Matchers.is("cookie"));
        Assert.assertThat(res.getStatusCode(), Matchers.is(200));
    }

    interface Application {

        @Get
        ResponseEntity<UserGroup> GET(@Param(name = "userId") int userId, @Param(name = "groupId") int groupId);

        @Post
        ResponseEntity<UserGroup> POST(@Param(name = "userId") int userId, @Param(name = "groupId") int groupId);

        @Put
        ResponseEntity<UserGroup> PUT(@Param(name = "userId") int userId, @Param(name = "groupId") int groupId);

        @Delete
        ResponseEntity<UserGroup> DELETE();
    }

}
