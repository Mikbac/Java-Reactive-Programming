package pl.mikbac.examples.example065;

import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import pl.mikbac.examples.example062.SubscriberImpl;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class Main {

    private final static Faker faker = Faker.instance();

    @SneakyThrows
    public static void main(String[] args) {
        bookProd()
                .buffer(Duration.ofSeconds(10))
                .map(Main::groupBooks)
                .subscribe(new SubscriberImpl("S1"));

        Thread.sleep(60000);
    }

    private static Map<String, Integer> groupBooks(List<BookOrder> bookOrders) {
        return bookOrders.stream()
                .collect(Collectors.groupingBy(BookOrder::genre, Collectors.summingInt(BookOrder::price)));
    }

    private record BookOrder(String genre,
                             String title,
                             Integer price) {
        public static BookOrder create() {
            return new BookOrder(
                    faker.book().genre(),
                    faker.book().title(),
                    faker.random().nextInt(10, 100)
            );
        }
    }

    private static Flux<BookOrder> bookProd() {
        return Flux.interval(Duration.ofMillis(200))
                .map(i -> BookOrder.create());
    }

}
