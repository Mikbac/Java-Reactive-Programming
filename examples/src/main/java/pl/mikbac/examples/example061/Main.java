package pl.mikbac.examples.example061;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.UnaryOperator;

@Slf4j
public class Main {

    @SneakyThrows
    public static void main(String[] args) {
        log.info("Example - 1");
        Flux.range(1, 10)
                .collectList()
                .transform(monoLog())
                .subscribe();

        // [main] Subscribed
        // [main] Received [item=[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]]

    }

    private static <T> UnaryOperator<Mono<T>> monoLog() {
        return mono -> mono
                .doOnSubscribe(s -> log.info("Subscribed"))
                .doOnNext(i -> log.info("Received [item={}]", i))
                .doOnCancel(() -> log.info("Cancelling]"));
    }
}
