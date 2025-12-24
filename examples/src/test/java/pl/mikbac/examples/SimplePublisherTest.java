package pl.mikbac.examples;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.test.publisher.TestPublisher;

import java.util.function.UnaryOperator;

/**
 * Created by MikBac on 24.12.2025
 */
public class SimplePublisherTest {

    @Test
    public void publisherTest1() {
        TestPublisher<String> publisher = TestPublisher.create();
        Flux<String> flux = publisher.flux();

        StepVerifier.create(flux.transform(processor()))
                .then(() -> publisher.emit("test", "t"))
                .expectNext("TEST")
                .expectComplete()
                .verify();
    }

    private UnaryOperator<Flux<String>> processor() {
        return flux -> flux
                .filter(s -> s.length() > 1)
                .map(String::toUpperCase);
    }

}
