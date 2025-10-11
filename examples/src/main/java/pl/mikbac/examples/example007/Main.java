package pl.mikbac.examples.example007;

import lombok.extern.slf4j.Slf4j;
import pl.mikbac.examples.example006.SubscriberImpl;
import reactor.core.publisher.Mono;

/**
 * Created by MikBac on 02.10.2025
 */

@Slf4j
public class Main {

    public static void main(String[] args) {
        getUser(1).subscribe(new SubscriberImpl());
        // Subscribed
        // User: 1
        // Completed
    }

    private static Mono<String> getUser(final int id) {
        // Executes a `Runnable` upon subscription and completes without emitting a value.
        return Mono.fromRunnable(() -> sendMessage(id));
    }

    private static void sendMessage(final int id) {
        log.info("User {} not found.", id);
    }
}
