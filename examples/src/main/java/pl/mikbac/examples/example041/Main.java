package pl.mikbac.examples.example041;

import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.util.function.UnaryOperator;

@Slf4j
public class Main {

    private final static Faker faker = Faker.instance();

    public static void main(String[] args) {
        getUsers()
                .transform(printRecord())
                .subscribe();

        getCountries()
                .transform(printRecord())
                .subscribe();

    }

    private static Flux<User> getUsers() {
        return Flux.range(1, 5)
                .map(i -> new User(faker.name().name()));
    }

    private static Flux<Country> getCountries() {
        return Flux.range(1, 5)
                .map(i -> new Country(faker.country().name()));
    }

    private static <T> UnaryOperator<Flux<T>> printRecord() {
        return flux -> flux
                .log()
                .doOnNext(i -> log.info("Received: {}", i));
    }

}
