package pl.mikbac.examples.example066;

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
        eventProd()
                .window(Duration.ofMillis(1000))
                .flatMap(Main::fluxProc)
                .subscribe(new SubscriberImpl("S1"));
        //*****
        //****
        //*****
        //******
        //****
        //******
        //*****

        Thread.sleep(60000);
    }

    private static Flux<String> eventProd() {
        return Flux.interval(Duration.ofMillis(200))
                .map(i -> "event-" + i);
    }

    private static Mono<Void> fluxProc(Flux<String> flux) {
        return flux.doOnNext(e -> System.out.print("*"))
                .doOnComplete(System.out::println)
                .then();
    }

}
