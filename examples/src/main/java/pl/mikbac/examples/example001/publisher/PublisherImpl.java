package pl.mikbac.examples.example001.publisher;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

/**
 * Created by MikBac on 02.10.2025
 */
public class PublisherImpl implements Publisher<String> {

    @Override
    public void subscribe(Subscriber<? super String> subscriber) {
        final SubscriptionImpl subscription = new SubscriptionImpl(subscriber);
        subscriber.onSubscribe(subscription);
    }

}
