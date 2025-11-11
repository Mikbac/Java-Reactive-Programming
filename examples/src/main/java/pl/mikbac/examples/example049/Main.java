package pl.mikbac.examples.example049;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class Main {

    @SneakyThrows
    public static void main(String[] args) {
        // import reactor.util.concurrent.Queues;
        // public static final int SMALL_BUFFER_SIZE = Math.max(16,
        //		Integer.parseInt(System.getProperty("reactor.bufferSize.small", "256")));
        System.setProperty("reactor.bufferSize.small", "16");

        var flex = Flux.generate(
                        () -> 0,
                        (i, sink) -> {
                            i++;
                            log.info("Generating: {}", i);
                            sink.next(i);
                            return i;
                        }
                )
                .cast(Integer.class)
                .subscribeOn(Schedulers.parallel());

        flex.publishOn(Schedulers.boundedElastic())
                .map(Main::compute)
                .subscribe(new SubscriberImpl("S1"));

        // [main] Subscribed [subscriberId=S1]
        // [parallel-1] Generating: 1
        // ...
        // [parallel-1] Generating: 16
        // [boundedElastic-1] Received [item=3, subscriberId=S1]
        // ...
        // [boundedElastic-1] Received [item=14, subscriberId=S1]
        // [parallel-1] Generating: 17
        // ...
        // [parallel-1] Generating: 28
        // [boundedElastic-1] Received [item=15, subscriberId=S1]
        // [boundedElastic-1] Received [item=12, subscriberId=S1]
        // [boundedElastic-1] Received [item=13, subscriberId=S1]
        // [boundedElastic-1] Received [item=18, subscriberId=S1]
        // [boundedElastic-1] Received [item=19, subscriberId=S1]
        // [boundedElastic-1] Received [item=16, subscriberId=S1]
        // [boundedElastic-1] Received [item=17, subscriberId=S1]

        Thread.sleep(20000);

    }

    @SneakyThrows
    private static int compute(int n) {
        Thread.sleep(1000);
        return n ^ 2;
    }

}
