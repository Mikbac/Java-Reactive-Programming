package pl.mikbac.examples.example037;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class Main {

    public static void main(String[] args) {
        Flux.<Integer>create(fluxSink -> {
                    log.info("producer started");
                    for (int i = 0; i < 4; i++) {
                        fluxSink.next(i);
                    }
                    fluxSink.complete();
                    // fluxSink.error(new RuntimeException("error"));
                    log.info("producer ended");
                })
                .doOnComplete(() -> log.info("doOnComplete-1"))
                .doFirst(() -> log.info("doFirst-1"))
                .doOnNext(item -> log.info("doOnNext-1: {}", item))
                .doOnSubscribe(subscription -> log.info("doOnSubscribe-1: {}", subscription))
                .doOnRequest(request -> log.info("doOnRequest-1: {}", request))
                .doOnError(error -> log.info("doOnError-1: {}", error.getMessage()))
                .doOnTerminate(() -> log.info("doOnTerminate-1")) // complete or error case
                .doOnCancel(() -> log.info("doOnCancel-1"))
                .doOnDiscard(Object.class, o -> log.info("doOnDiscard-1: {}", o))
                .doFinally(signal -> log.info("doFinally-1: {}", signal)) // finally irrespective of the reason
                // .take(2)
                .doOnComplete(() -> log.info("doOnComplete-2"))
                .doFirst(() -> log.info("doFirst-2"))
                .doOnNext(item -> log.info("doOnNext-2: {}", item))
                .doOnSubscribe(subscription -> log.info("doOnSubscribe-2: {}", subscription))
                .doOnRequest(request -> log.info("doOnRequest-2: {}", request))
                .doOnError(error -> log.info("doOnError-2: {}", error.getMessage()))
                .doOnTerminate(() -> log.info("doOnTerminate-2")) // complete or error case
                .doOnCancel(() -> log.info("doOnCancel-2"))
                .doOnDiscard(Object.class, o -> log.info("doOnDiscard-2: {}", o))
                .doFinally(signal -> log.info("doFinally-2: {}", signal)) // finally irrespective of the reason
                //.take(4)
                .subscribe(new SubscriberImpl());

        // example037.Main -- doFirst-2
        // example037.Main -- doFirst-1
        // example037.Main -- doOnSubscribe-1: reactor.core.publisher.FluxPeekFuseable$PeekConditionalSubscriber@4bbfb90a
        // example037.Main -- doOnSubscribe-2: reactor.core.publisher.FluxPeekFuseable$PeekConditionalSubscriber@6c49835d
        // example035.SubscriberImpl -- Subscribed
        // example037.Main -- doOnRequest-2: 9223372036854775807
        // example037.Main -- doOnRequest-1: 9223372036854775807
        // example037.Main -- producer started
        // example037.Main -- doOnNext-1: 0
        // example037.Main -- doOnNext-2: 0
        // example035.SubscriberImpl -- Received: 0
        // example037.Main -- doOnNext-1: 1
        // example037.Main -- doOnNext-2: 1
        // example035.SubscriberImpl -- Received: 1
        // example037.Main -- doOnNext-1: 2
        // example037.Main -- doOnNext-2: 2
        // example035.SubscriberImpl -- Received: 2
        // example037.Main -- doOnNext-1: 3
        // example037.Main -- doOnNext-2: 3
        // example035.SubscriberImpl -- Received: 3
        // example037.Main -- doOnComplete-1
        // example037.Main -- doOnTerminate-1
        // example037.Main -- doOnComplete-2
        // example037.Main -- doOnTerminate-2
        // example035.SubscriberImpl -- Completed
        // example037.Main -- doFinally-2: onComplete
        // example037.Main -- doFinally-1: onComplete
        // example037.Main -- producer ended

    }

}
