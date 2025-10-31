package pl.mikbac.examples.example043;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Slf4j
public class Main {

    @SneakyThrows
    public static void main(String[] args) {

        var flux = Flux.create(fluxSink -> {
                    fluxSink.next("aaa");
                    fluxSink.next("bbb");
                    fluxSink.next("ccc");
                    fluxSink.complete();
                })
                .delayElements(Duration.ofSeconds(1))
                .share();
        // share() - As long as there is at least one Subscriber this Flux will be subscribed and emitting data.
        // When all subscribers have cancelled it will cancel the source Flux.
        // (for hot publisher)

        flux.subscribe(new SubscriberImpl("S1"));
        Thread.sleep(2000);
        flux.subscribe(new SubscriberImpl("S2"));
        Thread.sleep(5000);
        // Subscribed [subscriberId=S1]
        // Received [item=aaa, subscriberId=S1]
        // Received [item=bbb, subscriberId=S1]
        // Subscribed [subscriberId=S2]
        // Received [item=ccc, subscriberId=S1]
        // Received [item=ccc, subscriberId=S2]
        // Completed [subscriberId=S1]
        // Completed [subscriberId=S2]

    }

}
