package pl.mikbac.examples.example102;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import pl.mikbac.examples.example102.dto.EmployeeDto;

import java.util.UUID;
import java.util.function.Consumer;

/**
 * Created by MikBac on 21.01.2026
 */

@Service
@Slf4j
public class ExchangeFilterSamples implements CommandLineRunner {

    @Override
    @SneakyThrows
    public void run(final String... args) {
        createWebClient(wc -> wc.filter(sampleFilterOne()).filter(sampleFilterTwo()))
                .get()
                .uri("/{id}", 1)
                .attribute("sampleValue", "testValue")
                .retrieve()
                .bodyToMono(EmployeeDto.class)
                .doOnNext(e -> log.info("Received employee [id={},email={}]", e.id(), e.email()))
                .subscribe();
        Thread.sleep(5000);
    }

    private ExchangeFilterFunction sampleFilterOne() {
        return (request, next) -> {
            log.info("Called exchange filter");
            log.info("Request method: {}", request.method());
            String correlationId = UUID.randomUUID().toString().replace("-", "");
            ClientRequest modifiedRequest = ClientRequest
                    .from(request)
                    .header("user-token", "qwerty!")
                    .header("correlationId", correlationId)
                    .build();

            return next.exchange(modifiedRequest);
        };
    }

    private ExchangeFilterFunction sampleFilterTwo() {
        return (request, next) -> {
            log.info("Second filter called with attribute [{}]",request.attributes().getOrDefault("sampleValue", "") );

            return next.exchange(request);
        };
    }

    // --------------------
    // -- Helper methods --
    // --------------------

    private WebClient createWebClient() {
        return createWebClient(b -> {
        });
    }

    private WebClient createWebClient(Consumer<WebClient.Builder> consumer) {
        var builder = WebClient.builder()
                .baseUrl("http://localhost:8080/employees/v2");
        consumer.accept(builder);
        return builder.build();
    }

}
