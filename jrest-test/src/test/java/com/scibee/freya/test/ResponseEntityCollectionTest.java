package com.scibee.freya.test;

import com.scibee.freya.Freya;
import com.scibee.freya.annotation.Get;
import com.scibee.freya.annotation.Mapping;
import com.scibee.freya.annotation.Post;
import com.scibee.freya.annotation.Put;
import com.scibee.freya.converter.GsonConverterFactory;
import com.scibee.freya.interceptor.ResponseEntity;
import com.scibee.freya.test.domain.IP;
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
@SpringApplicationConfiguration(classes = FreyaTestApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:1337")
public class ResponseEntityCollectionTest {

    private void test(List<IP> ips) {
        Assert.assertThat(ips.get(0).getIp(), Matchers.is("127.0.0.1"));
        Assert.assertThat(ips.size(), Matchers.is(3));
    }

    @Test
    public void GETConverterInApplicationTest() {
        final Freya freya = new Freya.Builder().build();
        final ApplicationWithConverterFactory app = freya.create(ApplicationWithConverterFactory.class);
        test(new ArrayList<>(app.GET().getObject()));
    }

    @Test
    public void POSTConverterInApplicationTest() {
        final Freya freya = new Freya.Builder().build();
        final ApplicationWithConverterFactory app =
                freya.create(ApplicationWithConverterFactory.class);
        test(new ArrayList<>(app.POST().getObject()));
    }

    @Test
    public void PUTConverterInApplicationTest() {
        final Freya freya = new Freya.Builder().build();
        final ApplicationWithConverterFactory app =
                freya.create(ApplicationWithConverterFactory.class);
        List<IP> list = new ArrayList<>(app.PUT().getObject());
        Collections.sort(list, new Comparator<IP>() {
            @Override
            public int compare(IP o1, IP o2) {
                return o1.getIp().compareTo(o2.getIp());
            }
        });
        test(list);
    }

    @Mapping(url = "http://localhost:1337/v1/collection/ip", converterFactory = GsonConverterFactory.class)
    interface ApplicationWithConverterFactory {

        @Get
        ResponseEntity<List<IP>> GET();

        @Post
        ResponseEntity<Collection<IP>> POST();

        @Put
        ResponseEntity<Set<IP>> PUT();

    }

}
