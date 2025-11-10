package pl.mikbac.examples.example045;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class Main {

    @SneakyThrows
    public static void main(String[] args) {

        System.setProperty("reactor.schedulers.defaultBoundedElasticOnVirtualThreads", "true");

        var flux = Flux.create(fluxSink -> {
            fluxSink.next("aaa");
            fluxSink.next("bbb");
            fluxSink.next("ccc");
            fluxSink.complete();
        });

        flux
                .doFirst(() -> log.info("first-1, isVirtual:{}", Thread.currentThread().isVirtual()))
                .subscribeOn(Schedulers.boundedElastic())
                .doFirst(() -> log.info("first-2, isVirtual:{}", Thread.currentThread().isVirtual()))
                .subscribe(new SubscriberImpl("S1"));
        // [main] first-2, isVirtual:false
        // [main] Subscribed [subscriberId=S1]
        // [loomBoundedElastic-1] first-1, isVirtual:true
        // [loomBoundedElastic-1] Received [item=aaa, subscriberId=S1]
        // [loomBoundedElastic-1] Received [item=bbb, subscriberId=S1]
        // [loomBoundedElastic-1] Received [item=ccc, subscriberId=S1]
        // [loomBoundedElastic-1] Completed [subscriberId=S1]

        Thread.sleep(4000);
    }

}
