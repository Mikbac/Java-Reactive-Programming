package pl.mikbac.examples.example073;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Slf4j
public class Main {

    @SneakyThrows
    public static void main(String[] args) {
        log.info("Example 1");
        Sinks.Many<Object> sink = Sinks.many().multicast().onBackpressureBuffer();

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
        // [main] Received [item=test 4, subscriberId=S1]
        // [main] Received [item=test 4, subscriberId=S2]

    }

}
