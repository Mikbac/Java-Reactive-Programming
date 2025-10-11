package pl.mikbac.examples.example005;

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
        var list = List.of("aaa", "bbb", "ccc");
        Mono.fromSupplier(() -> concat(list))
                .subscribe(new SubscriberImpl());
        // Subscribed
        // Received: AAABBBCCC
        // Completed
    }

    private static String concat(List<String> list) {
        return list.stream()
                .map(String::toUpperCase)
                .collect(Collectors.joining());
    }
}
