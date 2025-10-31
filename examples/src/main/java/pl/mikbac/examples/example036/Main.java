package pl.mikbac.examples.example036;

import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

/**
 * Created by MikBac on 19.10.2025
 */
@Slf4j
public class Main {

    private final static Faker faker = Faker.instance();

    public static void main(String[] args) {
        log.info("Example 1 -- Cannot emit more than one data");
        Flux.range(1, 10)
                .handle((item, sink) -> {
                    sink.next("aaa");
                    sink.next("bbb");
                    sink.next("ccc");
                    sink.complete();
                })
                .subscribe(new SubscriberImpl());

        log.info("Example 2");
        Flux.range(1, 10)
                .handle((item, sink) -> {
                    sink.next("aaa");
                    sink.complete();
                })
                .subscribe(new SubscriberImpl());

        log.info("Example 3");
        Flux.range(1, 10)
                .handle((item, sink) -> {
                    if (item % 2 == 0) {
                        sink.next(0);
                    } else {
                        sink.next(1);
                    }
                })
                .cast(Integer.class)
                .subscribe(new SubscriberImpl());

        log.info("Example 4");
        Flux.range(1, 10)
                .handle((item, sink) -> {
                    if (item % 2 == 0) {
                        sink.next(0);
                    } else if (item == 5) {
                        sink.complete();
                    } else {
                        sink.next(1);
                    }
                })
                .cast(Integer.class)
                .subscribe(new SubscriberImpl());

        log.info("Example 5");
        Flux.range(1, 10)
                .handle((item, sink) -> {
                    if (item % 2 == 0) {
                        sink.next(0);
                    } else if (item == 5) {
                        sink.error(new RuntimeException("error"));
                    } else {
                        sink.next(1);
                    }
                })
                .cast(Integer.class)
                .subscribe(new SubscriberImpl());

        log.info("Example 6");
        Flux.generate(synchronousSink -> synchronousSink.next(faker.book().title()))
                .handle((item, sink) -> {
                    sink.next(item);
                    if (item.equals("Endless Night")) {
                        sink.complete();
                    }
                })
                .subscribe(new SubscriberImpl());
    }

}
