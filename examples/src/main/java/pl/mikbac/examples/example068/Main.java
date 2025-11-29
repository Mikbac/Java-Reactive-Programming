package pl.mikbac.examples.example068;

import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Map;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

@Slf4j
public class Main {

    private final static Faker faker = Faker.instance();

    private static final Map<String, UnaryOperator<Flux<Order>>> PROCESSORS = Map.of(
            "Clothing", clothingProcessing(),
            "Toys", toysProcessing()
    );

    @SneakyThrows
    public static void main(String[] args) {
        orderStream()
                .filter(canProcess())
                .groupBy(Order::category)
                .flatMap(gf -> gf.transform(processOrder(gf.key())))
                .subscribe(new SubscriberImpl("S1"));
        // [parallel-1] Received [item=Order[name=Durable Leather Keyboard, category=Clothing, price=53], subscriberId=S1]
        // [parallel-1] Received [item=Order[name=Durable Leather Keyboard-FREE, category=Clothing, price=0], subscriberId=S1]
        // [parallel-1] Received [item=Order[name=Incredible Rubber Computer, category=Clothing, price=59], subscriberId=S1]
        // [parallel-1] Received [item=Order[name=Incredible Rubber Computer-FREE, category=Clothing, price=0], subscriberId=S1]
        // [parallel-1] Received [item=Order[name=Rustic Concrete Bag, category=Clothing, price=64], subscriberId=S1]
        // [parallel-1] Received [item=Order[name=Rustic Concrete Bag-FREE, category=Clothing, price=0], subscriberId=S1]
        // [parallel-1] Received [item=Order[name=Intelligent Granite Clock, category=Clothing, price=42], subscriberId=S1]
        // [parallel-1] Received [item=Order[name=Intelligent Granite Clock-FREE, category=Clothing, price=0], subscriberId=S1]
        // [parallel-1] Received [item=Order[name=Synergistic Plastic Plate, category=Toys, price=180], subscriberId=S1]
        // [parallel-1] Received [item=Order[name=Enormous Concrete Car, category=Clothing, price=51], subscriberId=S1]
        // [parallel-1] Received [item=Order[name=Enormous Concrete Car-FREE, category=Clothing, price=0], subscriberId=S1]

        Thread.sleep(60000);
    }

    private record Order(String name, String category, Integer price) {
    }

    private static Flux<Order> orderStream() {
        return Flux.interval(Duration.ofMillis(200))
                .map(i -> new Order(faker.commerce().productName(), faker.commerce().department(), faker.random().nextInt(10, 100)));
    }

    public static Predicate<Order> canProcess() {
        return p -> PROCESSORS.containsKey(p.category());
    }

    public static UnaryOperator<Flux<Order>> processOrder(String category) {
        return PROCESSORS.get(category);
    }

    private static UnaryOperator<Flux<Order>> clothingProcessing() {
        return flux -> flux
                .flatMap(p -> getFreeClothingOrder(p).startWith(p));
    }

    private static Flux<Order> getFreeClothingOrder(Order order) {
        return Flux.just(new Order(
                order.name() + "-FREE",
                order.category(),
                0
        ));
    }

    private static UnaryOperator<Flux<Order>> toysProcessing() {
        return flux -> flux.map(p -> new Order(p.name(), p.category(), p.price * 2));
    }

}
