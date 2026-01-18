package pl.mikbac.examples.example100.employeeCRUD.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webtestclient.autoconfigure.AutoConfigureWebTestClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import pl.mikbac.examples.example100.employeeCRUD.AbstractTest;
import pl.mikbac.examples.example100.employeeCRUD.dto.EmployeeDto;

import java.util.Objects;

/**
 * Created by MikBac on 5.01.2026
 */

@AutoConfigureWebTestClient
@Slf4j
@SpringBootTest(properties = {
        "employees.functional.endpoints=false"
})
class EmployeeControllerTest extends AbstractTest {

    @Autowired
    private WebTestClient client;

    @Test
    public void getUnauthorized() {
        this.client.get()
                .uri("/employees/v1")
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.UNAUTHORIZED);

        this.client.get()
                .uri("/employees/v1")
                .header("user-token", "1223")
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void getForbidden() {
        EmployeeDto employee = EmployeeDto.builder()
                .name("t")
                .email("t@t.com")
                .build();
        this.client.post()
                .uri("/employees/v1")
                .header("user-token", "qwerty")
                .bodyValue(employee)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void allEmployee() {
        this.client.get()
                .uri("/employees/v1")
                .header("user-token", "qwerty")
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
                .header("user-token", "qwerty")
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
                .header("user-token", "qwerty")
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
        EmployeeDto employee = EmployeeDto.builder()
                .name("t")
                .email("t@t.com")
                .build();
        this.client.post()
                .uri("/employees/v1")
                .header("user-token", "qwerty!")
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
                .header("user-token", "qwerty!")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody().isEmpty();
    }

    @Test
    public void updateEmployee() {
        EmployeeDto employee = EmployeeDto.builder()
                .name("t")
                .email("t@t.com")
                .build();
        this.client.put()
                .uri("/employees/v1/8")
                .header("user-token", "qwerty!")
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
                .header("user-token", "qwerty")
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody()
                .jsonPath("$.title").isEqualTo("Employee Not Found");

        this.client.delete()
                .uri("/employees/v1/99")
                .header("user-token", "qwerty!")
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody()
                .jsonPath("$.title").isEqualTo("Employee Not Found");

        final EmployeeDto employee = EmployeeDto.builder()
                .name("t")
                .email("t@t.com")
                .build();
        this.client.put()
                .uri("/employees/v1/11")
                .header("user-token", "qwerty!")
                .bodyValue(employee)
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody()
                .jsonPath("$.title").isEqualTo("Employee Not Found");
    }

    @Test
    public void invalidInput() {
        final EmployeeDto missingName = EmployeeDto.builder()
                .email("t@t.com")
                .build();
        this.client.post()
                .uri("/employees/v1")
                .header("user-token", "qwerty!")
                .bodyValue(missingName)
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody()
                .jsonPath("$.title").isEqualTo("Invalid Input");

        final EmployeeDto missingEmail = EmployeeDto.builder()
                .name("t")
                .build();
        this.client.post()
                .uri("/employees/v1")
                .header("user-token", "qwerty!")
                .bodyValue(missingEmail)
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody()
                .jsonPath("$.title").isEqualTo("Invalid Input");

        final EmployeeDto invalidEmail = EmployeeDto.builder()
                .email("tt")
                .build();
        this.client.put()
                .uri("/employees/v1/5")
                .header("user-token", "qwerty!")
                .bodyValue(invalidEmail)
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody()
                .jsonPath("$.title").isEqualTo("Invalid Input");
    }

}
