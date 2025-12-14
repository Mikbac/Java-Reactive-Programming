package pl.mikbac.examples.example071;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@Slf4j
public class Main {

    @SneakyThrows
    public static void main(String[] args) {
        log.info("Example 1");
        Sinks.One<Object> sink1 = Sinks.one();
        Mono<Object> mono1 = sink1.asMono();
        mono1.subscribe(new SubscriberImpl<>("S1"));
        // [main] Example 1
        // [main] Subscribed [subscriberId=S1]

        log.info("Example 2");
        Sinks.One<Object> sink2 = Sinks.one();
        Mono<Object> mono2 = sink2.asMono();
        mono2.subscribe(new SubscriberImpl<>("S2"));
        sink2.tryEmitEmpty();
        // [main] Example 2
        // [main] Subscribed [subscriberId=S2]
        // [main] Completed [subscriberId=S2]

        log.info("Example 3");
        Sinks.One<Object> sink3 = Sinks.one();

        Mono<Object> mono31 = sink3.asMono();
        mono31.subscribe(new SubscriberImpl<>("S31"));

        Mono<Object> mono32 = sink3.asMono();
        mono32.subscribe(new SubscriberImpl<>("S32"));

        sink3.tryEmitValue("test 3");
        // [main] Example 3
        // [main] Subscribed [subscriberId=S31]
        // [main] Subscribed [subscriberId=S32]
        // [main] Received [item=test 3, subscriberId=S31]
        // [main] Completed [subscriberId=S31]
        // [main] Received [item=test 3, subscriberId=S32]
        // [main] Completed [subscriberId=S32]

        log.info("Example 4");
        Sinks.One<Object> sink4 = Sinks.one();
        Mono<Object> mono4 = sink4.asMono();
        mono4.subscribe(new SubscriberImpl<>("S4"));

        sink4.emitValue("test 4-1", (signalType, emitResult) -> {
            log.info("emit: test 4-1");
            log.info(signalType.name());
            log.info(emitResult.name());
            return false; // if true, retry emitting
        });

        sink4.emitValue("test 4-2", (signalType, emitResult) -> {
            log.info("emit: test 4-2");
            log.info(signalType.name());
            log.info(emitResult.name());
            return false; // if true, retry emitting
        });
        // [main] Example 4
        // [main] Subscribed [subscriberId=S4]
        // [main] Received [item=test 4-1, subscriberId=S4]
        // [main] Completed [subscriberId=S4]
        // [main] emit: test 4-2
        // [main] ON_NEXT
        // [main] FAIL_TERMINATED

    }

}
