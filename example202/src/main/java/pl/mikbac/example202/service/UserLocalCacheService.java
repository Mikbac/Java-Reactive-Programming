package pl.mikbac.example202.service;

import org.redisson.api.RLocalCachedMapReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.api.options.LocalCachedMapOptions;
import org.springframework.stereotype.Service;
import pl.mikbac.example202.model.UserModel;
import pl.mikbac.example202.repository.UserRepository;
import reactor.core.publisher.Mono;

/**
 * Created by MikBac on 28.03.2026
 */

@Service
public class UserLocalCacheService extends AbstractCacheTemplate<Integer, UserModel> {

    private final UserRepository userRepository;
    private final RLocalCachedMapReactive<Integer, UserModel> cacheMap;

    public UserLocalCacheService(final UserRepository userRepository, final RedissonReactiveClient redissonReactiveClient) {
        LocalCachedMapOptions<Integer, UserModel> localCachedMapOptions = LocalCachedMapOptions.<Integer, UserModel>name("user-local")
                .syncStrategy(LocalCachedMapOptions.SyncStrategy.UPDATE)
                .reconnectionStrategy(LocalCachedMapOptions.ReconnectionStrategy.CLEAR);
        this.userRepository = userRepository;
        this.cacheMap = redissonReactiveClient.getLocalCachedMap(localCachedMapOptions);
    }

    @Override
    protected Mono<UserModel> getFromSource(final Integer id) {
        return userRepository.findById(id);
    }

    @Override
    protected Mono<UserModel> getFromCache(final Integer id) {
        return cacheMap.get(id);
    }

    @Override
    protected Mono<UserModel> updateSource(final Integer id, final UserModel userModel) {
        return userRepository.findById(id)
                .doOnNext(u -> userModel.setId(u.getId()))
                .flatMap(p -> userRepository.save(userModel));
    }

    @Override
    protected Mono<UserModel> updateCache(final Integer id, final UserModel userModel) {
        return cacheMap.fastPut(id, userModel).thenReturn(userModel);
    }

    @Override
    protected Mono<Void> deleteFromSource(final Integer id) {
        return userRepository.deleteById(id);
    }

    @Override
    protected Mono<Void> deleteFromCache(final Integer id) {
        return cacheMap.fastRemove(id).then();
    }
}
