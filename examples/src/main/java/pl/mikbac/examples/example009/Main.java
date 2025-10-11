package pl.mikbac.examples.example009;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by MikBac on 02.10.2025
 */

@Slf4j
public class Main {

    public static void main(String[] args) {
        // Defer delay the publisher creation
        Mono.defer(Main::createPublisher);
        // without a subscribe publisher will not be created

        Mono.defer(Main::createPublisher)
                .subscribe(new SubscriberImpl());
    }

    @SneakyThrows
    private static Mono<String> createPublisher() {
        log.info("Creating Publisher");
        var list = List.of("aaa", "bbb", "ccc");
        Thread.sleep(2000);
        return Mono.fromSupplier(() -> concat(list));
    }

    @SneakyThrows
    private static String concat(List<String> list) {
        log.info("Concatenating List");
        Thread.sleep(4000);
        return list.stream()
                .map(String::toUpperCase)
                .collect(Collectors.joining());
    }
}
