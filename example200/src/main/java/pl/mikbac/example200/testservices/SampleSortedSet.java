package pl.mikbac.example200.testservices;

import lombok.RequiredArgsConstructor;
import org.redisson.api.RScoredSortedSetReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.client.codec.StringCodec;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.springframework.stereotype.Service;
import pl.mikbac.example200.dto.UserWithPriority;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.function.Function;

/**
 * Created by MikBac on 23.03.2026
 */

@Service
@RequiredArgsConstructor
public class SampleSortedSet {

    private final RedissonReactiveClient redissonReactiveClient;

    public void addToSortedSet() {
        RScoredSortedSetReactive<String> sortedSet = redissonReactiveClient.getScoredSortedSet("set-scores", StringCodec.INSTANCE);

        sortedSet.addScore("aaa", 1)
                .then(sortedSet.add(2, "bbb"))
                .then(sortedSet.addScore("ccc", 3))
                .then(sortedSet.entryRange(0, 2))
                .flatMapIterable(Function.identity())
                .map(s -> s.getScore() + " " + s.getValue())
                .doOnNext(System.out::println)
                .subscribe();
    }

    public void createPriorityQueue() {
        // ZRANGE users-priority 0 -1 WITHSCORES
        RScoredSortedSetReactive<UserWithPriority> sortedSet = redissonReactiveClient.getScoredSortedSet("users-priority", new TypedJsonJacksonCodec(UserWithPriority.class));

        Flux.interval(Duration.ofSeconds(1))
                .map(n -> n + 1000)
                .flatMap(i -> {
                    var u1 = new UserWithPriority(i + "aaa", UserWithPriority.Category.PRIME);
                    var u2 = new UserWithPriority(i + "bbb", UserWithPriority.Category.GUEST);
                    var u3 = new UserWithPriority(i + "ccc", UserWithPriority.Category.GUEST);

                    return Flux.just(u1, u2, u3)
                            .flatMap(u -> sortedSet.add(u.category().getPriority(), u));
                })
                .subscribe();

        sortedSet.takeFirstElements().limitRate(1)
                .delayElements(Duration.ofSeconds(1))
                .doOnNext(System.out::println)
                .subscribe();

    }

}
