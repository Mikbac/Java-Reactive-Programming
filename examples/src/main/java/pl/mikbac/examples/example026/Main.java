package pl.mikbac.examples.example026;

import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MikBac on 5.10.2025
 */
@Slf4j
public class Main {

    private final static Faker faker = Faker.instance();

    public static void main(String[] args) {

        Flux.defer(() -> Flux.fromIterable(getList()))
                .subscribe(new SubscriberImpl());
        // Start getList
        // Subscribed
        // Received: Lory
        // Received: Suanne
        // Received: Hayley
        // Completed

    }

    private static List<String> getList() {
        log.info("Start getList");
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            list.add(faker.name().firstName());
        }
        return list;
    }

}
