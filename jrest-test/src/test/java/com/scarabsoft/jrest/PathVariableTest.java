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
public class PathVariableTest {

    private Application app;

    @Before
    public void before() {
        final JRest jrest = new JRest.Builder().build();
        app = jrest.create(Application.class);
    }

    @Test
    public void GETTest() {
        assertion(app.GET(1, 2), app.GETWithout());
    }

    @Test
    public void POSTTest() {
        assertion(app.POST(1, 2), app.POSTWithout());
    }

    @Test
    public void PUTTest() {
        assertion(app.PUT(1, 2), app.PUTWithout());
    }

    @Test
    public void DELETETest() {
        assertion(app.DELETE(1, 2), app.DELETEWithout());
    }

    private void assertion(UserGroup ug1, UserGroup ug2) {
        Assert.assertThat(ug1.getUserId(), Matchers.is(1));
        Assert.assertThat(ug1.getGroupId(), Matchers.is(2));

        Assert.assertThat(ug2.getUserId(), Matchers.is(4));
        Assert.assertThat(ug2.getGroupId(), Matchers.is(5));
    }

    @Mapping(url = "http://localhost:1337/v1", converterFactory = GsonConverterFactory.class)
    static interface Application {

        @Get(url = "/path/{userId}/{groupId}")
        UserGroup GET(@Path(name = "userId") int userId, @Path(name = "groupId") int groupId);

        @Get(url = "/path/4/5")
        UserGroup GETWithout();

        @Post(url = "/path/{userId}/{groupId}")
        UserGroup POST(@Path(name = "userId") int userId, @Path(name = "groupId") int groupId);

        @Post(url = "/path/4/5")
        UserGroup POSTWithout();

        @Put(url = "/path/{userId}/{groupId}")
        UserGroup PUT(@Path(name = "userId") int userId, @Path(name = "groupId") int groupId);

        @Put(url = "/path/4/5")
        UserGroup PUTWithout();

        @Delete(url = "/path/{userId}/{groupId}")
        UserGroup DELETE(@Path(name = "userId") int userId, @Path(name = "groupId") int groupId);

        @Delete(url = "/path/4/5")
        UserGroup DELETEWithout();

    }

}
