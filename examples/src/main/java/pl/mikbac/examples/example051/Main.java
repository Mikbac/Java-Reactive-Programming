package pl.mikbac.examples.example051;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class Main {

    @SneakyThrows
    public static void main(String[] args) {

        var flex = Flux.generate(
                        () -> 0,
                        (i, sink) -> {
                            if (i == 100) {
                                sink.complete();
                            }
                            try {
                                Thread.sleep(200);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }

                            i++;
                            log.info("Generating: {}", i);
                            sink.next(i);
                            return i;
                        }
                )
                .cast(Integer.class)
                .subscribeOn(Schedulers.parallel());

        // var flex1 = Flux.generate(
        //                 () -> 0,
        //                 (i, sink) -> {
        //                     if (i == 100) {
        //                         sink.complete();
        //                     }
        //                     try {
        //                         Thread.sleep(200);
        //                     } catch (InterruptedException e) {
        //                         throw new RuntimeException(e);
        //                     }
        //
        //                     i++;
        //                     log.info("Generating: {}", i);
        //                     sink.next(i);
        //                     return i;
        //                 }, FluxSink.OverflowStrategy.DROP
        //         )
        //         .cast(Integer.class)
        //         .subscribeOn(Schedulers.parallel());

        flex.onBackpressureBuffer()
                // .onBackpressureBuffer(10)
                // .onBackpressureError()
                // .onBackpressureDrop()
                // .onBackpressureLatest()
                .publishOn(Schedulers.boundedElastic())
                .map(Main::compute)
                .subscribe(new SubscriberImpl("S1"));

        // [main] Subscribed [subscriberId=S1]
        // [parallel-1] Generating: 1
        // [parallel-1] Generating: 2
        // [parallel-1] Generating: 3
        // [parallel-1] Generating: 4
        // [parallel-1] Generating: 5
        // [boundedElastic-1] Received [item=3, subscriberId=S1]
        // [parallel-1] Generating: 6
        // [parallel-1] Generating: 7
        // [parallel-1] Generating: 8
        // [parallel-1] Generating: 9
        // [parallel-1] Generating: 10
        // [boundedElastic-1] Received [item=0, subscriberId=S1]
        // [parallel-1] Generating: 11
        // [parallel-1] Generating: 12
        // ...

        Thread.sleep(20000);

    }

    @SneakyThrows
    private static int compute(int n) {
        Thread.sleep(1000);
        return n ^ 2;
    }

}
