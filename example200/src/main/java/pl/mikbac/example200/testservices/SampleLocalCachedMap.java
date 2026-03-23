package pl.mikbac.example200.testservices;

import lombok.RequiredArgsConstructor;
import org.redisson.api.RLocalCachedMap;
import org.redisson.api.RedissonClient;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.api.options.LocalCachedMapOptions;
import org.springframework.stereotype.Service;
import pl.mikbac.example200.dto.User;
import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * Created by MikBac on 18.03.2026
 */

@Service
@RequiredArgsConstructor
public class SampleLocalCachedMap {

    private final RedissonReactiveClient redissonReactiveClient;
    private final RedissonClient redissonClient;

    public void checkLocalCachedMap() {
        LocalCachedMapOptions<Integer, User> mapOptions = LocalCachedMapOptions.<Integer, User>name("user10")
                .syncStrategy(LocalCachedMapOptions.SyncStrategy.UPDATE)
                .reconnectionStrategy(LocalCachedMapOptions.ReconnectionStrategy.CLEAR);
        // syncStrategy:
        // * NONE       --  No synchronizations on map changes.
        // * INVALIDATE --  Invalidate local cache entry across all LocalCachedMap instances on map entry change.
        //                  Broadcasts map entry hash (16 bytes) to all instances.
        // * UPDATE     --  Update local cache entry across all LocalCachedMap instances on map entry change.
        //                  Broadcasts full map entry state (Key and Value objects) to all instances.
        //
        // reconnectionStrategy:
        // * NONE   -- No reconnect handling.
        // * CLEAR  -- Clear local cache if map instance disconnected.
        // * LOAD   -- Store invalidated entry hash in invalidation log for 10 minutes.
        //             Cache keys for stored invalidated entry hashes will be removed if LocalCachedMap instance has been disconnected
        //             less than 10 minutes or whole local cache will be cleaned otherwise.

        RLocalCachedMap<Integer, User> usersMap = redissonClient.getLocalCachedMap(mapOptions);

        User userOne = new User("Alice", "alice@mail.com");
        User userTwo = new User("Bob", "bob@mail.com");

        usersMap.put(1, userOne);
        usersMap.put(2, userTwo);

        Flux.interval(Duration.ofSeconds(1))
                .doOnNext(i -> System.out.println(i + " ==> " + usersMap.get(1)))
                .subscribe();
    }

}
