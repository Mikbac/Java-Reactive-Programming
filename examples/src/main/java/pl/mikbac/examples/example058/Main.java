package pl.mikbac.examples.example058;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.UnaryOperator;

@Slf4j
public class Main {

    @SneakyThrows
    public static void main(String[] args) {
        log.info("Example - 1");
        getProducts()
                .flatMap(Main::getProductLocalizations)
                .transform(fluxLog())
                .subscribe();
        // [main] Subscribed
        // [main] Received [item=ProductLocalization[productName=apple, city=New York, distance=1000]]
        // [main] Received [item=ProductLocalization[productName=apple, city=Paris, distance=100]]
        // [main] Received [item=ProductLocalization[productName=banana, city=New York, distance=1000]]
        // [main] Received [item=ProductLocalization[productName=banana, city=Paris, distance=100]]
        // [main] Completed

    }

    private record ProductLocalization(String productName, String city, BigDecimal distance) {
    }

    private static Flux<String> getProducts() {
        return Flux.fromIterable(List.of("apple", "banana"));
    }

    private static Flux<ProductLocalization> getProductLocalizations(String productName) {
        List<ProductLocalization> products = List.of(new ProductLocalization(productName, "New York", BigDecimal.valueOf(1000)),
                new ProductLocalization(productName, "Paris", BigDecimal.valueOf(100)));

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
