package pl.mikbac.example200.testservices;

import lombok.RequiredArgsConstructor;
import org.redisson.api.RBucketReactive;
import org.redisson.api.RTransactionReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.api.TransactionOptions;
import org.redisson.client.codec.LongCodec;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * Created by MikBac on 22.03.2026
 */

@Service
@RequiredArgsConstructor
public class SampleTransaction {

    private final RedissonReactiveClient redissonReactiveClient;

    public void createTransaction() {
        // set balance:1 30
        // set balance:2 0
        //
        // get balance:1
        // get balance:2

        RTransactionReactive transaction = redissonReactiveClient.createTransaction(TransactionOptions.defaults());
        RBucketReactive<Long> fromBalance = transaction.getBucket("balance:1", LongCodec.INSTANCE);
        RBucketReactive<Long> toBalance = transaction.getBucket("balance:2", LongCodec.INSTANCE);

        int amount = 10;
        Flux.zip(fromBalance.get(), toBalance.get())
                .filter(t -> t.getT1() >= amount)
                .flatMap(t -> fromBalance.set(t.getT1() - amount).thenReturn(t))
                .flatMap(t -> toBalance.set(t.getT2() + amount))
                .then()
//                .thenReturn(0) // to cause an error
//                .map(n -> 2 / n) // to cause an error
                .then(transaction.commit())
                .onErrorResume(ex -> transaction.rollback())
                .subscribe();

    }

}
