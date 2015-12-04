package com.scarabsoft.jrest.test;

import com.scarabsoft.jrest.JRest;
import com.scarabsoft.jrest.annotation.Get;
import com.scarabsoft.jrest.annotation.Mapping;
import com.scarabsoft.jrest.test.domain.IP;
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
public class NoInterfaceConstructionTest {

    @Test(expected = RuntimeException.class)
    public void GETclassImplementationTestTest() {
        final JRest jrest = new JRest.Builder().build();
        final SimpleApplication app = jrest.create(SimpleApplication.class);
        app.GET();
    }

    @Test(expected = RuntimeException.class)
    public void GETabstractClassImplementationTest() {
        final JRest jrest = new JRest.Builder().build();
        final AbstractSimpleApplication app = jrest.create(AbstractSimpleApplication.class);
        app.GET();
    }

    @Mapping(url = "http://localhost:1337/v1/simple/ip")
    class SimpleApplication {
        @Get
        IP GET() {
            return null;
        }
    }

    @Mapping(url = "http://localhost:1337/v1/simple/ip")
    abstract class AbstractSimpleApplication {
        @Get
        abstract IP GET();

    }

}
