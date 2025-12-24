package pl.mikbac.examples;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/**
 * Created by MikBac on 24.12.2025
 */

@Slf4j
public class SimpleMonoTest {

    @Test
    void test1() {
        StepVerifier.create(getNonEmptyValue(1))
                .expectNext("val-1")
                .expectComplete()
                .verify();
    }

    @Test
    void test2() {
        StepVerifier.create(getEmptyValue())
                .expectComplete()
                .verify();
    }

    @Test
    void test21() {
        StepVerifier.create(getEmptyValue())
                .verifyComplete(); // expectComplete + verify
    }

    @Test
    void test3() {
        StepVerifier.create(getError())
                .expectError(RuntimeException.class)
                .verify();
    }

    Mono<String> getNonEmptyValue(int id) {
        return Mono.fromSupplier(() -> "val-" + id)
                .doFirst(() -> log.info("doFirst for id={}", id));
    }

    Mono<String> getEmptyValue() {
        return Mono.empty();
    }

    Mono<String> getError() {
        return Mono.error(new RuntimeException("test error"));
    }

}
