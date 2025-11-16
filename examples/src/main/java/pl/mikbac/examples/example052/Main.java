package pl.mikbac.examples.example052;

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
                .startWith(4, 5)
                .subscribe(new SubscriberImpl("S1"));
        // [main] Subscribed [subscriberId=S1]
        // [main] Received [item=4, subscriberId=S1]
        // [main] Received [item=5, subscriberId=S1]
        // [main] Subscribing prod1
        // [parallel-1] Received [item=1, subscriberId=S1]
        // [parallel-2] Received [item=2, subscriberId=S1]
        // [parallel-3] Received [item=3, subscriberId=S1]
        // [parallel-3] Completed [subscriberId=S1]

        Thread.sleep(2000);

        log.info("Example - 2");
        getProd1()
                .startWith(getProd2())
                .subscribe(new SubscriberImpl("S2"));
        // [main] Subscribing prod2
        // [main] Subscribed [subscriberId=S2]
        // [parallel-4] Received [item=100, subscriberId=S2]
        // [parallel-5] Received [item=200, subscriberId=S2]
        // [parallel-6] Received [item=300, subscriberId=S2]
        // [parallel-6] Subscribing prod1
        // [parallel-7] Received [item=1, subscriberId=S2]
        // [parallel-8] Received [item=2, subscriberId=S2]
        // [parallel-1] Received [item=3, subscriberId=S2]
        // [parallel-1] Completed [subscriberId=S2]

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

}
