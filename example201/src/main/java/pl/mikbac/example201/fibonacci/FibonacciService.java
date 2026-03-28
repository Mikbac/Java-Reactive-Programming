package pl.mikbac.example201.fibonacci;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Created by MikBac on 24.03.2026
 */

@Service
@Slf4j
public class FibonacciService {

    // 127.0.0.1:6379> keys *
    // 1) "fibonacci"
    //
    // 127.0.0.1:6379> type fibonacci
    // hash
    //
    // 127.0.0.1:6379> hgetall fibonacci
    // 1) "\x02\x0e"
    // 2) "\x02\x1a"
    // 3) "\x02P"
    // 4) "\x02\x96\xfb\xcba"
    // 5) "\x02d"
    // 6) "\x02\xbd\x9a\xe6\x9c\x02"
    @Cacheable(value = "fibonacci")
    public int getFibonacciNumber(int index) {
        log.info("Get Fibonacci number for: {}", index);
        return calculateFibonacci(index);
    }

    @Cacheable(cacheNames = "fibonacci", key = "#index")
    public int getFibonacciNumber(int index, String additionalKey) {
        log.info("Get Fibonacci number for: {}", index);
        return calculateFibonacci(index);
    }

    @CachePut(cacheNames = "fibonacci", key = "#index")
    public int updateFibonacciCache(int index) {
        log.info("Update Fibonacci cache");
        return calculateFibonacci(index);
    }

    @Scheduled(fixedRate = 5_000)
    @CacheEvict(value = "fibonacci", allEntries = true)
    public void evictCache() {
        log.info("Scheduled cache eviction");
    }

    @CacheEvict(cacheNames = "fibonacci", key = "#index") // happens after method execution
    public void evictCache(int index) {
        log.info("Evict index cache index={}", index);
    }

    private int calculateFibonacci(int index) {
        if (index < 2)
            return index;
        return calculateFibonacci(index - 1) + calculateFibonacci(index - 2);
    }

}
