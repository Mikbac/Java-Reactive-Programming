package pl.mikbac.examples.example022;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

/**
 * Created by MikBac on 5.10.2025
 */
@Slf4j
public class Main {

    public static void main(String[] args) {

        // Observe all Reactive Streams signals and trace them using Logger support. Default will use Level.INFO and java.util.logging.
        // If SLF4J is available, it will be used instead.
        // https://projectreactor.io/docs/core/release/api/reactor/core/publisher/Flux.html
        Flux.range(1, 3)
                .log("test")
                .map(n -> n * n)
                .log()
                .subscribe(new SubscriberImpl());
        // test -- | onSubscribe([Synchronous Fuseable] FluxRange.RangeSubscription)
        // reactor.Flux.MapFuseable.2 -- | onSubscribe([Fuseable] FluxMapFuseable.MapFuseableSubscriber)
        // pl.mikbac.examples.example022.SubscriberImpl -- Subscribed
        // reactor.Flux.MapFuseable.2 -- | request(unbounded)
        // test -- | request(unbounded)
        // test -- | onNext(1)
        // reactor.Flux.MapFuseable.2 -- | onNext(1)
        // pl.mikbac.examples.example022.SubscriberImpl -- Received: 1
        // test -- | onNext(2)
        // reactor.Flux.MapFuseable.2 -- | onNext(4)
        // pl.mikbac.examples.example022.SubscriberImpl -- Received: 4
        // test -- | onNext(3)
        // reactor.Flux.MapFuseable.2 -- | onNext(9)
        // pl.mikbac.examples.example022.SubscriberImpl -- Received: 9
        // test -- | onComplete()
        // reactor.Flux.MapFuseable.2 -- | onComplete()
        // pl.mikbac.examples.example022.SubscriberImpl -- Completed

    }

}
