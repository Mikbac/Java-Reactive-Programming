package pl.mikbac.examples.example104.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webtestclient.autoconfigure.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import pl.mikbac.examples.example104.dto.EmployeeDto;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by MikBac on 26.01.2026
 */

@AutoConfigureWebTestClient
@SpringBootTest
@Slf4j
class EmployeeControllerTest {

    @Autowired
    private WebTestClient client;


    @Test
    public void serverStreamTest() {
        this.client.get()
                .uri("employees/v4/stream")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .returnResult(EmployeeDto.class)
                .getResponseBody()
                .take(7)
                .doOnNext(e -> log.info("received: {}", e))
                .collectList()
                .as(StepVerifier::create)
                .assertNext(list -> {
                    Assertions.assertEquals(7, list.size());
                })
                .expectComplete()
                .verify();
    }

}
