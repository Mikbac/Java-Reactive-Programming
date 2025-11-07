package pl.mikbac.examples.example043;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Slf4j
public class Main {

    @SneakyThrows
    public static void main(String[] args) {

        log.info("Example 1");
        var flux1 = Flux.create(fluxSink -> {
                    fluxSink.next("aaa");
                    fluxSink.next("bbb");
                    fluxSink.next("ccc");
                    fluxSink.complete();
                })
                .delayElements(Duration.ofSeconds(1))
                .share();
        // share() - As long as there is at least one Subscriber this Flux will be subscribed and emitting data.
        // When all subscribers have cancelled it will cancel the source Flux.
        // (for hot publisher)

        flux1.subscribe(new SubscriberImpl("S1"));
        Thread.sleep(2000);
        flux1.subscribe(new SubscriberImpl("S2"));
        Thread.sleep(5000);
        // Subscribed [subscriberId=S1]
        // Received [item=aaa, subscriberId=S1]
        // Received [item=bbb, subscriberId=S1]
        // Subscribed [subscriberId=S2]
        // Received [item=ccc, subscriberId=S1]
        // Received [item=ccc, subscriberId=S2]
        // Completed [subscriberId=S1]
        // Completed [subscriberId=S2]

        log.info("Example 2");
        var flux2 = Flux.create(fluxSink -> {
                    fluxSink.next("aaa");
                    fluxSink.next("bbb");
                    fluxSink.next("ccc");
                    fluxSink.complete();
                })
                .publish()
                .refCount(1); // needs 1 min subscriber to emit data

        // start emits data when has et least 1 subscriber
        // stop when subscribers cancel
        flux2.subscribe(new SubscriberImpl("S1"));
        Thread.sleep(2000);
        flux2.subscribe(new SubscriberImpl("S2"));
        Thread.sleep(5000);
        // Subscribed [subscriberId=S1]
        // Received [item=aaa, subscriberId=S1]
        // Received [item=bbb, subscriberId=S1]
        // Received [item=ccc, subscriberId=S1]
        // Completed [subscriberId=S1]
        // Subscribed [subscriberId=S2]
        // Received [item=aaa, subscriberId=S2]
        // Received [item=bbb, subscriberId=S2]
        // Received [item=ccc, subscriberId=S2]
        // Completed [subscriberId=S2]

        log.info("Example 3");
        var flux3 = Flux.create(fluxSink -> {
                    fluxSink.next("aaa");
                    fluxSink.next("bbb");
                    fluxSink.next("ccc");
                    fluxSink.next("ddd");
                    fluxSink.complete();
                })
                .publish()
                .autoConnect(2); // ConnectableFlux automatically starts emitting data when a certain number of subscribers connect.
        // doesn't stop when subscribers cancel

        flux3.subscribe(new SubscriberImpl("S1"));
        Thread.sleep(2000);
        flux3.subscribe(new SubscriberImpl("S2"));
        Thread.sleep(5000);
        // Subscribed [subscriberId=S1]
        // Subscribed [subscriberId=S2]
        // Received [item=aaa, subscriberId=S1]
        // Received [item=aaa, subscriberId=S2]
        // Received [item=bbb, subscriberId=S1]
        // Received [item=bbb, subscriberId=S2]
        // Received [item=ccc, subscriberId=S1]
        // Received [item=ccc, subscriberId=S2]
        // Completed [subscriberId=S1]
        // Completed [subscriberId=S2]

        log.info("Example 4");
        var flux4 = Flux.create(fluxSink -> {
                    fluxSink.next("aaa");
                    fluxSink.next("bbb");
                    fluxSink.next("ccc");
                    fluxSink.next("ddd");
                    fluxSink.next("eee");
                    fluxSink.complete();
                })
                .delayElements(Duration.ofSeconds(1))
                .doOnNext(e -> log.info("emitting: {}", e))
                .replay(1)// replay last n values
                .autoConnect(0);

        flux4.subscribe(new SubscriberImpl("S1"));
        Thread.sleep(2000);
        flux4.subscribe(new SubscriberImpl("S2"));
        Thread.sleep(6000);
        // Subscribed [subscriberId=S1]
        // emitting: aaa
        // Received [item=aaa, subscriberId=S1]
        // emitting: bbb
        // Received [item=bbb, subscriberId=S1]
        // Subscribed [subscriberId=S2]
        // Received [item=bbb, subscriberId=S2]
        // emitting: ccc
        // Received [item=ccc, subscriberId=S1]
        // Received [item=ccc, subscriberId=S2]
        // emitting: ddd
        // Received [item=ddd, subscriberId=S1]
        // Received [item=ddd, subscriberId=S2]
        // emitting: eee
        // Received [item=eee, subscriberId=S1]
        // Received [item=eee, subscriberId=S2]
        // Completed [subscriberId=S1]
        // Completed [subscriberId=S2]
    }

}
