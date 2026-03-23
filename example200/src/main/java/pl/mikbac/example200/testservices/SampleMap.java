package pl.mikbac.example200.testservices;

import lombok.RequiredArgsConstructor;
import org.redisson.api.RMapCacheReactive;
import org.redisson.api.RMapReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.client.codec.StringCodec;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.springframework.stereotype.Service;
import pl.mikbac.example200.dto.User;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by MikBac on 17.03.2026
 */

@Service
@RequiredArgsConstructor
public class SampleMap {

    private final RedissonReactiveClient redissonReactiveClient;

    public void putIntoMap() {
        RMapReactive<String, String> userMap = this.redissonReactiveClient.getMap("user:7", StringCodec.INSTANCE);

        Mono<String> putUsernameMono = userMap.put("username", "Bob");
        Mono<String> putEmailMono = userMap.put("email", "1@mail.com");

        Mono<String> getUsernameMono = userMap.get("username");
        Mono<String> getEmailMono = userMap.get("email");

        putUsernameMono.then(putEmailMono)
                .then(getUsernameMono)
                .doOnNext(System.out::println)
                .then(getEmailMono)
                .doOnNext(System.out::println)
                .subscribe();
    }

    public void putAllIntoMap() {
        RMapReactive<String, String> userMap = this.redissonReactiveClient.getMap("user:8", StringCodec.INSTANCE);

        Mono<Void> putUsernameMono = userMap.putAll(Map.of("username", "Alice", "email", "2@mail.com"));

        Mono<String> getUsernameMono = userMap.get("username");
        Mono<String> getEmailMono = userMap.get("email");

        putUsernameMono
                .then(getUsernameMono)
                .doOnNext(System.out::println)
                .then(getEmailMono)
                .doOnNext(System.out::println)
                .subscribe();
    }

    public void putUsersIntoMap() {
        TypedJsonJacksonCodec codec = new TypedJsonJacksonCodec(Integer.class, User.class);
        RMapReactive<Integer, User> map = redissonReactiveClient.getMap("users", codec);

        User userOne = new User("Alice", "alice@mail.com");
        User userTwo = new User("Bob", "bob@mail.com");

        map.put(1, userOne)
                .then(map.put(2, userTwo))
                .subscribe();
        // hgetall users
        // 1) "1"
        // 2) "{\"email\":\"alice@mail.com\",\"username\":\"Alice\"}"
        // 3) "2"
        // 4) "{\"email\":\"bob@mail.com\",\"username\":\"Bob\"}"
        //
        // hget users 1
        // "{\"email\":\"alice@mail.com\",\"username\":\"Alice\"}"
    }

    public void putUsersIntoMapCache() {
        TypedJsonJacksonCodec codec = new TypedJsonJacksonCodec(Integer.class, User.class);
        RMapCacheReactive<Integer, User> mapCache = redissonReactiveClient.getMapCache("users:cache", codec);

        User userOne = new User("Alice", "alice@mail.com");
        User userTwo = new User("Bob", "bob@mail.com");

        Mono<User> userMonoOne = mapCache.put(1, userOne, 5, TimeUnit.SECONDS);
        Mono<User> userMonoTwo = mapCache.put(2, userTwo, 50, TimeUnit.SECONDS);

        userMonoOne.then(userMonoTwo)
                .then(mapCache.get(1))
                .doOnNext(System.out::println)
                .then(mapCache.get(2))
                .doOnNext(System.out::println)
                .subscribe();
        // hgetall users:cache
    }

}
