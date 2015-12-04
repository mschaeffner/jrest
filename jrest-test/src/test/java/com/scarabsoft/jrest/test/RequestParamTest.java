package com.scarabsoft.jrest.test;

import com.scarabsoft.jrest.JRest;
import com.scarabsoft.jrest.annotation.*;
import com.scarabsoft.jrest.converter.GsonConverterFactory;
import com.scarabsoft.jrest.test.domain.UserGroup;
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
public class RequestParamTest {

    @Test
    public void GETtest() {
        JRest jrest = new JRest.Builder().build();
        Application app = jrest.create(Application.class);
        assertion(app.GET(1, 2));

    }

    private void assertion(UserGroup obj) {
        Assert.assertThat(obj.getUserId(), Matchers.is(1));
        Assert.assertThat(obj.getGroupId(), Matchers.is(2));
    }

    @Test
    public void POSTtest() {
        JRest jrest = new JRest.Builder().build();
        Application app = jrest.create(Application.class);
        assertion(app.POST(1, 2));
    }

    @Test
    public void PUTtest() {
        JRest jrest = new JRest.Builder().build();
        Application app = jrest.create(Application.class);
        assertion(app.PUT(1, 2));
    }

    @Mapping(url = "http://localhost:1337/v1", converterFactory = GsonConverterFactory.class)
    interface Application {
        @Get(url = "/param")
        UserGroup GET(@Param(name = "userId") int userId,
                      @Param(name = "groupId") int groupId);

        @Post(url = "/param")
        UserGroup POST(@Param(name = "userId") int userId,
                       @Param(name = "groupId") int groupId);

        @Put(url = "/param")
        UserGroup PUT(@Param(name = "userId") int userId,
                      @Param(name = "groupId") int groupId);
    }
}
