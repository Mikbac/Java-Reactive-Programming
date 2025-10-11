package pl.mikbac.examples.example006;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * Created by MikBac on 02.10.2025
 */

@Slf4j
public class SubscriberImpl implements Subscriber<String> {

    @Override
    public void onSubscribe(Subscription subscription) {
        log.info("Subscribed");
        subscription.request(Long.MAX_VALUE);
    }

    @Override
    public void onNext(String text) {
        log.info("Received: {}", text);
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
