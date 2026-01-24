package pl.mikbac.examples.example102;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import pl.mikbac.examples.example102.dto.EmployeeDto;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;

/**
 * Created by MikBac on 18.01.2026
 */

@Service
@Slf4j
public class WebClientSamples implements CommandLineRunner {

    @Override
    @SneakyThrows
    public void run(final String... args) {
//        getAllEmployees();
//        Thread.sleep(5000);

//        getEmployeesPaginated();
//        Thread.sleep(5000);

//        getEmployeeById();
//        Thread.sleep(5000);

//        getEmployeeByIdWithDefaultHeader();
//        Thread.sleep(5000);

//        getEmployeeByIdWithBasicAuth();
//        Thread.sleep(5000);

//        getEmployeeByIdWithBearerAuth();
//        Thread.sleep(5000);

//        getEmployeeByIdWithExchange();
//        Thread.sleep(5000);

//        saveEmployee1();
//        Thread.sleep(5000);

//        saveEmployee2();
//        Thread.sleep(5000);

//        saveEmployeeWithException();
//        Thread.sleep(5000);

        System.exit(0);
    }

    private void saveEmployee1() {
        final EmployeeDto employee = EmployeeDto.builder()
                .name("Eddy")
                .email("ed@mail.com")
                .build();
        createWebClient().post()
                .uri("")
                .bodyValue(employee)
                .header("user-token", "qwerty!")
                .retrieve()
                .bodyToMono(EmployeeDto.class)
                .doOnNext(e -> log.info("Received new employee [id={},email={}]", e.id(), e.email()))
                .subscribe();
    }

    private void saveEmployee2() {
        final Mono<EmployeeDto> employeeMono = Mono.fromSupplier(() -> EmployeeDto.builder()
                .name("Eddy")
                .email("e2d@mail.com")
                .build());
        createWebClient().post()
                .uri("")
                .body(employeeMono, EmployeeDto.class)
                .header("user-token", "qwerty!")
                .retrieve()
                .bodyToMono(EmployeeDto.class)
                .doOnNext(e -> log.info("Received new employee [id={},email={}]", e.id(), e.email()))
                .subscribe();
    }

    private void saveEmployeeWithException() {
        final Mono<EmployeeDto> employeeMono = Mono.fromSupplier(() -> EmployeeDto.builder()
                .build());
        createWebClient().post()
                .uri("")
                .body(employeeMono, EmployeeDto.class)
                .header("user-token", "qwerty!")
                .retrieve()
                .bodyToMono(EmployeeDto.class)
                .doOnError(WebClientResponseException.class, ex -> log.error("{}", ex.getResponseBodyAs(ProblemDetail.class)))
//                .onErrorReturn(new EmployeeDto(0, "t", "tt"))
                .onErrorReturn(WebClientResponseException.BadRequest.class, new EmployeeDto(0, "t", "tt"))
                .doOnNext(e -> log.info("Received new employee [id={},email={}]", e.id(), e.email()))
                .subscribe();
    }

    private void getEmployeesPaginated() {
        final String path = "/paginated";
        final String query = "page={first}&size={second}";
        createWebClient().get()
                .uri(builder -> builder.path(path).query(query).build(2, 2))
                .header("user-token", "qwerty!")
                .retrieve()
                .bodyToFlux(EmployeeDto.class)
                .doOnNext(e -> log.info("Received employee [employee={}]", e))
                .subscribe();
    }

    private void getAllEmployees() {
        createWebClient().get()
                .uri("")
                .header("user-token", "qwerty!")
                .retrieve()
                .bodyToFlux(EmployeeDto.class)
                .map(e -> String.format("%s, %s", e.id(), e.email()))
                .doOnNext(e -> log.info("Received employee [employee={}]", e))
                .doOnSubscribe(d -> log.info("Subscribed"))
                .doFirst(() -> log.info("Received the first employee"))
                .doOnComplete(() -> log.info("Completed"))
                .doOnError(err -> log.error("Error", err))
                .subscribe();
    }

    private void getEmployeeByIdWithDefaultHeader() {
        createWebClient(wc -> wc.defaultHeader("user-token", "qwerty!")).get()
                .uri("/{id}", 1)
                .retrieve()
                .bodyToMono(EmployeeDto.class)
                .doOnNext(e -> log.info("Received employee [id={},email={}]", e.id(), e.email()))
                .subscribe();
    }

    private void getEmployeeByIdWithBasicAuth() {
        createWebClient(wc -> wc.defaultHeaders(h -> h.setBasicAuth("user", "pass")))
                .get()
                .uri("/{id}", 1)
                .retrieve()
                .bodyToMono(EmployeeDto.class)
                .doOnNext(e -> log.info("Received employee [id={},email={}]", e.id(), e.email()))
                .subscribe();
    }

    private void getEmployeeByIdWithBearerAuth() {
        createWebClient(b -> b.defaultHeaders(h -> h.setBearerAuth("1232122322123aaaa")))
                .get()
                .uri("/{id}", 1)
                .retrieve()
                .bodyToMono(EmployeeDto.class)
                .doOnNext(e -> log.info("Received employee [id={},email={}]", e.id(), e.email()))
                .subscribe();
    }

    private void getEmployeeByIdWithExchange() {
        createWebClient().get()
                .uri("/{id}", 1)
                .header("user-token", "qwerty!")
                .exchangeToMono(cr -> {
                    log.info("headers: {}", cr.headers());
                    log.info("status code: {}", cr.statusCode());

                    return cr.bodyToMono(EmployeeDto.class);
                })
                .doOnNext(e -> log.info("Received employee [id={},email={}]", e.id(), e.email()))
                .subscribe();
    }

    private void getEmployeeById() {
        createWebClient().get()
                .uri("/{id}", 1)
                .header("user-token", "qwerty!")
                .retrieve()
                .bodyToMono(EmployeeDto.class)
                .doOnNext(e -> log.info("Received employee [id={},email={}]", e.id(), e.email()))
                .subscribe();
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
