package pl.mikbac.examples.example028;

import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by MikBac on 8.10.2025
 */
@Slf4j
public class Main {

    private final static Faker faker = Faker.instance();

    public static void main(String[] args) {

        Flux.create(fluxSink -> {
                    fluxSink.next("aaa");
                    fluxSink.next("bbb");
                    fluxSink.next("ccc");
                    fluxSink.complete();
                })
                .subscribe(new SubscriberImpl());
        // Subscribed
        // Received: aaa
        // Received: bbb
        // Received: ccc
        // Completed

        Flux.create(fluxSink -> {
                    for (int i = 0; i < 10; i++) {
                        fluxSink.next(faker.name().firstName());
                    }
                    fluxSink.complete();
                })
                .subscribe(new SubscriberImpl());
        // Subscribed
        // Received: Brigette
        // Received: Christiana
        // Received: Gustavo
        // Received: Valery
        // Received: Katharine
        // Received: Deandra
        // Received: Robbi
        // Received: Boyce
        // Received: Lillian
        // Received: Drema
        // Completed

    }

}
