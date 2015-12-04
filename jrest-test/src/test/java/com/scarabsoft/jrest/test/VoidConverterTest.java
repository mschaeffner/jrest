package com.scarabsoft.jrest.test;

import com.scarabsoft.jrest.JRest;
import com.scarabsoft.jrest.annotation.Get;
import com.scarabsoft.jrest.annotation.Mapping;
import com.scarabsoft.jrest.annotation.Post;
import com.scarabsoft.jrest.annotation.Put;
import com.scarabsoft.jrest.converter.GsonConverterFactory;
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
public class VoidConverterTest {

    @Mapping(url = "http://localhost:1337/v1/simple/ip", converterFactory = GsonConverterFactory.class)
    interface VoidInterface {

        @Get
        void GET();

        @Get
        Void VGET();

        @Post
        void POST();

        @Post
        Void VPOST();

        @Put
        void PUT();

        @Put
        Void VPUT();

    }

    @Test
    public void voidTest() {
        final JRest jrest = new JRest.Builder().build();
        VoidInterface app = jrest.create(VoidInterface.class);
        app.GET();
        app.VGET();
        app.POST();
        app.VPOST();
        app.PUT();
        app.VPUT();
    }


}
