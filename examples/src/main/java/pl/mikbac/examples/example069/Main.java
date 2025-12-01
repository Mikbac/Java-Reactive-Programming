package pl.mikbac.examples.example069;

import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

@Slf4j
public class Main {

    private final static Faker faker = Faker.instance();

    /**
     * Resubscribe after complete
     */

    @SneakyThrows
    public static void main(String[] args) {

        log.info("Example 1");
        Mono.fromSupplier(() -> faker.book().title())
                .repeat(3)
                .subscribe(new SubscriberImpl("S1"));
        // [main] Subscribed [subscriberId=S1]
        // [main] Received [item=The Curious Incident of the Dog in the Night-Time, subscriberId=S1]
        // [main] Received [item=After Many a Summer Dies the Swan, subscriberId=S1]
        // [main] Received [item=O Pioneers!, subscriberId=S1]
        // [main] Received [item=Cabbages and Kings, subscriberId=S1]
        // [main] Completed [subscriberId=S1]

        log.info("Example 2");
        Mono.fromSupplier(() -> faker.book().title())
                .repeat()
                .takeUntil(b -> b.equals("Cabbages and Kings"))
                .subscribe(new SubscriberImpl("S1"));
        // [main] Subscribed [subscriberId=S1]
        // [main] Received [item=For Whom the Bell Tolls, subscriberId=S1]
        //...
        // [main] Received [item=A Time to Kill, subscriberId=S1]
        // [main] Received [item=Cabbages and Kings, subscriberId=S1]
        // [main] Completed [subscriberId=S1]

        log.info("Example 3");
        final AtomicInteger counter1 = new AtomicInteger(0);
        Mono.fromSupplier(() -> faker.book().title())
                .repeat(() -> counter1.incrementAndGet() < 5)
                .subscribe(new SubscriberImpl("S1"));
        // [main] Subscribed [subscriberId=S1]
        // [main] Received [item=Consider Phlebas, subscriberId=S1]
        // [main] Received [item=Number the Stars, subscriberId=S1]
        // [main] Received [item=An Instant In The Wind, subscriberId=S1]
        // [main] Received [item=The Golden Bowl, subscriberId=S1]
        // [main] Received [item=Taming a Sea Horse, subscriberId=S1]
        // [main] Completed [subscriberId=S1]

        log.info("Example 4");
        Mono.fromSupplier(() -> faker.book().title())
                .repeatWhen(f -> f.delayElements(Duration.ofSeconds(1)).take(3))
                .subscribe(new SubscriberImpl("S1"));
        // [main] Subscribed [subscriberId=S1]
        // [main] Received [item=The Other Side of Silence, subscriberId=S1]
        // [parallel-1] Received [item=Vanity Fair, subscriberId=S1]
        // [parallel-2] Received [item=Nine Coaches Waiting, subscriberId=S1]
        // [parallel-3] Received [item=This Lime Tree Bower, subscriberId=S1]
        // [parallel-3] Completed [subscriberId=S1]
        Thread.sleep(5000);

        log.info("Example 5");
        Flux.just(1, 2)
                .repeat(3)
                .subscribe(new SubscriberImpl("S1"));
        // [main] Subscribed [subscriberId=S1]
        // [main] Received [item=1, subscriberId=S1]
        // [main] Received [item=2, subscriberId=S1]
        // [main] Received [item=1, subscriberId=S1]
        // [main] Received [item=2, subscriberId=S1]
        // [main] Received [item=1, subscriberId=S1]
        // [main] Received [item=2, subscriberId=S1]
        // [main] Received [item=1, subscriberId=S1]
        // [main] Received [item=2, subscriberId=S1]
        // [main] Completed [subscriberId=S1]

    }

}
