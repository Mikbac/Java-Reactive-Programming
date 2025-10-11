package pl.mikbac.examples.example025;

import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * Created by MikBac on 5.10.2025
 */
@Slf4j
public class Main {

    private final static Faker faker = Faker.instance();

    @SneakyThrows
    public static void main(String[] args) {

        Flux.interval(Duration.ofMillis(1000))
                .map(i -> faker.name().firstName())
                .subscribe(new SubscriberImpl());

        Thread.sleep(6000);
    }

}
