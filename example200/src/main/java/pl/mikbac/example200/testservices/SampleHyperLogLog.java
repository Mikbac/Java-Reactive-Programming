package pl.mikbac.example200.testservices;

import lombok.RequiredArgsConstructor;
import org.redisson.api.RHyperLogLogReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.client.codec.LongCodec;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.LongStream;

/**
 * Created by MikBac on 20.03.2026
 */

@Service
@RequiredArgsConstructor
public class SampleHyperLogLog {

    private final RedissonReactiveClient redissonReactiveClient;

    public void createCounter() {
        // RHyperLogLogReactive in Redisson uses Redis HyperLogLog to asynchronously estimate the number of unique elements (not exact),
        // so count() may return approximate or unexpected values, especially if operations aren’t subscribed or datasets are small.
        RHyperLogLogReactive<Long> counter = redissonReactiveClient.getHyperLogLog("logLogCounter", LongCodec.INSTANCE);

        List<Long> list1 = LongStream.rangeClosed(1, 30000)
                .boxed()
                .toList();

        List<Long> list2 = LongStream.rangeClosed(1, 20000)
                .boxed()
                .toList();

        Flux.just(list1, list2)
                .flatMap(counter::addAll)
                .then(counter.count())
                .doOnNext(n -> System.out.println("Counter: " + n))
                .subscribe();
    }

}
