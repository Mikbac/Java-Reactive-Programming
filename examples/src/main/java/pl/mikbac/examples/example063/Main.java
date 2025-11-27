package pl.mikbac.examples.example063;

import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import pl.mikbac.examples.example062.SubscriberImpl;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Slf4j
public class Main {

    private final static Faker faker = Faker.instance();

    @SneakyThrows
    public static void main(String[] args) {
        log.info("Example - 1");
        getProductsIds()
                .flatMap(Main::getProductInformation)
                .subscribe(new SubscriberImpl("S1"));

        // [main] Subscribed [subscriberId=S1]
        // [parallel-1] Received [item=Product[id=1, name=Ergonomic Paper Computer, stock=7, price=12.86], subscriberId=S1]
        // [parallel-2] Received [item=Product[id=2, name=Gorgeous Plastic Clock, stock=4, price=64.78], subscriberId=S1]
        // [parallel-3] Received [item=Product[id=3, name=Gorgeous Concrete Clock, stock=6, price=62.48], subscriberId=S1]
        // [parallel-4] Received [item=Product[id=4, name=Ergonomic Wool Car, stock=4, price=89.51], subscriberId=S1]
        // [parallel-5] Received [item=Product[id=5, name=Ergonomic Plastic Shoes, stock=3, price=88.85], subscriberId=S1]
        // [parallel-5] Completed [subscriberId=S1]

        Thread.sleep(1000);

    }

    private record Product(Integer id, String name, Integer stock, String price) {
    }

    private static Flux<Integer> getProductsIds() {
        return Flux.range(1, 5)
                .delayElements(Duration.ofMillis(100));
    }

    private static Mono<Product> getProductInformation(int id) {
        return Mono.zip(
                        getProductName(id),
                        getProductStock(id),
                        getProductPrice(id)
                )
                .map(tuple -> new Product(id, tuple.getT1(), tuple.getT2(), tuple.getT3()));
    }

    private static Mono<String> getProductPrice(int id) {
        return Mono.just(faker.commerce().price());
    }

    private static Mono<Integer> getProductStock(int id) {
        return Mono.just(faker.number().numberBetween(1, 10));
    }

    private static Mono<String> getProductName(int id) {
        return Mono.just(faker.commerce().productName());
    }

}
