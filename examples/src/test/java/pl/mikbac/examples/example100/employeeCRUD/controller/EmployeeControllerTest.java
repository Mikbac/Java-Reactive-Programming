package pl.mikbac.examples.example100.employeeCRUD.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import pl.mikbac.examples.example100.employeeCRUD.AbstractTest;
import org.springframework.boot.webtestclient.autoconfigure.AutoConfigureWebTestClient;
import pl.mikbac.examples.example100.employeeCRUD.dto.EmployeeDto;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by MikBac on 5.01.2026
 */

@AutoConfigureWebTestClient
@Slf4j
class EmployeeControllerTest extends AbstractTest {

    @Autowired
    private WebTestClient client;

    @Test
    public void allEmployee() {
        this.client.get()
                .uri("/employees/v1")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(EmployeeDto.class)
                .value(list -> log.info("{}", list))
                .hasSize(8);
    }

    @Test
    public void paginatedEmployee() {
        this.client.get()
                .uri("/employees/v1/paginated?page=2&size=2")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .consumeWith(r -> log.info("{}", new String(Objects.requireNonNull(r.getResponseBody()))))
                .jsonPath("$.length()").isEqualTo(2)
                .jsonPath("$[0].id").isEqualTo(3)
                .jsonPath("$[1].id").isEqualTo(4);
    }

    @Test
    public void employeeById() {
        this.client.get()
                .uri("/employees/v1/2")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .consumeWith(r -> log.info("{}", new String(Objects.requireNonNull(r.getResponseBody()))))
                .jsonPath("$.id").isEqualTo(2)
                .jsonPath("$.name").isEqualTo("alice")
                .jsonPath("$.email").isEqualTo("alice@mail.com");
    }

    @Test
    public void createAndDeleteEmployee() {
        EmployeeDto employee =  EmployeeDto.builder()
                .name("t")
                .email("t@t.com")
                .build();
        this.client.post()
                .uri("/employees/v1")
                .bodyValue(employee)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .consumeWith(r -> log.info("{}", new String(Objects.requireNonNull(r.getResponseBody()))))
                .jsonPath("$.id").isEqualTo(9)
                .jsonPath("$.name").isEqualTo("t")
                .jsonPath("$.email").isEqualTo("t@t.com");

        this.client.delete()
                .uri("/employees/v1/9")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody().isEmpty();
    }

    @Test
    public void updateEmployee() {
        EmployeeDto employee =  EmployeeDto.builder()
                .name("t")
                .email("t@t.com")
                .build();
        this.client.put()
                .uri("/employees/v1/8")
                .bodyValue(employee)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .consumeWith(r -> log.info("{}", new String(Objects.requireNonNull(r.getResponseBody()))))
                .jsonPath("$.id").isEqualTo(8)
                .jsonPath("$.name").isEqualTo("t")
                .jsonPath("$.email").isEqualTo("t@t.com");
    }

    @Test
    public void employeeNotFound() {
        this.client.get()
                .uri("/employees/v1/99")
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody().isEmpty();

        this.client.delete()
                .uri("/employees/v1/99")
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody().isEmpty();

        EmployeeDto employee =  EmployeeDto.builder()
                .name("t")
                .email("t@t.com")
                .build();
        this.client.put()
                .uri("/employees/v1/11")
                .bodyValue(employee)
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody().isEmpty();
    }

}
