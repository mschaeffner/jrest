package com.scarabsoft.jrest.test;

import com.scarabsoft.jrest.converter.GsonConverterFactory;
import com.scarabsoft.jrest.JRest;
import com.scarabsoft.jrest.annotation.Get;
import com.scarabsoft.jrest.annotation.Mapping;
import com.scarabsoft.jrest.annotation.Post;
import com.scarabsoft.jrest.annotation.Put;
import com.scarabsoft.jrest.test.domain.IP;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = JRestTestApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:1337")
public class CollectionConverterTests {

    private void test(List<IP> ips) {
        Assert.assertThat(ips.get(0).getIp(), Matchers.is("127.0.0.1"));
        Assert.assertThat(ips.get(1).getIp(), Matchers.is("128.0.0.1"));
        Assert.assertThat(ips.get(2).getIp(), Matchers.is("129.0.0.1"));
        Assert.assertThat(ips.size(), Matchers.is(3));
    }

    @Test
    public void GETConverterInApplicationTest() {
        final JRest jrest = new JRest.Builder().build();
        final ApplicationWithConverterFactory app = jrest.create(ApplicationWithConverterFactory.class);
        test(new ArrayList<>(app.GET()));
    }

    @Test
    public void POSTConverterInApplicationTest() {
        final JRest jrest = new JRest.Builder().build();
        final ApplicationWithConverterFactory app = jrest.create(ApplicationWithConverterFactory.class);
        test(new ArrayList<>(app.POST()));
    }

    @Test
    public void PUTConverterInApplicationTest() {
        final JRest jrest = new JRest.Builder().build();
        final ApplicationWithConverterFactory app = jrest.create(ApplicationWithConverterFactory.class);
        List<IP> list = new ArrayList<>(app.PUT());
        Collections.sort(list, (o1, o2) -> o1.getIp().compareTo(o2.getIp()));
        test(list);
    }

    @Mapping(url = "http://localhost:1337/v1/collection/ip", converterFactory = GsonConverterFactory.class)
    interface ApplicationWithConverterFactory {

        @Get
        List<IP> GET();

        @Post
        Collection<IP> POST();

        @Put
        Set<IP> PUT();

    }

}
