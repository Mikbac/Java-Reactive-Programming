package pl.mikbac.examples.example039;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Slf4j
public class Main {

    @SneakyThrows
    public static void main(String[] args) {

        log.info("Example 1");
        Flux.range(1, 8)
                .map(i -> i / (i - 3)) // java.lang.ArithmeticException: / by zero
                .onErrorReturn(999)
                .subscribe(new SubscriberImpl());
        // Received: 0
        // Received: -2
        // Received: 999
        // Completed

        log.info("Example 2");
        Flux.range(1, 8)
                .map(i -> i / (i - 3)) // java.lang.ArithmeticException: / by zero
                .onErrorReturn(ArithmeticException.class, 999)
                .subscribe(new SubscriberImpl());
        // Received: 0
        // Received: -2
        // Received: 999
        // Completed

        log.info("Example 3");
        Flux.range(1, 8)
                .map(i -> i / (i - 3)) // java.lang.ArithmeticException: / by zero
                .onErrorReturn(IllegalAccessError.class, 999)
                .onErrorReturn(IllegalStateException.class, 888)
                .onErrorReturn(777)
                .subscribe(new SubscriberImpl());
        // Received: 0
        // Received: -2
        // Received: 777
        // Completed

        log.info("Example 4");
        Flux.range(1, 8)
                .map(i -> i / (i - 3)) // java.lang.ArithmeticException: / by zero
                .onErrorResume(e -> Mono.just(999))
                .subscribe(new SubscriberImpl());
        // Received: 0
        // Received: -2
        // Received: 999
        // Completed

        log.info("Example 5");
        Flux.range(1, 8)
                .map(i -> i / (i - 3)) // java.lang.ArithmeticException: / by zero
                .onErrorResume(ArithmeticException.class, e -> Mono.just(999))
                .subscribe(new SubscriberImpl());
        // Received: 0
        // Received: -2
        // Received: 999
        // Completed

        log.info("Example 6");
        Flux.range(1, 8)
                .map(i -> i / (i - 3)) // java.lang.ArithmeticException: / by zero
                .onErrorResume(ArithmeticException.class, e -> Mono.just(999))
                .onErrorResume(IllegalStateException.class, e -> Mono.just(888))
                .onErrorResume(e -> Mono.just(777)) // default fallback
                .subscribe(new SubscriberImpl());
        // Received: 0
        // Received: -2
        // Received: 999
        // Completed

        log.info("Example 7");
        Flux.range(1, 8)
                .map(i -> i / (i - 3)) // java.lang.ArithmeticException: / by zero
                .onErrorComplete()
                .subscribe(new SubscriberImpl());
        // Received: 0
        // Received: -2
        // Completed

        log.info("Example 8");
        Flux.range(1, 8)
                .map(i -> i / (i - 3)) // java.lang.ArithmeticException: / by zero
                .onErrorContinue((ex, obj) -> log.info(obj.toString()))
                .subscribe(new SubscriberImpl());
        // SubscriberImpl -- Subscribed
        // SubscriberImpl -- Received: 0
        // SubscriberImpl -- Received: -2
        // Main -- 3
        // SubscriberImpl -- Received: 4
        // SubscriberImpl -- Received: 2
        // SubscriberImpl -- Received: 2
        // SubscriberImpl -- Received: 1
        // SubscriberImpl -- Received: 1
        // SubscriberImpl -- Completed
    }

}
