package pl.mikbac.examples;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.test.StepVerifierOptions;
import reactor.util.context.Context;

/**
 * Created by MikBac on 24.12.2025
 */
public class SimpleContextTest {

    @Test
    void test1() {
        final StepVerifierOptions options = StepVerifierOptions.create().withInitialContext(Context.of("testKey", "testValue"));
        StepVerifier.create(getSampleContext(), options)
                .expectNext("Value from context: testValue")
                .expectComplete()
                .verify();
    }

    private static Mono<String> getSampleContext() {
        return Mono.deferContextual(ctx -> {
            return Mono.just("Value from context: %s".formatted(ctx.get("testKey").toString()));
        });
    }

}
