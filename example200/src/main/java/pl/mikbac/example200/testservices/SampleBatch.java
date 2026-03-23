package pl.mikbac.example200.testservices;

import lombok.RequiredArgsConstructor;
import org.redisson.api.BatchOptions;
import org.redisson.api.RBatchReactive;
import org.redisson.api.RListReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.client.codec.LongCodec;
import org.springframework.stereotype.Service;

/**
 * Created by MikBac on 22.03.2026
 */

@Service
@RequiredArgsConstructor
public class SampleBatch {

    private final RedissonReactiveClient redissonReactiveClient;

    public void createBatch() {
        RBatchReactive batch = redissonReactiveClient.createBatch(BatchOptions.defaults());
        RListReactive<Long> list = batch.getList("numbers-batch", LongCodec.INSTANCE);
        for (long i = 0; i < 1_000_000; i++) {
            list.add(i);
        }
        batch.execute().subscribe();
    }
}
