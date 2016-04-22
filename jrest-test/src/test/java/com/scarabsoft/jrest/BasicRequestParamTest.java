package com.scarabsoft.jrest;

import com.scarabsoft.jrest.annotation.*;
import com.scarabsoft.jrest.converter.GsonConverterFactory;
import com.scarabsoft.jrest.converter.StringConverterFactory;
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

    private TextApplication textApplication;

    @Before
    public void before() {
        JRest jrest = new JRest.Builder().build();
        app = jrest.create(Application.class);
        textApplication = jrest.create(TextApplication.class);
    }

    private void assertion(UserGroup obj) {
        Assert.assertThat(obj.getUserId(), Matchers.is(1));
        Assert.assertThat(obj.getGroupId(), Matchers.is(2));
    }

    @Test
    public void POSTTest() {
        assertion(app.POST(1, 2));
    }

    @Test
    public void getTextTest(){
        String result = textApplication.GET("?öüäß,.-das");
        Assert.assertThat(result, Matchers.is("{\"text\":\"?öüäß,.-das\"}"));
    }

    @Test
    public void postTextTest(){
        String result = textApplication.POST("?öüäß,.-das");
        Assert.assertThat(result, Matchers.is("{\"text\":\"?öüäß,.-das\"}"));
    }

    @Test
    public void putTextTest(){
        String result = textApplication.PUT("?öüäß,.-das");
        Assert.assertThat(result, Matchers.is("{\"text\":\"?öüäß,.-das\"}"));
    }

    @Mapping(value = "http://localhost:1337/v1", converterFactory = GsonConverterFactory.class)
    interface Application {

        @Post(value = "/param", multipart = false)
        UserGroup POST(@Param("userId") int userId,
                       @Param("groupId") int groupId);
    }

    @Mapping(value = "http://localhost:1337/v1", converterFactory = StringConverterFactory.class)
    interface TextApplication{

        @Get("/param/text")
        String GET(@Param("text") String text);

        @Post("/param/text")
        String POST(@Param("text") String text);

        @Put("/param/text")
        String PUT(@Param("text") String text);
    }
}
