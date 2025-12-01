package pl.mikbac.examples.example069;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * Created by MikBac on 09.10.2025
 */

@Slf4j
public class SubscriberImpl<T> implements Subscriber<T> {

    private String subscriberId;

    public SubscriberImpl(String subscriberId) {
        this.subscriberId = subscriberId;
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        log.info("Subscribed [subscriberId={}]", subscriberId);
        subscription.request(Long.MAX_VALUE);
    }

    @Override
    public void onNext(T item) {
        log.info("Received [item={}, subscriberId={}]", item, subscriberId);
    }

    @Override
    public void onError(Throwable throwable) {
        log.error(throwable.getMessage(), throwable);
        log.error("Error");
    }

    @Override
    public void onComplete() {
        log.info("Completed [subscriberId={}]", subscriberId);
    }
}
