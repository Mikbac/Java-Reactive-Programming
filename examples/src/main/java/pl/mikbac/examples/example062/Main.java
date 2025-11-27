package pl.mikbac.examples.example062;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Slf4j
public class Main {

    @SneakyThrows
    public static void main(String[] args) {
        log.info("Example - 1");
        Flux.range(1, 5)
                .map(i -> "proceed: " + i)
                .log()
                .delayElements(Duration.ofMillis(500))
                .then(doSomething())
                .subscribe(new SubscriberImpl("S1"));
        // [main] Subscribed [subscriberId=S1]
        // [main] | onSubscribe([Fuseable] FluxMapFuseable.MapFuseableSubscriber)
        // [main] | request(1)
        // [main] | onNext(proceed: 1)
        // [parallel-1] | request(1)
        // [parallel-1] | onNext(proceed: 2)
        // [parallel-2] | request(1)
        // [parallel-2] | onNext(proceed: 3)
        // [parallel-3] | request(1)
        // [parallel-3] | onNext(proceed: 4)
        // [parallel-4] | request(1)
        // [parallel-4] | onNext(proceed: 5)
        // [parallel-4] | onComplete()
        // [parallel-5] successfully executed
        // [parallel-5] Completed [subscriberId=S1]

        Thread.sleep(6000);

    }

    private static Mono<Void> doSomething() {
        return Mono.fromRunnable(() -> log.info("successfully executed"));
    }

}
