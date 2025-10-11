package pl.mikbac.examples.example023;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * Created by MikBac on 02.10.2025
 */

@Slf4j
public class SubscriberSimplImpl implements Subscriber<String> {

    private Subscription subscription;

    public Subscription getSubscription() {
        return subscription;
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        this.subscription = subscription;
    }

    @Override
    public void onNext(String email) {
        log.info("Received: {}", email);
    }

    @Override
    public void onError(Throwable throwable) {
        log.error("Error");
    }

    @Override
    public void onComplete() {
        log.info("Completed");
    }
}
