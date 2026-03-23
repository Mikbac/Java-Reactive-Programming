package pl.mikbac.example200.testservices;

import lombok.RequiredArgsConstructor;
import org.redisson.api.RBlockingDequeReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.client.codec.LongCodec;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * Created by MikBac on 20.03.2026
 */

@Service
@RequiredArgsConstructor
public class SampleBlockingQueue {

    private final RedissonReactiveClient redissonReactiveClient;

    public void produceAndConsumeFromQueue() {
        RBlockingDequeReactive<Long> queue = redissonReactiveClient.getBlockingDeque("numberQueue", LongCodec.INSTANCE);
        Flux.range(1, 200)
                .delayElements(Duration.ofMillis(500))
                .doOnNext(i -> System.out.println("Producer: " + i))
                .flatMap(i -> queue.add(Long.valueOf(i)))
                .subscribe();

        queue.takeElements()
                .doOnNext(i -> System.out.println("Consumer: " + i))
                .doOnComplete(() -> System.out.println("Completed (never happens)"))
                .subscribe();
        // Producer: 1
        // Consumer: 1
        // Producer: 2
        // Consumer: 2
        // Producer: 3
        // Consumer: 3
        // Producer: 4
        // Consumer: 4
        // ...
    }

}
