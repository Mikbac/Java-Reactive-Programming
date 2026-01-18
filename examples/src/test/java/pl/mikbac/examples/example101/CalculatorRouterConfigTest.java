package pl.mikbac.examples.example101;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webtestclient.autoconfigure.AutoConfigureWebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Created by MikBac on 18.01.2026
 */

@AutoConfigureWebTestClient
@SpringBootTest
class CalculatorRouterConfigTest {

    @Autowired
    private WebTestClient client;

    @Test
    public void shouldAdd() {
        this.client.get()
                .uri("/calculator/v1/{a}/{b}", 2, 3)
                .header("X-Operation", "+")
                .exchange()
                .expectStatus().isEqualTo(200)
                .expectBody(String.class)
                .value(s -> {
                    Assertions.assertNotNull(s);
                    Assertions.assertEquals("5", s);
                });
    }

    @Test
    public void shouldSubtract() {
        this.client.get()
                .uri("/calculator/v1/{a}/{b}", 7, 4)
                .header("X-Operation", "-")
                .exchange()
                .expectStatus().isEqualTo(200)
                .expectBody(String.class)
                .value(s -> {
                    Assertions.assertNotNull(s);
                    Assertions.assertEquals("3", s);
                });
    }

    @Test
    public void shouldMultiply() {
        this.client.get()
                .uri("/calculator/v1/{a}/{b}", 4, 5)
                .header("X-Operation", "*")
                .exchange()
                .expectStatus().isEqualTo(200)
                .expectBody(String.class)
                .value(s -> {
                    Assertions.assertNotNull(s);
                    Assertions.assertEquals("20", s);
                });
    }

    @Test
    public void shouldDivide() {
        this.client.get()
                .uri("/calculator/v1/{a}/{b}", 20, 4)
                .header("X-Operation", "/")
                .exchange()
                .expectStatus().isEqualTo(200)
                .expectBody(String.class)
                .value(s -> {
                    Assertions.assertNotNull(s);
                    Assertions.assertEquals("5", s);
                });
    }

    @Test
    public void shouldDivideByZero() {
        this.client.get()
                .uri("/calculator/v1/{a}/0", 10)
                .header("X-Operation", "/")
                .exchange()
                .expectStatus().isEqualTo(400)
                .expectBody(String.class)
                .value(s -> {
                    Assertions.assertNotNull(s);
                    Assertions.assertEquals("Param b cannot be 0", s);
                });
    }

}
