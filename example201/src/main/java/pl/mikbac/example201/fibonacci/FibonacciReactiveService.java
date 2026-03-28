package pl.mikbac.example201.fibonacci;

import org.redisson.api.RMapCacheReactive;
import org.redisson.api.RMapReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * Created by MikBac on 25.03.2026
 */

@Service
public class FibonacciReactiveService {

    private final RMapReactive<Integer, Integer> fibonacciMap;
    private final RMapCacheReactive<Integer, Integer> fibonacciCacheMap;

    public FibonacciReactiveService(RedissonReactiveClient redissonReactiveClient) {
        fibonacciMap = redissonReactiveClient.getMap("fibonacci-reactive", new TypedJsonJacksonCodec(Integer.class, Integer.class));
        fibonacciCacheMap = redissonReactiveClient.getMapCache("fibonacci-reactive", new TypedJsonJacksonCodec(Integer.class, Integer.class));
    }

    public Mono<Integer> getFibonacciNumberV1(int index) {
        return fibonacciMap.get(index)
                .switchIfEmpty(Mono.fromSupplier(() -> calculateFibonacci(index))
                        .flatMap(i -> fibonacciMap.fastPut(index, i).thenReturn(i)));
    }

    public Mono<Integer> getFibonacciNumberV2(int index) {
        return fibonacciCacheMap.get(index)
                .switchIfEmpty(Mono.fromSupplier(() -> calculateFibonacci(index))
                        .flatMap(i -> fibonacciCacheMap.fastPut(index, i, 5, TimeUnit.SECONDS).thenReturn(i)));
    }

    private int calculateFibonacci(int index) {
        if (index < 2)
            return index;
        return calculateFibonacci(index - 1) + calculateFibonacci(index - 2);
    }

}
