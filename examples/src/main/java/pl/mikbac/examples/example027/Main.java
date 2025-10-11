package pl.mikbac.examples.example027;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by MikBac on 8.10.2025
 */
@Slf4j
public class Main {

    public static void main(String[] args) {

        Flux<String> flux = Flux.just("aaa", "bbb", "ccc", "ddd");
        Mono.from(flux)
                .subscribe(new SubscriberImpl());

        Mono<String> mono = Mono.just("aaa");
        Flux.from(mono)
                .subscribe(new SubscriberImpl());

    }

}
