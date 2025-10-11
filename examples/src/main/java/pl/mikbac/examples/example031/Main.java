package pl.mikbac.examples.example031;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

/**
 * Created by MikBac on 8.10.2025
 */
@Slf4j
public class Main {

    public static void main(String[] args) {

        var subscriber = new SubscriberImpl();
        Flux.<Integer>create(fluxSink -> {
            for (int i = 0; i < 10; i++) {
                log.info("generated: {}", i);
                fluxSink.next(i);
            }
            fluxSink.complete();
        }).subscribe(subscriber);

        subscriber.getSubscription().request(2);
        subscriber.getSubscription().request(2);
        subscriber.getSubscription().cancel();

    }

}
