package pl.mikbac.example202.service;

import reactor.core.publisher.Mono;

/**
 * Created by MikBac on 28.03.2026
 */
public abstract class AbstractCacheTemplate<KEY, ENTITY> {

    public Mono<ENTITY> get(KEY key) {
        return getFromCache(key)
                .switchIfEmpty(
                        getFromSource(key)
                                .flatMap(e -> updateCache(key, e))
                );
    }

    public Mono<ENTITY> update(KEY key, ENTITY entity) {
        return updateSource(key, entity)
                .flatMap(e -> deleteFromCache(key).thenReturn(e));
    }

    public Mono<Void> delete(KEY key) {
        return deleteFromSource(key)
                .then(deleteFromCache(key));
    }

    abstract protected Mono<ENTITY> getFromSource(KEY key);

    abstract protected Mono<ENTITY> getFromCache(KEY key);

    abstract protected Mono<ENTITY> updateSource(KEY key, ENTITY entity);

    abstract protected Mono<ENTITY> updateCache(KEY key, ENTITY entity);

    abstract protected Mono<Void> deleteFromSource(KEY key);

    abstract protected Mono<Void> deleteFromCache(KEY key);

}
