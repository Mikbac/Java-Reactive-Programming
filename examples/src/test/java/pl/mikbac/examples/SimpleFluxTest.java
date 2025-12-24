package pl.mikbac.examples;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.test.StepVerifierOptions;

import java.time.Duration;

/**
 * Created by MikBac on 24.12.2025
 */
public class SimpleFluxTest {

    @Test
    void test1() {
        StepVerifier.create(getVals(), 1)
                .expectNext(1)
                .thenCancel()
                .verify();
    }

    @Test
    void test2() {
        final StepVerifierOptions options = StepVerifierOptions.create().scenarioName("test description");
        StepVerifier.create(getVals(), options)
                .expectNext(1)
                .as("check first value")
                .expectNext(2)
                .expectNext(3)
                .expectComplete()
                .verify();
    }

    @Test
    void test3() {
        StepVerifier.create(getVals())
                .expectNext(1, 2, 3)
                .expectComplete()
                .verify();
    }

    @Test
    void test4() {
        StepVerifier.create(getValsRange())
                .expectNext(1, 2, 3, 4)
                .expectNextCount(96)
                .expectComplete()
                .verify();
    }

    @Test
    void test5() {
        StepVerifier.create(getValsRange())
                .expectNextMatches(i -> i > 0 && i < 101)
                .expectNextCount(99)
                .expectComplete()
                .verify();
    }

    @Test
    void test6() {
        StepVerifier.create(getValsRange())
                .thenConsumeWhile(i -> i > 0 && i < 101)
                .expectComplete()
                .verify();
    }

    @Test
    void test7() {
        StepVerifier.create(getValsRange())
                .assertNext(i -> Assertions.assertEquals(1, i))
                .assertNext(i -> Assertions.assertEquals(2, i))
                .expectNextCount(98)
                .expectComplete()
                .verify();
    }

    @Test
    void test8() {
        StepVerifier.create(getValsRange().collectList())
                .assertNext(i -> Assertions.assertEquals(100, i.size()))
                .expectComplete()
                .verify();
    }

    @Test
    void test9() {
        StepVerifier.withVirtualTime(this::getValsDelay)
                .thenAwait(Duration.ofSeconds(55))
                .expectNext(1, 2, 3, 4, 5)
                .expectComplete()
                .verify();
    }

    @Test
    void test10() {
        StepVerifier.withVirtualTime(this::getValsDelay)
                .expectSubscription()
                .expectNoEvent(Duration.ofSeconds(8))
                .thenAwait(Duration.ofSeconds(2))
                .expectNext(1)
                .thenAwait(Duration.ofSeconds(41))
                .expectNext(2, 3, 4, 5)
                .expectComplete()
                .verify();
    }

    private Flux<Integer> getVals() {
        return Flux.just(1, 2, 3)
                .log();
    }

    private Flux<Integer> getValsRange() {
        return Flux.range(1, 100);
    }

    private Flux<Integer> getValsDelay() {
        return Flux.range(1, 5)
                .delayElements(Duration.ofSeconds(10));
    }

}
