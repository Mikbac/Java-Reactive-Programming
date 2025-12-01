package pl.mikbac.examples.example070;

import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class Main {

    /**
     * Resubscribe after error
     */

    @SneakyThrows
    public static void main(String[] args) {
        log.info("Example 1");
        getError()
                .retry(1)
                .subscribe(new SubscriberImpl("S1"));
        // [main] Subscribed [subscriberId=S1]
        // [main] Error: important error
        // [main] Error: important error
        // [main] important error
        //java.lang.RuntimeException: important error
        //	at pl.mikbac.examples.example070.Main.lambda$getError$0(Main.java:57)
        //	at reactor.core.publisher.MonoSupplier$MonoSupplierSubscription.request(MonoSupplier.java:126)
        //	at reactor.core.publisher.MonoPeekTerminal$MonoTerminalPeekSubscriber.request(MonoPeekTerminal.java:139)
        //	at reactor.core.publisher.Operators$MultiSubscriptionSubscriber.set(Operators.java:2366)
        //	at reactor.core.publisher.Operators$MultiSubscriptionSubscriber.onSubscribe(Operators.java:2240)
        //	at reactor.core.publisher.MonoPeekTerminal$MonoTerminalPeekSubscriber.onSubscribe(MonoPeekTerminal.java:152)
        //	at reactor.core.publisher.MonoSupplier.subscribe(MonoSupplier.java:48)
        //	at reactor.core.publisher.InternalMonoOperator.subscribe(InternalMonoOperator.java:76)

        log.info("Example 2");
        getError()
                .retry(5)
                .subscribe(new SubscriberImpl("S1"));
        // [main] Subscribed [subscriberId=S1]
        // [main] Error: important error
        // [main] Error: important error
        // [main] Received [item=counter: 3, subscriberId=S1]
        // [main] Completed [subscriberId=S1]

        log.info("Example 3");
        getError()
                .retryWhen(Retry.max(3))
                .subscribe(new SubscriberImpl("S1"));
        // [main] Example 3
        // [main] Subscribed [subscriberId=S1]
        // [main] Error: important error
        // [main] Error: important error
        // [main] Received [item=counter: 3, subscriberId=S1]
        // [main] Completed [subscriberId=S1]

        log.info("Example 4");
        getError()
                .retryWhen(Retry.fixedDelay(2, Duration.ofSeconds(1)).doBeforeRetry(rs -> log.info("Retry: {}", rs)))
                .subscribe(new SubscriberImpl("S1"));
        // [main] Subscribed [subscriberId=S1]
        // [main] Error: important error
        // [main] Retry: attempt #1 (1 in a row), last failure={java.lang.RuntimeException: important error}
        // [parallel-1] Error: important error
        // [parallel-1] Retry: attempt #2 (2 in a row), last failure={java.lang.RuntimeException: important error}
        // [parallel-2] Received [item=counter: 3, subscriberId=S1]
        // [parallel-2] Completed [subscriberId=S1]
        Thread.sleep(5000);

    }

    private static Mono<String> getError() {
        final AtomicInteger counter = new AtomicInteger(0);
        return Mono.fromSupplier(() -> {
                            if (counter.incrementAndGet() < 3) {
                                throw new RuntimeException("important error");
                            }
                            return "counter: " + counter;
                        }
                )
                .doOnError(err -> log.info("Error: {}", err.getMessage()));
    }
}
