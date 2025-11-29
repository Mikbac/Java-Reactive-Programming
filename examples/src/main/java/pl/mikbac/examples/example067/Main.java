package pl.mikbac.examples.example067;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.GroupedFlux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Slf4j
public class Main {

    @SneakyThrows
    public static void main(String[] args) {
        Flux.range(1, 10)
                .delayElements(Duration.ofSeconds(1))
                .groupBy(i -> i % 2)
                .flatMap(Main::process)
                .subscribe();
        // [parallel-1] received [key=1, item=1]
        // [parallel-2] received [key=0, item=2]
        // [parallel-3] received [key=1, item=3]
        // [parallel-4] received [key=0, item=4]
        // [parallel-5] received [key=1, item=5]
        // [parallel-6] received [key=0, item=6]
        // [parallel-7] received [key=1, item=7]
        // [parallel-8] received [key=0, item=8]
        // [parallel-9] received [key=1, item=9]
        // [parallel-10] received [key=0, item=10]
        // [parallel-10] Completed [key=0]
        // [parallel-10] Completed [key=1]

        Thread.sleep(60000);
    }

    private static Mono<Void> process(final GroupedFlux<Integer, Integer> groupedFlux) {
        return groupedFlux.doOnNext(i -> log.info("received [key={}, item={}]", groupedFlux.key(), i))
                .doOnComplete(() -> log.info("Completed [key={}]", groupedFlux.key()))
                .then();
    }

}
