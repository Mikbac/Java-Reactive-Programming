package pl.mikbac.examples.example053;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Slf4j
public class Main {

    @SneakyThrows
    public static void main(String[] args) {
        log.info("Example - 1");
        getProd1()
                .concatWithValues(40, 50)
                .subscribe(new SubscriberImpl("S1"));
        // [main] Subscribing prod1
        // [main] Subscribed [subscriberId=S1]
        // [parallel-1] Received [item=1, subscriberId=S1]
        // [parallel-2] Received [item=2, subscriberId=S1]
        // [parallel-3] Received [item=3, subscriberId=S1]
        // [parallel-3] Received [item=40, subscriberId=S1]
        // [parallel-3] Received [item=50, subscriberId=S1]
        // [parallel-3] Completed [subscriberId=S1]

        Thread.sleep(2000);

        log.info("Example - 2");
        getProd1()
                .concatWith(getProd2())
                .subscribe(new SubscriberImpl("S2"));
        // [main] Subscribing prod1
        // [main] Subscribed [subscriberId=S2]
        // [parallel-4] Received [item=1, subscriberId=S2]
        // [parallel-5] Received [item=2, subscriberId=S2]
        // [parallel-6] Received [item=3, subscriberId=S2]
        // [parallel-6] Subscribing prod2
        // [parallel-7] Received [item=100, subscriberId=S2]
        // [parallel-8] Received [item=200, subscriberId=S2]
        // [parallel-9] Received [item=300, subscriberId=S2]
        // [parallel-9] Completed [subscriberId=S2]

        Thread.sleep(2000);

        log.info("Example - 3");
        Flux.concatDelayError(getProd1(), getProd3(), getProd2())
                .subscribe(new SubscriberImpl("S3"));
        // [main] Subscribing prod1
        // [main] Subscribed [subscriberId=S3]
        // [parallel-10] Received [item=1, subscriberId=S3]
        // [parallel-11] Received [item=2, subscriberId=S3]
        // [parallel-12] Received [item=3, subscriberId=S3]
        // [parallel-12] Subscribing prod2
        // [parallel-1] Received [item=100, subscriberId=S3]
        // [parallel-2] Received [item=200, subscriberId=S3]
        // [parallel-3] Received [item=300, subscriberId=S3]
        // [parallel-3] null
        //java.lang.RuntimeException: null
        //	at pl.mikbac.examples.example053.Main.getProd3(Main.java:66)
        //	at pl.mikbac.examples.example053.Main.main(Main.java:47)
        // [parallel-3] Error

        Thread.sleep(2000);
    }

    private static Flux<Integer> getProd1() {
        return Flux.just(1, 2, 3)
                .doOnSubscribe(s -> log.info("Subscribing prod1"))
                .delayElements(Duration.ofMillis(20));
    }

    private static Flux<Integer> getProd2() {
        return Flux.just(100, 200, 300)
                .doOnSubscribe(s -> log.info("Subscribing prod2"))
                .delayElements(Duration.ofMillis(20));
    }

    private static Flux<Integer> getProd3() {
        return Flux.error(new RuntimeException());
    }

}
