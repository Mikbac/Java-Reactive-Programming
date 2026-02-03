package pl.mikbac.examples;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.mockserver.springtest.MockServerTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webtestclient.autoconfigure.AutoConfigureWebTestClient;
import org.mockserver.model.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

/**
 * Created by MikBac on 2.02.2026
 */

// https://www.mock-server.com/

@MockServerTest
@AutoConfigureWebTestClient
@SpringBootTest(properties = {"employee.service.url=http://localhost:${mockServerPort}"})
@Slf4j
public class SimpleMockServerTest {

    private MockServerClient mockServerClient;

    @Autowired
    protected WebTestClient client;

    @Test
    public void employeeInformation() {
        // given
        mockEmployeeInformation("employee-service/employee-200.json", 200);

        // then
        getEmployee(HttpStatus.OK)
                .jsonPath("$.id").isEqualTo(1)
                .jsonPath("$.email").isEqualTo("1@1.com");
    }

    private void mockEmployeeInformation(String path, int responseCode) {
        var responseBody = this.resourceToString(path);
        mockServerClient
                .when(HttpRequest.request("/employess/1"))
                .respond(
                        HttpResponse.response(responseBody)
                                .withStatusCode(responseCode)
                                .withContentType(MediaType.APPLICATION_JSON)
                );
    }

    private String resourceToString(String relativePath) {
        try {
            return Files.readString(Path.of("src/test/resources").resolve(relativePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private WebTestClient.BodyContentSpec getEmployee(HttpStatus expectedStatus) {
        return this.client.get()
                .uri("/employess/1")
                .exchange()
                .expectStatus().isEqualTo(expectedStatus)
                .expectBody()
                .consumeWith(e -> log.info("{}", new String(Objects.requireNonNull(e.getResponseBody()))));
    }

}
