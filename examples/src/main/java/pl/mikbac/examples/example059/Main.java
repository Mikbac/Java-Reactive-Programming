package pl.mikbac.examples.example059;

import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;
import java.util.function.UnaryOperator;

@Slf4j
public class Main {

    private final static Faker faker = Faker.instance();

    @SneakyThrows
    public static void main(String[] args) {
        log.info("Example - 1");
        Flux.range(1, 10)
                .flatMap(Main::getCountry, 3)
                .transform(fluxLog())
                .subscribe();

        // [main] Subscribed
        // [parallel-2] Received [item=Country[id=2, name=Palau]]
        // [parallel-2] Received [item=Country[id=1, name=Azerbaijan]]
        // [parallel-2] Received [item=Country[id=3, name=Argentina]]
        // [parallel-5] Received [item=Country[id=5, name=Barbados]]
        // [parallel-5] Received [item=Country[id=6, name=Egypt]]
        // [parallel-5] Received [item=Country[id=4, name=Zimbabwe]]
        // [parallel-7] Received [item=Country[id=7, name=Slovenia]]
        // [parallel-7] Received [item=Country[id=8, name=Guatemala]]
        // [parallel-7] Received [item=Country[id=9, name=Slovenia]]
        // [parallel-10] Received [item=Country[id=10, name=Guinea]]
        // [parallel-10] Completed

        Thread.sleep(5000);
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
