package pl.mikbac.example200.testservices;

import lombok.RequiredArgsConstructor;
import org.redisson.api.RBucketReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.springframework.stereotype.Service;
import pl.mikbac.example200.dto.User;
import reactor.core.publisher.Mono;

/**
 * Created by MikBac on 16.03.2026
 */

@Service
@RequiredArgsConstructor
public class SampleObjectStore {

    private final RedissonReactiveClient redissonReactiveClient;

    public void insertObject() {
        RBucketReactive<User> bucket = this.redissonReactiveClient.getBucket("user:4", new TypedJsonJacksonCodec(User.class));
        Mono<Void> setMono = bucket.set(new User("test", "test@mail.com"));
        Mono<Void> getMono = bucket.get()
                .doOnNext(System.out::println)
                .then();
        setMono.then(getMono)
                .subscribe();
    }

}
