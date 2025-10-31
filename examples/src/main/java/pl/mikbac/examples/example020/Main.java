package pl.mikbac.examples.example020;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

/**
 * Created by MikBac on 5.10.2025
 */

@Slf4j
public class Main {

    public static void main(String[] args) {

        log.info("Example 1");
        Flux.just(1, 2, 3, "aaa", 4, "bbb")
                .subscribe(new SubscriberImpl());
        // Subscribed
        // Received: 1
        // Received: 2
        // Received: 3
        // Received: aaa
        // Received: 4
        // Received: bbb
        // Completed

        log.info("Example 2");
        Flux.just(1, 2, 3, "aaa", 4, "bbb")
                .filter(o -> o instanceof String)
                .subscribe(new SubscriberImpl());
        // Subscribed
        // Received: aaa
        // Received: bbb
        // Completed

        log.info("Example 3");
        Flux.just(1, 2, 3, "aaa", 4, "bbb")
                .filter(o -> o instanceof String)
                .map(o -> ">" + o + "<")
                .subscribe(new SubscriberImpl());
        // Subscribed
        // Received: >aaa<
        // Received: >bbb<
        // Completed

        log.info("Example 4");
        Flux.just(1, 2, 3, "aaa", 4, "bbb", "ccc")
                .filter(o -> o instanceof String)
                .map(o -> ">" + o + "<")
                .doOnSubscribe(d -> log.info("Subscribed"))
                .doOnNext(i -> log.info("Received: {}", i))
                .doOnComplete(() -> log.info("Completed"))
                .doOnError(err -> log.error("Error", err))
                .subscribe();
        // Subscribed
        // Received: >aaa<
        // Received: >bbb<
        // Received: >ccc<
        // Completed

        log.info("Example 5");
        Flux.empty()
                .subscribe(new SubscriberImpl());
        // Subscribed
        // Completed

        log.info("Example 6");
        Flux.error(new RuntimeException("exception"))
                .subscribe(new SubscriberImpl());
        // Subscribed
        // exception
    }

}
