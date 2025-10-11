package pl.mikbac.examples.example004;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * Created by MikBac on 02.10.2025
 */

@Slf4j
public class Main {

    public static void main(String[] args) {

        getMessage(1).subscribe(new SubscriberImpl());
        // Received: aaa
        // Completed

        getMessage(2).subscribe(new SubscriberImpl());
        // Received: bbb
        // Completed

        getMessage(3).subscribe(new SubscriberImpl());
        // Completed

        getMessage(4).subscribe(new SubscriberImpl());
        // Error

    }

    private static Mono<String> getMessage(int id) {
        return switch (id) {
            case 1 -> Mono.just("aaa");
            case 2 -> Mono.just("bbb");
            case 3 -> Mono.empty();
            default -> Mono.error(new RuntimeException("invalid input"));
        };
    }
}
