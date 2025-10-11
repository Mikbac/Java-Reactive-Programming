package pl.mikbac.examples.example001.publisher;

import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * Created by MikBac on 02.10.2025
 */

@Slf4j
public class SubscriptionImpl implements Subscription {

    private static final int MAX_ITEMS = 10;

    private final Faker faker;
    private final Subscriber<? super String> subscriber;

    private boolean subscriptionCancelled;
    private int count = 0;

    public SubscriptionImpl(Subscriber<? super String> subscriber) {
        this.subscriber = subscriber;
        this.faker = Faker.instance();
    }

    @Override
    public void request(long requested) {
        if (subscriptionCancelled) {
            this.subscriber.onError(new RuntimeException("Subscription is canceled"));
            return;
        }

        log.info("Subscriber has requested {} items", requested);

        for (int i = 0; i < requested && count < MAX_ITEMS; i++) {
            count++;
            this.subscriber.onNext(this.faker.internet().emailAddress());
        }

        if (count == MAX_ITEMS) {
            log.info("No more data");
            this.subscriptionCancelled = true;
            this.subscriber.onComplete();
        }
    }

    @Override
    public void cancel() {
        log.info("Subscriber has cancelled");
        this.subscriptionCancelled = true;
    }

}
