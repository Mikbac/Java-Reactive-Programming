package pl.mikbac.examples.example020;

import reactor.core.publisher.Flux;

/**
 * Created by MikBac on 5.10.2025
 */
public class Main {

    public static void main(String[] args) {
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

        Flux.just(1, 2, 3, "aaa", 4, "bbb")
                .filter(o -> o instanceof String)
                .subscribe(new SubscriberImpl());
        // Subscribed
        // Received: aaa
        // Received: bbb
        // Completed

        Flux.just(1, 2, 3, "aaa", 4, "bbb")
                .filter(o -> o instanceof String)
                .map(o -> ">" + o + "<")
                .subscribe(new SubscriberImpl());
        // Subscribed
        // Received: >aaa<
        // Received: >bbb<
        // Completed

        Flux.empty()
                .subscribe(new SubscriberImpl());
        // Subscribed
        // Completed

        Flux.error(new RuntimeException("exception"))
                .subscribe(new SubscriberImpl());
        // Subscribed
        // exception
    }

}
