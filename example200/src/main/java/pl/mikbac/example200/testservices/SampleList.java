package pl.mikbac.example200.testservices;

import lombok.RequiredArgsConstructor;
import org.redisson.api.RDequeReactive;
import org.redisson.api.RListReactive;
import org.redisson.api.RQueueReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.client.codec.LongCodec;
import org.redisson.client.codec.StringCodec;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Created by MikBac on 19.03.2026
 */

@Service
@RequiredArgsConstructor
public class SampleList {

    private final RedissonReactiveClient redissonReactiveClient;

    public void insertLongValuesIntoList() {
        // lrange numberList 0 -1
        RListReactive<Long> numberList = redissonReactiveClient.getList("numberList", LongCodec.INSTANCE);
        Flux.range(1, 20)
                .map(Long::valueOf)
                .flatMap(numberList::add)
                .subscribe();
    }

    public void insertStringValuesIntoList() {
        // lrange stringList 0 -1
        RListReactive<String> stringList = redissonReactiveClient.getList("stringList", StringCodec.INSTANCE);
        stringList.addAll(List.of("aaa", "bbb", "ccc"))
                .subscribe();
    }

    public void produceAndConsumeFromQueue() {
        RListReactive<Long> numberList = redissonReactiveClient.getList("numberList2", LongCodec.INSTANCE);
        Mono<Boolean> insertMono = numberList.addAll(List.of(1L, 2L, 3L, 4L, 5L, 6L, 7L));

        RQueueReactive<Long> queue = redissonReactiveClient.getQueue("numberList2", LongCodec.INSTANCE);
        Flux<Long> queuePull = queue.poll()
                .doOnNext(i -> System.out.println("Consumer: " + i))
                .repeat(3);

        insertMono.thenMany(queuePull)
                .subscribe();
        // Consumer: 1
        // Consumer: 2
        // Consumer: 3
        // Consumer: 4
    }

    public void produceAndConsumeFromStack() {
        RListReactive<Long> numberList = redissonReactiveClient.getList("numberList3", LongCodec.INSTANCE);
        Mono<Boolean> insertMono = numberList.addAll(List.of(1L, 2L, 3L, 4L, 5L, 6L, 7L));

        RDequeReactive<Long> stack = redissonReactiveClient.getDeque("numberList3", LongCodec.INSTANCE);
        Flux<Long> queuePull = stack.pollLast()
                .doOnNext(i -> System.out.println("Consumer: " + i))
                .repeat(3);

        insertMono.thenMany(queuePull)
                .subscribe();
        // Consumer: 7
        // Consumer: 6
        // Consumer: 5
        // Consumer: 4
    }

}
