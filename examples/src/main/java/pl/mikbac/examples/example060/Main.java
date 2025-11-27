package pl.mikbac.examples.example060;

import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.function.UnaryOperator;

@Slf4j
public class Main {

    private final static Faker faker = Faker.instance();

    @SneakyThrows
    public static void main(String[] args) {
        log.info("Example - 1");
        Flux.range(1, 10)
                .concatMap(Main::getCountry)
                .transform(fluxLog())
                .subscribe();

        // [main] Subscribed
        // [parallel-1] Received [item=Country[id=1, name=South Africa]]
        // [parallel-2] Received [item=Country[id=2, name=Trinidad and Tobago]]
        // [parallel-3] Received [item=Country[id=3, name=Congo]]
        // [parallel-4] Received [item=Country[id=4, name=Ireland]]
        // [parallel-5] Received [item=Country[id=5, name=Albania]]
        // [parallel-6] Received [item=Country[id=6, name=Pakistan]]
        // [parallel-7] Received [item=Country[id=7, name=Kazakhstan]]
        // [parallel-8] Received [item=Country[id=8, name=Sri Lanka]]
        // [parallel-9] Received [item=Country[id=9, name=Oman]]
        // [parallel-10] Received [item=Country[id=10, name=C√¥te d'Ivoire]]
        // [parallel-10] Completed

        Thread.sleep(15000);
    }

    private record Country(Integer id, String name) {
    }

    private static Mono<Country> getCountry(int id) {
        return Mono.just(new Country(id, faker.country().name()))
                .delayElement(Duration.ofMillis(1000));
    }

    private static <T> UnaryOperator<Flux<T>> fluxLog() {
        return flux -> flux
                .doOnSubscribe(s -> log.info("Subscribed"))
                .doOnNext(i -> log.info("Received [item={}]", i))
                .doOnCancel(() -> log.info("Cancelling"))
                .doOnComplete(() -> log.info("Completed"));
    }
}
