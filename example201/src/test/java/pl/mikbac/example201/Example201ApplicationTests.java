package pl.mikbac.example201;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.data.redis.core.ReactiveValueOperations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
class Example201ApplicationTests {

    @Autowired
    private ReactiveStringRedisTemplate reactiveStringRedisTemplate;

    @RepeatedTest(4)
    void shouldIncrementRedisCounter() {
        final ReactiveValueOperations<String, String> valueOperations = this.reactiveStringRedisTemplate.opsForValue();

        final Mono<Void> counter = Flux.range(1, 100_000)
                .flatMap(n -> valueOperations.increment("counter"))
                .then();

        StepVerifier.create(counter)
                .verifyComplete();
    }

}
