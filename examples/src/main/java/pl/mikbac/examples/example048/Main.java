package pl.mikbac.examples.example048;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class Main {

    @SneakyThrows
    public static void main(String[] args) {

        log.info("Example - 1");
        Flux.range(1, 10)
                .parallel(4)
                .runOn(Schedulers.parallel())
                .map(Main::compute)
                .map(n -> "new value -> " + n)
                .subscribe(new SubscriberImpl("S1"));
        // [main] Subscribed [subscriberId=S1]
        // [parallel-2] Received [item=new value -> 0, subscriberId=S1]
        // [parallel-2] Received [item=new value -> 3, subscriberId=S1]
        // [parallel-2] Received [item=new value -> 1, subscriberId=S1]
        // [parallel-2] Received [item=new value -> 6, subscriberId=S1]
        // [parallel-4] Received [item=new value -> 10, subscriberId=S1]
        // [parallel-4] Received [item=new value -> 7, subscriberId=S1]
        // [parallel-4] Received [item=new value -> 4, subscriberId=S1]
        // [parallel-4] Received [item=new value -> 5, subscriberId=S1]
        // [parallel-2] Received [item=new value -> 8, subscriberId=S1]
        // [parallel-2] Received [item=new value -> 11, subscriberId=S1]
        // [parallel-2] Completed [subscriberId=S1]

        Thread.sleep(4000);

        log.info("Example - 2");
        Flux.range(1, 10)
                .parallel(4)
                .runOn(Schedulers.parallel())
                .map(Main::compute)
                .sequential()
                .map(n -> "new value -> " + n)
                .subscribe(new SubscriberImpl("S1"));
        // [main] Subscribed [subscriberId=S1]
        // [parallel-6] Received [item=new value -> 0, subscriberId=S1]
        // [parallel-6] Received [item=new value -> 3, subscriberId=S1]
        // [parallel-6] Received [item=new value -> 1, subscriberId=S1]
        // [parallel-6] Received [item=new value -> 6, subscriberId=S1]
        // [parallel-6] Received [item=new value -> 4, subscriberId=S1]
        // [parallel-6] Received [item=new value -> 7, subscriberId=S1]
        // [parallel-6] Received [item=new value -> 5, subscriberId=S1]
        // [parallel-6] Received [item=new value -> 10, subscriberId=S1]
        // [parallel-5] Received [item=new value -> 11, subscriberId=S1]
        // [parallel-5] Received [item=new value -> 8, subscriberId=S1]
        // [parallel-5] Completed [subscriberId=S1]

        Thread.sleep(4000);
    }

    @SneakyThrows
    private static int compute(int n) {
        Thread.sleep(1000);
        return n ^ 2;
    }

}
