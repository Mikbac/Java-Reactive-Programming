package pl.mikbac.examples.example033;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

/**
 * Created by MikBac on 12.10.2025
 */
@Slf4j
public class Main {

    public static void main(String[] args) {
        Flux.range(1, 5)
                .log("before take")
                .take(3)
                .log("before sub")
                .subscribe(new SubscriberImpl());
        // Received: 1
        // Received: 2
        // Received: 3
        // cancel()
        // onComplete()
        // Completed

        Flux.range(1, 5)
                .log("before take")
                .take(10)
                .log("before sub")
                .subscribe(new SubscriberImpl());
        // Received: 1
        // Received: 2
        // Received: 3
        // Received: 4
        // Received: 5
        // cancel()
        // onComplete()
        // Completed

        Flux.range(1, 5)
                .log("before take")
                .takeWhile(i -> i < 3) // only includes the matching data
                .log("before sub")
                .subscribe(new SubscriberImpl());
        // Received: 1
        // Received: 2
        // cancel()
        // onComplete()
        // Completed

        Flux.range(1, 5)
                .log("before take")
                .takeUntil(i -> i > 2) // relay values from this Flux until the given Predicate matches + the last item
                .log("before sub")
                .subscribe(new SubscriberImpl());
        // Received: 1
        // Received: 2
        // Received: 3
        // cancel()
        // onComplete()
        // Completed
    }

}
