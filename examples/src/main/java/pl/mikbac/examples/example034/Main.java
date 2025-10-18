package pl.mikbac.examples.example034;

import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

/**
 * Created by MikBac on 12.10.2025
 */
@Slf4j
public class Main {

    private final static Faker faker = Faker.instance();

    public static void main(String[] args) {
        log.info("Example 1");
        Flux.generate(synchronousSink -> {
                    log.info("invoked");
                    synchronousSink.next(1);
                    // synchronousSink can emit only one item
                    // synchronousSink.next(2);
                    // synchronousSink.next(3);
                    // synchronousSink.next(4);
                    synchronousSink.complete();
                })
                .take(2)
                .subscribe(new SubscriberImpl());
        // Subscribed
        // Received: 1
        // Completed

        log.info("Example 2");
        Flux.generate(synchronousSink -> {
                    log.info("invoked");
                    synchronousSink.next(faker.book().title());
                })
                .take(4)
                .subscribe(new SubscriberImpl());
        // Subscribed
        // Received: The Proper Study
        // Received: Great Work of Time
        // Received: The Wings of the Dove
        // Received: Endless Night
        // Completed

        log.info("Example 3");
        Flux.generate(synchronousSink -> {
                    synchronousSink.next(faker.book().title());
                })
                .takeUntil(b -> b.equals("Endless Night"))
                .subscribe(new SubscriberImpl());

        log.info("Example 4");
        Flux.generate(() -> 0,
                        (counter, synchronousSink) -> {
                            final String book = faker.book().title();
                            synchronousSink.next(book);
                            counter++;
                            if (counter == 10 || book.equalsIgnoreCase("Endless Night")) {
                                synchronousSink.complete();
                            }
                            return counter;
                        })
                .subscribe(new SubscriberImpl());

        log.info("Example 5");
        Flux.generate(() -> 0,
                        (counter, synchronousSink) -> {
                            final String book = faker.book().title();
                            synchronousSink.next(book);
                            counter++;
                            if (counter == 10 || book.equalsIgnoreCase("Endless Night")) {
                                synchronousSink.complete();
                            }
                            return counter;
                        },
                        counter -> log.info("Called onComplete, onError, onCancel, value: {}", counter)
                )
                .subscribe(new SubscriberImpl());

    }

}
