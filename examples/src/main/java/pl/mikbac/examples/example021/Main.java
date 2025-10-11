package pl.mikbac.examples.example021;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Stream;

/**
 * Created by MikBac on 5.10.2025
 */
@Slf4j
public class Main {

    public static void main(String[] args) {
        log.info("List example:");
        List<String> list = List.of("aaa", "bbb", "ccc");

        Flux.fromIterable(list)
                .subscribe(new SubscriberImpl());
        // Subscribed
        // Received: aaa
        // Received: bbb
        // Received: ccc
        // Completed

        log.info("Array example:");
        String[] array = {"aaa", "bbb", "ccc"};

        Flux.fromArray(array)
                .subscribe(new SubscriberImpl());
        // Subscribed
        // Received: aaa
        // Received: bbb
        // Received: ccc
        // Completed

        log.info("Stream example:");
        Stream<String> stream = Stream.of("aaa", "bbb", "ccc");

        Flux.fromStream(stream)
                .subscribe(new SubscriberImpl());
        // Subscribed
        // Received: aaa
        // Received: bbb
        // Received: ccc
        // Completed

        // Flux.fromStream(stream)
        //         .subscribe(new SubscriberImpl());
        // stream has already been operated upon or closed

        log.info("Range example:");
        Flux.range(4, 8)
                  .subscribe(new SubscriberImpl());
        // Subscribed
        // 4
        // 5
        // 6
        // 7
        // 8
        // 9
        // 10
        // 11
        // Completed


    }

}
