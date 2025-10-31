package pl.mikbac.examples.example040;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Objects;

@Slf4j
public class Main {

    @SneakyThrows
    public static void main(String[] args) {

        log.info("Example 1");
        getNumbers()
                .timeout(Duration.ofSeconds(2))
                .subscribe(new SubscriberImpl());
        // Did not observe any item or terminal signal within 2000ms in

        Thread.sleep(5000);

        log.info("Example 2");
        getNumbers()
                .timeout(Duration.ofSeconds(2), fallback())
                .subscribe(new SubscriberImpl());
        // Subscribed
        // Received: 10
        // Received: 11
        // ...

        Thread.sleep(10000);

        log.info("Example 3");
        getTestNumbers()
                .timeout(Duration.ofSeconds(2))
                .filter(i -> i == -1)
                .switchIfEmpty(Flux.range(100, 3))
                .subscribe(new SubscriberImpl());
        // Subscribed
        // Received: 100
        // Received: 101
        // Received: 102
        // Completed

        Thread.sleep(15000);
    }

    private static Flux<Integer> getNumbers() {
        return Flux.range(1, 5)
                .delayElements(Duration.ofSeconds(3));
    }

    private static Flux<Integer> fallback() {
        return Flux.range(10, 5)
                .delayElements(Duration.ofSeconds(1));
    }

    private static Flux<Integer> getTestNumbers() {
        return Flux.just(1, 2, 3, 0, 4, 5, 0, 7)
                .delayElements(Duration.ofSeconds(1));
    }

}
