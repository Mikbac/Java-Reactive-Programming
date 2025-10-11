package pl.mikbac.examples.example023;

import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.IntStream;

/**
 * Created by MikBac on 5.10.2025
 */
@Slf4j
public class Main {

    private final static Faker faker = Faker.instance();

    public static void main(String[] args) {

        log.info("List example:");
        List<String> list = getNamesList(5);
        log.info(String.valueOf(list));
        // [Queenie, Samual, Michal, Francis, Edison]

        log.info("Flux-1 example:");
        getNamesFlux(5)
                .takeUntil(n -> n.equals("Bobbie"))
                .subscribe(new SubscriberInfImpl());
        // Subscribed
        // Received: Lorene
        // Received: Deidra
        // Received: Clair
        // Received: Deidra
        // Received: Tami
        // Completed

        log.info("Flux-2 example:");
        var subscriber = new SubscriberSimplImpl();
        getNamesFlux(5)
                .subscribe(subscriber);
        subscriber.getSubscription().request(2);
        // Received: Juan
        // Received: Gabriel
        subscriber.getSubscription().cancel();

    }

    public static List<String> getNamesList(int count){
        return IntStream.rangeClosed(1, count)
                .mapToObj(i -> generateName())
                .toList();
    }

    public static Flux<String> getNamesFlux(int count){
        return Flux.range(1, count)
                .map(i -> generateName());
    }

    @SneakyThrows
    private static String generateName(){
        Thread.sleep(1000);
        return faker.name().firstName();
    }

}
