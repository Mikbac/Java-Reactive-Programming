package pl.mikbac.examples.example047;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class Main {

    @SneakyThrows
    public static void main(String[] args) {

        var flux = Flux.create(fluxSink -> {
            fluxSink.next("aaa");
            fluxSink.next("bbb");
            fluxSink.next("ccc");
            fluxSink.complete();
        });

        flux
                .publishOn(Schedulers.parallel())
                .doOnNext(v -> log.info("value: {}", v))
                .doFirst(() -> log.info("first-1"))
                .subscribeOn(Schedulers.boundedElastic())
                .doFirst(() -> log.info("first-2"))
                .subscribe(new SubscriberImpl("S1"));
        // [main] first-2
        // [main] Subscribed [subscriberId=S1]
        // [boundedElastic-1] first-1
        // [parallel-1] value: aaa
        // [parallel-1] Received [item=aaa, subscriberId=S1]
        // [parallel-1] value: bbb
        // [parallel-1] Received [item=bbb, subscriberId=S1]
        // [parallel-1] value: ccc
        // [parallel-1] Received [item=ccc, subscriberId=S1]
        // [parallel-1] Completed [subscriberId=S1]

        Thread.sleep(4000);
    }

}
