package pl.mikbac.examples.example074;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.time.Duration;

@Slf4j
public class Main {

    @SneakyThrows
    public static void main(String[] args) {
        log.info("Example 1");
        Sinks.Many<Object> sink = Sinks.many().replay().all();

        Flux<Object> flux1 = sink.asFlux();
        flux1.subscribe(new SubscriberImpl<>("S1"));

        sink.tryEmitNext("test 1");
        sink.tryEmitNext("test 2");
        sink.tryEmitNext("test 3");

        flux1.subscribe(new SubscriberImpl<>("S2"));

        sink.tryEmitNext("test 4");
        // [main] Subscribed [subscriberId=S1]
        // [main] Received [item=test 1, subscriberId=S1]
        // [main] Received [item=test 2, subscriberId=S1]
        // [main] Received [item=test 3, subscriberId=S1]
        // [main] Subscribed [subscriberId=S2]
        // [main] Received [item=test 1, subscriberId=S2]
        // [main] Received [item=test 2, subscriberId=S2]
        // [main] Received [item=test 3, subscriberId=S2]
        // [main] Received [item=test 4, subscriberId=S1]
        // [main] Received [item=test 4, subscriberId=S2]

        log.info("Example 2");
        Sinks.Many<Object> sink2 = Sinks.many().replay().limit(Duration.ofSeconds(1));

        Flux<Object> flux2 = sink2.asFlux();
        flux2.subscribe(new SubscriberImpl<>("S1"));

        sink2.tryEmitNext("test 1");
        sink2.tryEmitNext("test 2");

        Thread.sleep(2000);

        sink2.tryEmitNext("test 3");

        flux2.subscribe(new SubscriberImpl<>("S2"));

        sink2.tryEmitNext("test 4");
        // [main] Subscribed [subscriberId=S1]
        // [main] Received [item=test 1, subscriberId=S1]
        // [main] Received [item=test 2, subscriberId=S1]
        // [main] Received [item=test 3, subscriberId=S1]
        // [main] Subscribed [subscriberId=S2]
        // [main] Received [item=test 3, subscriberId=S2]
        // [main] Received [item=test 4, subscriberId=S1]
        // [main] Received [item=test 4, subscriberId=S2]

    }

}
