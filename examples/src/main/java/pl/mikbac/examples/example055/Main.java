package pl.mikbac.examples.example055;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Map;
import java.util.function.UnaryOperator;

@Slf4j
public class Main {

    @SneakyThrows
    public static void main(String[] args) {
        log.info("Example - 1");
        Flux.zip(getProd1(), getProd2(), getProd3())
                .map(tup -> new Product(tup.getT1(), tup.getT2(), tup.getT3()))
                .transform(fluxLog("product"))
                .subscribe();
        // full:
        // 16:43:32.833 [main] Subscribed [prodId=product]
        // 16:43:32.837 [main] Subscribed [prodId=prod1]
        // 16:43:32.838 [main] Received [item=prodOne-1, prodId=prod1]
        // 16:43:32.843 [main] Subscribed [prodId=prod2]
        // 16:43:32.843 [main] Received [item=prodTwo-1, prodId=prod2]
        // 16:43:32.843 [main] Subscribed [prodId=prod3]
        // 16:43:32.843 [main] Received [item=1, prodId=prod3]
        // 16:43:33.847 [parallel-1] Received [item=prodOne-2, prodId=prod1]
        // 16:43:34.858 [parallel-2] Received [item=prodTwo-2, prodId=prod2]
        // 16:43:34.858 [parallel-4] Received [item=prodOne-3, prodId=prod1]
        // 16:43:34.858 [parallel-2] Completed [prodId=prod2]
        // 16:43:35.856 [parallel-3] Received [item=Product[name=prodOne-1, description=prodTwo-1, price=1], prodId=product]
        // 16:43:35.868 [parallel-3] Received [item=2, prodId=prod3]
        // 16:43:35.872 [parallel-6] Received [item=prodOne-4, prodId=prod1]
        // 16:43:36.883 [parallel-8] Received [item=prodOne-5, prodId=prod1]
        // 16:43:36.883 [parallel-8] Completed [prodId=prod1]
        // 16:43:38.877 [parallel-7] Received [item=Product[name=prodOne-2, description=prodTwo-2, price=2], prodId=product]
        // 16:43:38.877 [parallel-7] Cancelling  [prodId=prod3]
        // 16:43:38.877 [parallel-7] Completed [prodId=product]
        //
        // partial:
        // [main] Subscribed [prodId=product]
        // [parallel-3] Received [item=Product[name=prodOne-1, description=prodTwo-1, price=1], prodId=product]
        // [parallel-7] Received [item=Product[name=prodOne-2, description=prodTwo-2, price=2], prodId=product]
        // [parallel-7] Completed [prodId=product]


        Thread.sleep(10000);

        log.info("Example - 2");
        Flux.zip(Mono.just("1"), Mono.just("2"), Mono.just(BigDecimal.ONE))
                .map(tup -> new Product(tup.getT1(), tup.getT2(), tup.getT3()))
                .transform(fluxLog("product"))
                .subscribe();
        //01:28:31 [main] Subscribed [prodId=product]
        //01:28:31 [main] Received [item=Product[name=1, description=2, price=1], prodId=product]
        //01:28:31 [main] Completed [prodId=product]

        Thread.sleep(2000);

        log.info("Example - 3");
        Mono.just("1")
                .zipWhen(d -> Mono.just("2"))
                .zipWhen(d -> Mono.just("3"))
                .map(tup -> Map.of("z1", tup.getT1(), "z2", tup.getT2()))
                .log()
                .subscribe();
        //01:27:51.867 [main] | onSubscribe([Fuseable] FluxMapFuseable.MapFuseableSubscriber)
        //01:27:51.867 [main] | request(unbounded)
        //01:27:51.867 [main] | onNext({z1=[1,2], z2=3})
        //01:27:51.868 [main] | onComplete()

        Thread.sleep(2000);

    }

    private record Product(String name, String description, BigDecimal price) {}

    private static Flux<String> getProd1() {
        return Flux.range(1, 5)
                .map(i -> "prodOne-" + i)
                .transform(fluxLog("prod1"))
                .delayElements(Duration.ofMillis(1000));
    }

    private static Flux<String> getProd2() {
        return Flux.range(1, 2)
                .map(i -> "prodTwo-" + i)
                .transform(fluxLog("prod2"))
                .delayElements(Duration.ofMillis(2000));
    }

    private static Flux<BigDecimal> getProd3() {
        return Flux.range(1, 10)
                .map(BigDecimal::valueOf)
                .transform(fluxLog("prod3"))
                .delayElements(Duration.ofMillis(3000));
    }

    private static <T> UnaryOperator<Flux<T>> fluxLog(String prodId) {
        return flux -> flux
                .doOnSubscribe(s -> log.info("Subscribed [prodId={}]", prodId))
                .doOnNext(i -> log.info("Received [item={}, prodId={}]", i, prodId))
                .doOnCancel(() -> log.info("Cancelling  [prodId={}]", prodId))
                .doOnComplete(() -> log.info("Completed [prodId={}]", prodId));
    }
}
