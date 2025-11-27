package pl.mikbac.examples.example054;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.function.UnaryOperator;

@Slf4j
public class Main {

    @SneakyThrows
    public static void main(String[] args) {
        log.info("Example - 1");
        Flux.merge(getProd1(), getProd2(), getProd3())
                .subscribe();
        // [main] Subscribed [prodId=prod1]
        // [main] Received [item=1, prodId=prod1]
        // [main] Subscribed [prodId=prod2]
        // [main] Received [item=100, prodId=prod2]
        // [main] Subscribed [prodId=prod3]
        // [main] Received [item=200, prodId=prod3]
        // [parallel-2] Received [item=101, prodId=prod2]
        // [parallel-1] Received [item=2, prodId=prod1]
        // [parallel-3] Received [item=201, prodId=prod3]
        // [parallel-6] Received [item=202, prodId=prod3]
        // [parallel-4] Received [item=102, prodId=prod2]
        // [parallel-5] Received [item=3, prodId=prod1]
        // [parallel-6] Completed [prodId=prod3]
        // [parallel-4] Completed [prodId=prod2]
        // [parallel-5] Completed [prodId=prod1]

        Thread.sleep(2000);

        log.info("Example - 2");
        getProd1().mergeWith(getProd2())
                .mergeWith(getProd3())
                .subscribe();
        // [main] Subscribed [prodId=prod1]
        // [main] Received [item=1, prodId=prod1]
        // [main] Subscribed [prodId=prod2]
        // [main] Received [item=100, prodId=prod2]
        // [main] Subscribed [prodId=prod3]
        // [main] Received [item=200, prodId=prod3]
        // [parallel-11] Received [item=101, prodId=prod2]
        // [parallel-10] Received [item=2, prodId=prod1]
        // [parallel-12] Received [item=201, prodId=prod3]
        // [parallel-3] Received [item=202, prodId=prod3]
        // [parallel-1] Received [item=102, prodId=prod2]
        // [parallel-2] Received [item=3, prodId=prod1]
        // [parallel-3] Completed [prodId=prod3]
        // [parallel-2] Completed [prodId=prod1]
        // [parallel-1] Completed [prodId=prod2]

        Thread.sleep(2000);

    }

    private static Flux<Integer> getProd1() {
        return Flux.just(1, 2, 3)
                .transform(fluxLog("prod1"))
                .delayElements(Duration.ofMillis(20));
    }

    private static Flux<Integer> getProd2() {
        return Flux.just(100, 101, 102)
                .transform(fluxLog("prod2"))
                .delayElements(Duration.ofMillis(20));
    }

    private static Flux<Integer> getProd3() {
        return Flux.just(200, 201, 202)
                .transform(fluxLog("prod3"))
                .delayElements(Duration.ofMillis(20));
    }

    private static <T> UnaryOperator<Flux<T>> fluxLog(String prodId) {
        return flux -> flux
                .doOnSubscribe(s -> log.info("Subscribed [prodId={}]", prodId))
                .doOnNext(i -> log.info("Received [item={}, prodId={}]", i, prodId))
                .doOnCancel(() -> log.info("Cancelling  [prodId={}]", prodId))
                .doOnComplete(() -> log.info("Completed [prodId={}]", prodId));
    }
}
