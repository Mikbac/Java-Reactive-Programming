package pl.mikbac.examples.example056;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Map;
import java.util.function.UnaryOperator;

@Slf4j
public class Main {

    @SneakyThrows
    public static void main(String[] args) {
        log.info("Example - 1");
        getProductId("banana")
                .flatMap(Main::getProductPrice)
                .transform(monoLog())
                .subscribe();
        // [main] Subscribed
        // [main] Received [item=2.22]
    }

    private static Mono<Integer> getProductId(String productName) {
        Map<String, Integer> products = Map.of("apple", 1,
                "banana", 2);
        return Mono.fromSupplier(() -> products.get(productName));
    }

    private static Mono<BigDecimal> getProductPrice(Integer productId) {
        Map<Integer, BigDecimal> products = Map.of(1, BigDecimal.valueOf(1.1),
                2, BigDecimal.valueOf(2.22));
        return Mono.fromSupplier(() -> products.get(productId));
    }

    private static <T> UnaryOperator<Mono<T>> monoLog() {
        return mono -> mono
                .doOnSubscribe(s -> log.info("Subscribed"))
                .doOnNext(i -> log.info("Received [item={}]", i))
                .doOnCancel(() -> log.info("Cancelling]"));
    }
}
