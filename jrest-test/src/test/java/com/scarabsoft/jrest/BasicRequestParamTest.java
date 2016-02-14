package com.scarabsoft.jrest;

import com.scarabsoft.jrest.annotation.*;
import com.scarabsoft.jrest.converter.GsonConverterFactory;
import com.scarabsoft.jrest.domain.UserGroup;
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
public class BasicRequestParamTest {

    private Application app;

    @Before
    public void before() {
        JRest jrest = new JRest.Builder().build();
        app = jrest.create(Application.class);
    }

    private void assertion(UserGroup obj) {
        Assert.assertThat(obj.getUserId(), Matchers.is(1));
        Assert.assertThat(obj.getGroupId(), Matchers.is(2));
    }

    @Test
    public void POSTTest() {
        assertion(app.POST(1, 2));
    }

    @Mapping(url = "http://localhost:1337/v1", converterFactory = GsonConverterFactory.class)
    interface Application {

        @Post(url = "/param", multipart = false)
        UserGroup POST(@Param(name = "userId") int userId,
                       @Param(name = "groupId") int groupId);
    }
}
