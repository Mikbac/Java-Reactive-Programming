package pl.mikbac.example200.testservices;

import lombok.RequiredArgsConstructor;
import org.redisson.api.RAtomicLongReactive;
import org.redisson.api.RedissonReactiveClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * Created by MikBac on 17.03.2026
 */

@Service
@RequiredArgsConstructor
public class SampleNumbers {

    private final RedissonReactiveClient redissonReactiveClient;

    public void incrementNumbers() {
        RAtomicLongReactive atomicLong = redissonReactiveClient.getAtomicLong("user:1:counter");
        Flux.range(1, 10)
                .flatMap(i -> atomicLong.incrementAndGet())
                .then(atomicLong.get())
                .doOnNext(System.out::println)
                .subscribe();
    }

    public void decrementNumbers() {
        RAtomicLongReactive atomicLong = redissonReactiveClient.getAtomicLong("user:1:counter");
        Flux.range(1, 10)
                .flatMap(i -> atomicLong.decrementAndGet())
                .then(atomicLong.get())
                .doOnNext(System.out::println)
                .subscribe();
    }

}
