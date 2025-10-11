package pl.mikbac.examples.example003;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * Created by MikBac on 02.10.2025
 */

@Slf4j
public class Main {

    public static void main(String[] args) {
        Mono<String> mono = Mono.just("aaa")
                        .map(String::toUpperCase);

        mono.subscribe(i -> log.info("Received: {}", i));
        // Received: aaa

        mono.subscribe(
                i -> log.info("Received: {}", i),
                err -> log.error("Error", err),
                () -> log.info("Completed"),
                subscription -> subscription.request(1));
        // Received: aaa
        // Completed
    }

}
