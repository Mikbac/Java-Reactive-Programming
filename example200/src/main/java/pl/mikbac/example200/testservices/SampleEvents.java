package pl.mikbac.example200.testservices;

import lombok.RequiredArgsConstructor;
import org.redisson.api.DeletedObjectListener;
import org.redisson.api.ExpiredObjectListener;
import org.redisson.api.RBucketReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.client.codec.StringCodec;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * Created by MikBac on 17.03.2026
 */

// By default event notifications are disabled.
// https://redis.io/docs/latest/develop/pubsub/keyspace-notifications/
// e.g.
// config set notify-keyspace-events AKE
@Service
@RequiredArgsConstructor
public class SampleEvents {

    private final RedissonReactiveClient redissonReactiveClient;

    public void expiredEventListener() {
        RBucketReactive<String> bucket = redissonReactiveClient.getBucket("user:2:email", StringCodec.INSTANCE);
        Mono<Void> setUserMono = bucket.set("test2@email.com", Duration.ofSeconds(15));
        Mono<Void> getUserMono = bucket.get()
                .log()
                .doOnNext(System.out::println)
                .then();

        Mono<Void> eventListener = bucket.addListener(new ExpiredObjectListener() {
            @Override
            public void onExpired(final String name) {
                System.out.println("Expired: " + name);
            }
        }).then();

        setUserMono.then(getUserMono)
                .then(eventListener)
                .subscribe();
    }

    public void deletedEventListener() {
        RBucketReactive<String> bucket = redissonReactiveClient.getBucket("user:5:email", StringCodec.INSTANCE);
        Mono<Void> setUserMono = bucket.set("test5@email.com");
        Mono<Void> getUserMono = bucket.get()
                .log()
                .doOnNext(System.out::println)
                .then();
        Mono<Boolean> delUserMono = bucket.delete();

        Mono<Void> eventListener = bucket.addListener(new DeletedObjectListener() {
            @Override
            public void onDeleted(final String name) {
                System.out.println("Deleted: " + name);
            }
        }).then();

        setUserMono.then(getUserMono)
                .then(eventListener)
                .then(delUserMono)
                .subscribe();
    }

}
