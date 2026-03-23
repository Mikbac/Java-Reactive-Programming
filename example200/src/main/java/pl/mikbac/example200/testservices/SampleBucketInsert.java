package pl.mikbac.example200.testservices;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.redisson.api.RBucketReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.client.codec.StringCodec;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Map;

/**
 * Created by MikBac on 16.03.2026
 */

@Service
@RequiredArgsConstructor
public class SampleBucketInsert {

    private final RedissonReactiveClient redissonReactiveClient;

    public void getSetAndGetValue() {
        RBucketReactive<String> bucket = redissonReactiveClient.getBucket("user:1:email", StringCodec.INSTANCE);

        // set publisher
        Mono<Void> setUserMono = bucket.set("test@email.com");
        // get publisher
        Mono<Void> getUserMono = bucket.get()
                .log()
                .doOnNext(System.out::println)
                .then();

        setUserMono.then(getUserMono)
                .subscribe();
    }

    public void getSetWithExpireAndGetValue() {
        RBucketReactive<String> bucket = redissonReactiveClient.getBucket("user:2:email", StringCodec.INSTANCE);

        // set publisher
        Mono<Void> setUserMono = bucket.set("test2@email.com", Duration.ofSeconds(15));
        // get publisher
        Mono<Void> getUserMono = bucket.get()
                .log()
                .doOnNext(System.out::println)
                .then();

        setUserMono.then(getUserMono)
                .subscribe();
    }

    @SneakyThrows
    public void getSetWithExpireAndExtendAndGetValue() {
        RBucketReactive<String> bucket = redissonReactiveClient.getBucket("user:3:email", StringCodec.INSTANCE);

        // set publisher
        Mono<Void> setUserMono = bucket.set("test3@email.com", Duration.ofSeconds(15));
        // get publisher
        Mono<Void> getUserMono = bucket.get()
                .log()
                .doOnNext(System.out::println)
                .then();

        setUserMono.then(getUserMono)
                .subscribe();

        Thread.sleep(6000);

        // get expiration time
        Mono<Long> expirationTime = bucket.remainTimeToLive();

        bucket.expire(Duration.ofSeconds(30))
                .then(expirationTime)
                .doOnNext(System.out::println)
                .subscribe();

    }

    public void getMultipleValues() {
        redissonReactiveClient.getBuckets()
                .set(Map.of("user:1:mail", "user1@mail.com",
                        "user:2:mail", "user2@mail.com",
                        "user:3:mail", "user3@mail.com"))
                .then(redissonReactiveClient.getBuckets().get("user:1:mail", "user:2:mail", "user:3:mail"))
                .doOnNext(System.out::println)
                .subscribe();
    }

}
