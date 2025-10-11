package pl.mikbac.examples.example002;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * Created by MikBac on 02.10.2025
 */

@Slf4j
public class Main {

    public static void main(String[] args) {
        Mono<String> mono = Mono.just("aaa");
        // Publisher<String> mono = Mono.just("aaa");

        var subscriber = new SubscriberImpl();
        mono.subscribe(subscriber);

        subscriber.getSubscription().request(10);
        // Received: aaa
        // Completed

        // does nothing because the onComplete method has already been called
        subscriber.getSubscription().request(10);
        subscriber.getSubscription().cancel();
    }

}
