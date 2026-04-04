package pl.mikbac.example203;

import jakarta.annotation.PostConstruct;
import org.redisson.api.BatchOptions;
import org.redisson.api.RBatchReactive;
import org.redisson.api.RScoredSortedSetReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.client.codec.IntegerCodec;
import org.redisson.client.protocol.ScoredEntry;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by MikBac on 30.03.2026
 */

@Service
public class CounterService {

    private RedissonReactiveClient redissonReactiveClient;
    private Sinks.Many<Integer> sink;

    public CounterService(final RedissonReactiveClient redissonReactiveClient) {
        this.redissonReactiveClient = redissonReactiveClient;
        this.sink = Sinks.many().unicast().onBackpressureBuffer();
    }

    @PostConstruct
    private void init() {
        sink
                .asFlux()
                .buffer(Duration.ofSeconds(3))
                .map(l -> l.stream().collect(
                        Collectors.groupingBy(
                                Function.identity(),
                                Collectors.counting()
                        )
                ))
                .flatMap(this::updateBatch)
                .subscribe();
    }

    @Scheduled(fixedRate = 1_000)
    public void addVisit(){
        sink.tryEmitNext(ThreadLocalRandom.current().nextInt(0, 10));
    }

    public Mono<Map<Integer, Double>> getCounterStats(final int topN) {
        String format = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDate.now());
        RScoredSortedSetReactive<Integer> set = redissonReactiveClient.getScoredSortedSet("counter:" + format, IntegerCodec.INSTANCE);
        return set.entryRangeReversed(0, topN)
                .map(listSe -> listSe.stream().collect(
                        Collectors.toMap(
                                ScoredEntry::getValue,
                                ScoredEntry::getScore,
                                (a, b) -> a,
                                LinkedHashMap::new
                        )
                ));
    }

    private Mono<Void> updateBatch(Map<Integer, Long> map) {
        RBatchReactive batch = redissonReactiveClient.createBatch(BatchOptions.defaults());
        String format = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDate.now());
        RScoredSortedSetReactive<Integer> set = batch.getScoredSortedSet("counter:" + format, IntegerCodec.INSTANCE);
        return Flux.fromIterable(map.entrySet())
                .map(e -> set.addScore(e.getKey(), e.getValue()))
                .then(batch.execute())
                .then();
    }

}
