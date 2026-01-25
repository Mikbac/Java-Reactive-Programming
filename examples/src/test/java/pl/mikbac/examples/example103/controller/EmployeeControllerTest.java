package pl.mikbac.examples.example103.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import pl.mikbac.examples.example103.dto.EmployeeDto;
import pl.mikbac.examples.example103.dto.UploadResponse;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/**
 * Created by MikBac on 22.01.2026
 */

@Slf4j
class EmployeeControllerTest {

    private final WebClient client = WebClient.builder()
            .baseUrl("http://localhost:8080")
            .build();

    @Test
    public void uploadEmployees() {
        var flux = Flux.range(1, 1_000_000)
                .map(i -> EmployeeDto.builder().id(i).name("Employee" + i).email(i + "@mail.com").build());

        this.client.post()
                .uri("/employees/v3/upload")
                .contentType(MediaType.APPLICATION_NDJSON)
                .body(flux, EmployeeDto.class)
                .retrieve()
                .bodyToMono(UploadResponse.class)
                .doOnNext(r -> log.info("received: {}", r))
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();
    }

    @Test
    public void downloadEmployees() {
        this.client.get()
                .uri("/employees/v3/download")
                .accept(MediaType.APPLICATION_NDJSON)
                .retrieve()
                .bodyToFlux(EmployeeDto.class)
                .map(Record::toString)
                .doOnNext(e -> log.info("received: {}", e))
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();
    }

}
