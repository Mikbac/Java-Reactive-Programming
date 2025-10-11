package pl.mikbac.examples.example029;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

/**
 * Created by MikBac on 8.10.2025
 */
@Slf4j
public class Main {

    public static void main(String[] args) {

        CountryGenerator generator = new CountryGenerator();
        Flux<String> flux = Flux.create(generator);
        flux.subscribe(new SubscriberImpl());

        for (int i = 0; i < 10; i++) {
            generator.generate();
        }

        generator.complete();

    }

}
