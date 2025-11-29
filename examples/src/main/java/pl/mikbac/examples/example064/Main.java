package pl.mikbac.examples.example064;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import pl.mikbac.examples.example062.SubscriberImpl;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Slf4j
public class Main {

    @SneakyThrows
    public static void main(String[] args) {
        log.info("Example - 1");
        eventProd1()
                .buffer()// by default with Integer.MAX_VALUE
                .subscribe(new SubscriberImpl("S1"));
        // [main] Subscribed [subscriberId=S1]
        // [parallel-1] Received [item=[event-0, event-1, event-2, event-3, event-4], subscriberId=S1]
        // [parallel-1] Completed [subscriberId=S1]

        Thread.sleep(5000);

        log.info("Example - 2");
        eventProd1()
                .buffer(2)
                .subscribe(new SubscriberImpl("S1"));
        // [main] Subscribed [subscriberId=S1]
        // [parallel-2] Received [item=[event-0, event-1], subscriberId=S1]
        // [parallel-2] Received [item=[event-2, event-3], subscriberId=S1]
        // [parallel-2] Received [item=[event-4], subscriberId=S1]
        // [parallel-2] Completed [subscriberId=S1]

        Thread.sleep(5000);

        log.info("Example - 3");
        eventProd1()
                .buffer(Duration.ofMillis(500))
                .subscribe(new SubscriberImpl("S1"));
        // [main] Subscribed [subscriberId=S1]
        // [parallel-3] Received [item=[event-0, event-1], subscriberId=S1]
        // [parallel-3] Received [item=[event-2, event-3], subscriberId=S1]
        // [parallel-4] Received [item=[event-4], subscriberId=S1]
        // [parallel-4] Completed [subscriberId=S1]

        Thread.sleep(5000);

        log.info("Example - 4");
        eventProd1()
                .bufferTimeout(2, Duration.ofMillis(500))
                .subscribe(new SubscriberImpl("S1"));
        // [main] Subscribed [subscriberId=S1]
        // [parallel-5] Received [item=[event-0, event-1], subscriberId=S1]
        // [parallel-5] Received [item=[event-2, event-3], subscriberId=S1]
        // [parallel-5] Received [item=[event-4], subscriberId=S1]
        // [parallel-5] Completed [subscriberId=S1]

        Thread.sleep(5000);

        log.info("Example - 5");
        eventProd2()
                .buffer(2)
                .subscribe(new SubscriberImpl("S1"));
        // [main] Subscribed [subscriberId=S1]
        // [parallel-7] Received [item=[event-0, event-1], subscriberId=S1]
        // [parallel-7] Received [item=[event-2, event-3], subscriberId=S1]

        Thread.sleep(5000);

    }

    private static Flux<String> eventProd1() {
        return Flux.interval(Duration.ofMillis(200))
                .take(5)
                .map(i -> "event-" + i);
    }

    private static Flux<String> eventProd2() {
        return Flux.interval(Duration.ofMillis(200))
                .take(5)
                .concatWith(Flux.never())
                .map(i -> "event-" + i);
    }

}
