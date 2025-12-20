package pl.mikbac.examples.example075;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

@Slf4j
public class Main {

    @SneakyThrows
    public static void main(String[] args) {
        log.info("Example 1");
        getSampleContext()
                .contextWrite(Context.of("testKey", "testValue", "k2", "v2"))
                .subscribe(new SubscriberImpl<>("S1"));
        // [main] Context2{testKey=testValue, k2=v2}
        // [main] Subscribed [subscriberId=S1]
        // [main] Received [item=Value from context: testValue, subscriberId=S1]
        // [main] Completed [subscriberId=S1]

        log.info("Example 2");
        getSampleContext()
                .contextWrite(Context.of("testKey", "testValue", "k2", "v2"))
                .contextWrite(Context.of("c3", "d4").put("e6", "f7"))
                .subscribe(new SubscriberImpl<>("S1"));
        // [main] Context4{c3=d4, e6=f7, testKey=testValue, k2=v2}
        // [main] Subscribed [subscriberId=S1]
        // [main] Received [item=Value from context: testValue, subscriberId=S1]
        // [main] Completed [subscriberId=S1]

        log.info("Example 3");
        getSampleContext()
                .contextWrite(ctx -> Context.empty())
                .contextWrite(Context.of("testKey", "testValue", "k2", "v2"))
                .contextWrite(Context.of("c3", "d4").put("e6", "f7"))
                .subscribe(new SubscriberImpl<>("S1"));
        // [main] Context0{}
        // [main] Subscribed [subscriberId=S1]
        // [main] Context is empty
        //java.util.NoSuchElementException: Context is empty
        //	at reactor.util.context.Context0.get(Context0.java:43)
        //	at pl.mikbac.examples.example075.Main.lambda$getSampleContext$1(Main.java:43)
        //	at reactor.core.publisher.MonoDeferContextual.subscribe(MonoDeferContextual.java:47)
        //	at reactor.core.publisher.Mono.subscribe(Mono.java:4576)
        //	at pl.mikbac.examples.example075.Main.main(Main.java:37)
        // [main] Error

        log.info("Example 4");
        getSampleContext()
                .contextWrite(ctx ->  ctx.delete("c3"))
                .contextWrite(Context.of("testKey", "testValue", "k2", "v2"))
                .contextWrite(Context.of("c3", "d4").put("e6", "f7"))
                .subscribe(new SubscriberImpl<>("S1"));
        // [main] Context3{e6=f7, testKey=testValue, k2=v2}
        // [main] Subscribed [subscriberId=S1]
        // [main] Received [item=Value from context: testValue, subscriberId=S1]
        // [main] Completed [subscriberId=S1]
    }

    private static Mono<String> getSampleContext() {
        return Mono.deferContextual(ctx -> {
            log.info(ctx.toString());
            return Mono.just("Value from context: %s".formatted(ctx.get("testKey").toString()));
        });
    }
}
