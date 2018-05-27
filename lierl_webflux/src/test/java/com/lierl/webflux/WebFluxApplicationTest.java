package com.lierl.webflux;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * @Author: lierl
 * @Description:
 * @Date: 2018/5/26 17:03
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebFluxApplicationTest {

    @Autowired
    private WebTestClient webClient;

    @Test
    public void testWelcome(){
        this.webClient.get().uri("/").accept(MediaType.TEXT_PLAIN)
                .exchange().expectBody(String.class).isEqualTo("Hello World");
    }
}
