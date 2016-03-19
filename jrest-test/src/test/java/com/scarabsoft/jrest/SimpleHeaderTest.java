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
public class SimpleHeaderTest {

    private JRest jrest;

    @Before
    public void before() {
        jrest = new JRest.Builder().build();
    }

    @Test
    public void GETheaderAppTest() {
        HeaderApp app = jrest.create(HeaderApp.class);
        UserGroup userGroup = app.GET();
        Assert.assertThat(userGroup.getUserId(), Matchers.is(2));
        Assert.assertThat(userGroup.getGroupId(), Matchers.is(4));

        userGroup = app.GET2("123", 10);
        Assert.assertThat(userGroup.getUserId(), Matchers.is(123));
        Assert.assertThat(userGroup.getGroupId(), Matchers.is(10));
    }

    @Test(expected = RuntimeException.class)
    public void GETinvalidHeaderAppTest() {
        InvalidHeaderApp app = jrest.create(InvalidHeaderApp.class);
        app.GET();
    }

    @Test(expected = RuntimeException.class)
    public void GETanotherInvalidHeaderAppTest() {
        InvalidHeaderApp app = jrest.create(InvalidHeaderApp.class);
        app.GET2("");
    }

    @Test
    public void GETannotatedInterfaceAppTest() {
        AnnotatedInterfaceApp app = jrest.create(AnnotatedInterfaceApp.class);
        UserGroup userGroup = app.GET();

        Assert.assertThat(userGroup.getUserId(), Matchers.is(2));
        Assert.assertThat(userGroup.getGroupId(), Matchers.is(4));
    }

    @Test(expected = RuntimeException.class)
    public void GETinvalidAnnotatedInterfaceAppTest() {
        InvalidAnnotatedInterfaceApp app = jrest.create(InvalidAnnotatedInterfaceApp.class);
        app.GET();
    }

    @Test
    public void POSTheaderAppTest() {
        HeaderApp app = jrest.create(HeaderApp.class);
        UserGroup userGroup = app.POST();
        Assert.assertThat(userGroup.getUserId(), Matchers.is(2));
        Assert.assertThat(userGroup.getGroupId(), Matchers.is(4));

        userGroup = app.POST2("123", 10);
        Assert.assertThat(userGroup.getUserId(), Matchers.is(123));
        Assert.assertThat(userGroup.getGroupId(), Matchers.is(10));
    }

    @Test(expected = RuntimeException.class)
    public void POSTinvalidHeaderAppTest() {
        InvalidHeaderApp app = jrest.create(InvalidHeaderApp.class);
        app.POST();
    }

    @Test(expected = RuntimeException.class)
    public void POSTanotherInvalidHeaderAppTest() {
        InvalidHeaderApp app = jrest.create(InvalidHeaderApp.class);
        app.POST2("");
    }

    @Test
    public void POSTannotatedInterfaceAppTest() {
        AnnotatedInterfaceApp app = jrest.create(AnnotatedInterfaceApp.class);
        UserGroup userGroup = app.POST();

        Assert.assertThat(userGroup.getUserId(), Matchers.is(2));
        Assert.assertThat(userGroup.getGroupId(), Matchers.is(4));
    }

    @Test(expected = RuntimeException.class)
    public void POSTinvalidAnnotatedInterfaceAppTest() {
        InvalidAnnotatedInterfaceApp app = jrest.create(InvalidAnnotatedInterfaceApp.class);
        app.POST();
    }

    @Test
    public void PUTheaderAppTest() {
        HeaderApp app = jrest.create(HeaderApp.class);
        UserGroup userGroup = app.PUT();
        Assert.assertThat(userGroup.getUserId(), Matchers.is(2));
        Assert.assertThat(userGroup.getGroupId(), Matchers.is(4));

        userGroup = app.POST2("123", 10);
        Assert.assertThat(userGroup.getUserId(), Matchers.is(123));
        Assert.assertThat(userGroup.getGroupId(), Matchers.is(10));
    }

    @Test(expected = RuntimeException.class)
    public void PUTinvalidHeaderAppTest() {
        InvalidHeaderApp app = jrest.create(InvalidHeaderApp.class);
        app.PUT();
    }

    @Test(expected = RuntimeException.class)
    public void PUTanotherInvalidHeaderAppTest() {
        InvalidHeaderApp app = jrest.create(InvalidHeaderApp.class);
        app.PUT2("");
    }

    @Test
    public void PUTannotatedInterfaceAppTest() {
        AnnotatedInterfaceApp app = jrest.create(AnnotatedInterfaceApp.class);
        UserGroup userGroup = app.PUT();

        Assert.assertThat(userGroup.getUserId(), Matchers.is(2));
        Assert.assertThat(userGroup.getGroupId(), Matchers.is(4));
    }

    @Test(expected = RuntimeException.class)
    public void PUTinvalidAnnotatedInterfaceAppTest() {
        InvalidAnnotatedInterfaceApp app = jrest.create(InvalidAnnotatedInterfaceApp.class);
        app.PUT();
    }

    @Mapping(value = "http://localhost:1337/v1/header/simple", converterFactory = GsonConverterFactory.class)
    interface HeaderApp {


        @Headers(value = {
                @Header(key = "userId", value = "2"),
                @Header(key = "groupId", value = "4")
        })
        @Get
        UserGroup GET();

        @Get
        UserGroup GET2(@Header(key = "userId") String userId, @Header(key = "groupId") int groupId);

        @Headers({
                @Header(key = "userId", value = "2"),
                @Header(key = "groupId", value = "4")
        })
        @Post
        UserGroup POST();

        @Put
        UserGroup POST2(@Header(key = "userId") String userId, @Header(key = "groupId") int groupId);

        @Headers({
                @Header(key = "userId", value = "2"),
                @Header(key = "groupId", value = "4")
        })
        @Put
        UserGroup PUT();

        @Put
        UserGroup PUT2(@Header(key = "userId") String userId, @Header(key = "groupId") int groupId);

    }

    @Mapping(value = "http://localhost:1337/v1/header/simple", converterFactory = GsonConverterFactory.class)
    interface InvalidHeaderApp {

        @Headers({
                @Header(key = "userId", value = "2"),
                @Header(key = "groupId")
        })
        @Get
        UserGroup GET();

        @Header(key = "userId", value = "2")
        @Get
        UserGroup GET2(@Header(key = "groupId") String groupId);

        @Headers({
                @Header(key = "userId", value = "2"),
                @Header(key = "groupId")
        })
        @Post
        UserGroup POST();

        @Header(key = "userId", value = "2")
        @Post
        UserGroup POST2(@Header(key = "groupId") String groupId);

        @Headers({
                @Header(key = "userId", value = "2"),
                @Header(key = "groupId")
        })
        @Put
        UserGroup PUT();

        @Header(key = "userId", value = "2")
        @Put
        UserGroup PUT2(@Header(key = "groupId") String groupId);

    }

    @Headers({
            @Header(key = "userId", value = "2"),
            @Header(key = "groupId", value = "4")
    })
    @Mapping(value = "http://localhost:1337/v1/header/simple", converterFactory = GsonConverterFactory.class)
    interface AnnotatedInterfaceApp {

        @Get
        UserGroup GET();

        @Post
        UserGroup POST();

        @Put
        UserGroup PUT();

    }

    @Headers({
            @Header(key = "userId", value = "2"),
            @Header(key = "groupId")
    })
    @Mapping(value = "http://localhost:1337/v1/header/simple", converterFactory = GsonConverterFactory.class)
    interface InvalidAnnotatedInterfaceApp {

        @Get
        UserGroup GET();

        @Post
        UserGroup POST();

        @Put
        UserGroup PUT();

    }

}
