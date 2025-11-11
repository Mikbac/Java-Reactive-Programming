package pl.mikbac.examples.example050;

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

        flex.limitRate(4)
                .publishOn(Schedulers.boundedElastic())
                .map(Main::compute)
                .subscribe(new SubscriberImpl("S1"));

        // [main] Subscribed [subscriberId=S1]
        // [parallel-1] Generating: 1
        // [parallel-1] Generating: 2
        // [parallel-1] Generating: 3
        // [parallel-1] Generating: 4
        // [boundedElastic-1] Received [item=3, subscriberId=S1]
        // [boundedElastic-1] Received [item=0, subscriberId=S1]
        // [parallel-1] Generating: 5
        // [parallel-1] Generating: 6
        // [parallel-1] Generating: 7
        // [boundedElastic-1] Received [item=1, subscriberId=S1]
        // [boundedElastic-1] Received [item=6, subscriberId=S1]
        // [boundedElastic-1] Received [item=7, subscriberId=S1]
        // [parallel-1] Generating: 8
        // [parallel-1] Generating: 9
        // [parallel-1] Generating: 10
        // [boundedElastic-1] Received [item=4, subscriberId=S1]

        Thread.sleep(20000);

    }

    @SneakyThrows
    private static int compute(int n) {
        Thread.sleep(1000);
        return n ^ 2;
    }

}
