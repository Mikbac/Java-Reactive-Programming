package pl.mikbac.examples.example008;

import lombok.extern.slf4j.Slf4j;
import pl.mikbac.examples.example006.SubscriberImpl;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

/**
 * Created by MikBac on 02.10.2025
 */

@Slf4j
public class Main {

    public static void main(String[] args) {

        Mono.fromFuture(Main::getValue)
                .subscribe(new SubscriberImpl());

    }

    private static CompletableFuture<String> getValue(){
        return CompletableFuture.supplyAsync(() -> {
            log.info("generating value");
            return "aaa";
        });
    }
}
