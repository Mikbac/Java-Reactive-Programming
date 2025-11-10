package pl.mikbac.examples.example044;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class Main {

    @SneakyThrows
    public static void main(String[] args) {
        log.info("Example - 1");
        var flux = Flux.create(fluxSink -> {
            fluxSink.next("aaa");
            fluxSink.next("bbb");
            fluxSink.next("ccc");
            fluxSink.complete();
        });

        flux
                .doFirst(() -> log.info("first-1"))
                .subscribeOn(Schedulers.boundedElastic())
                .doFirst(() -> log.info("first-2"))
                .doFirst(() -> log.info("first-3"))
                .subscribe(new SubscriberImpl("S1"));
        // [main] first-3
        // [main] first-2
        // [main] Subscribed [subscriberId=S1]
        // [boundedElastic-1] first-1
        // [boundedElastic-1] Received [item=aaa, subscriberId=S1]
        // [boundedElastic-1] Received [item=bbb, subscriberId=S1]
        // [boundedElastic-1] Received [item=ccc, subscriberId=S1]
        // [boundedElastic-1] Completed [subscriberId=S1]

        Thread.sleep(4000);

        log.info("Example - 2");
        flux
                .subscribeOn(Schedulers.parallel())
                .doFirst(() -> log.info("first-1"))
                .subscribeOn(Schedulers.boundedElastic())
                .doFirst(() -> log.info("first-2"))
                .doFirst(() -> log.info("first-3"))
                .subscribe(new SubscriberImpl("S1"));
        // [main] first-3
        // [main] first-2
        // [main] Subscribed [subscriberId=S1]
        // [boundedElastic-1] first-1
        // [parallel-1] Received [item=aaa, subscriberId=S1]
        // [parallel-1] Received [item=bbb, subscriberId=S1]
        // [parallel-1] Received [item=ccc, subscriberId=S1]
        // [parallel-1] Completed [subscriberId=S1]

        Thread.sleep(4000);

        log.info("Example - 3");
        flux
                .subscribeOn(Schedulers.immediate()) // run on the thread that submitted them
                .doFirst(() -> log.info("first-1"))
                .subscribeOn(Schedulers.boundedElastic())
                .doFirst(() -> log.info("first-2"))
                .doFirst(() -> log.info("first-3"))
                .subscribe(new SubscriberImpl("S1"));
        // [main] first-3
        // [main] first-2
        // [main] Subscribed [subscriberId=S1]
        // [boundedElastic-1] first-1
        // [boundedElastic-1] Received [item=aaa, subscriberId=S1]
        // [boundedElastic-1] Received [item=bbb, subscriberId=S1]
        // [boundedElastic-1] Received [item=ccc, subscriberId=S1]
        // [boundedElastic-1] Completed [subscriberId=S1]

        Thread.sleep(4000);

    }

}
