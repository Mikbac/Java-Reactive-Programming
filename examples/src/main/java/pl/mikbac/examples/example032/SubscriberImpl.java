package pl.mikbac.examples.example032;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * Created by MikBac on 02.10.2025
 */

@Slf4j
public class SubscriberImpl<T> implements Subscriber<T> {

    private Subscription subscription;

    public Subscription getSubscription() {
        return subscription;
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        this.subscription = subscription;
    }

    @Override
    public void onNext(T item) {
        log.info("Received: {}", item);
    }

    @Override
    public void onError(Throwable throwable) {
        log.error(throwable.getMessage(), throwable);
        log.error("Error");
    }

    @Override
    public void onComplete() {
        log.info("Completed");
    }
}
