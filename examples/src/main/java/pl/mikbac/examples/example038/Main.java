package pl.mikbac.examples.example038;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Slf4j
public class Main {

    @SneakyThrows
    public static void main(String[] args) {
        Flux.range(1, 5)
                .log()
                .delayElements(Duration.ofSeconds(2))
                .subscribe(new SubscriberImpl());

        Thread.sleep(10000);

        Flux.range(1, 10)
                .filter(i -> i > 20)
                .defaultIfEmpty(999)
                .subscribe(new SubscriberImpl());
        // Subscribed
        // Received: 999
        // Completed

        Flux.range(1, 10)
                .filter(i -> i > 20)
                .switchIfEmpty(Flux.range(200, 3))
                .subscribe(new SubscriberImpl());
        // Subscribed
        // Received: 200
        // Received: 201
        // Received: 202
        // Completed
    }

}
