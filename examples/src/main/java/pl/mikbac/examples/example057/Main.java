package pl.mikbac.examples.example057;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.UnaryOperator;

@Slf4j
public class Main {

    @SneakyThrows
    public static void main(String[] args) {
        log.info("Example - 1");
        getProductId("banana")
                .flatMapMany(Main::getProductLocalizations)
                .transform(fluxLog())
                .subscribe();
        // [main] Subscribed
        // [main] Received [item=ProductLocalization[city=New York, distance=1000]]
        // [main] Received [item=ProductLocalization[city=Paris, distance=100]]
        // [main] Completed
    }

    private record ProductLocalization(String city, BigDecimal distance) {
    }

    private static Mono<Integer> getProductId(String productName) {
        Map<String, Integer> products = Map.of("apple", 1,
                "banana", 2);
        return Mono.fromSupplier(() -> products.get(productName));
    }

    private static Flux<ProductLocalization> getProductLocalizations(Integer productId) {
        List<ProductLocalization> products = List.of(new ProductLocalization("New York", BigDecimal.valueOf(1000)),
                new ProductLocalization("Paris", BigDecimal.valueOf(100)));

        return Flux.fromIterable(products);
    }

    private static <T> UnaryOperator<Flux<T>> fluxLog() {
        return flux -> flux
                .doOnSubscribe(s -> log.info("Subscribed"))
                .doOnNext(i -> log.info("Received [item={}]", i))
                .doOnCancel(() -> log.info("Cancelling"))
                .doOnComplete(() -> log.info("Completed"));
    }
}
