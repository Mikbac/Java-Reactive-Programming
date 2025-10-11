package pl.mikbac.examples.example001;

import lombok.extern.slf4j.Slf4j;
import pl.mikbac.examples.example001.publisher.PublisherImpl;
import pl.mikbac.examples.example001.subscriber.SubscriberImpl;

/**
 * Created by MikBac on 02.10.2025
 */

@Slf4j
public class Main {
    public static void main(String[] args) {
        log.info("------ Example I ------");
        exampleWithRequestsAndComplete();
        log.info("------ Example II ------");
        exampleWithRequestsAndCancel();
    }

    private static void exampleWithRequestsAndComplete() {
        var publisher = new PublisherImpl();
        var subscriber = new SubscriberImpl();
        publisher.subscribe(subscriber);

        subscriber.getSubscription().request(4);
        subscriber.getSubscription().request(5);
        subscriber.getSubscription().request(6);
    }

    private static void exampleWithRequestsAndCancel() {
        var publisher = new PublisherImpl();
        var subscriber = new SubscriberImpl();
        publisher.subscribe(subscriber);

        subscriber.getSubscription().request(5);
        subscriber.getSubscription().cancel();
        subscriber.getSubscription().request(5);
    }

}
