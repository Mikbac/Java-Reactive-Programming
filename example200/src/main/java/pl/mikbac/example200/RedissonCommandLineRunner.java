package pl.mikbac.example200;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import pl.mikbac.example200.testservices.SampleBatch;
import pl.mikbac.example200.testservices.SampleBlockingQueue;
import pl.mikbac.example200.testservices.SampleBucketInsert;
import pl.mikbac.example200.testservices.SampleEvents;
import pl.mikbac.example200.testservices.SampleHyperLogLog;
import pl.mikbac.example200.testservices.SampleList;
import pl.mikbac.example200.testservices.SampleLocalCachedMap;
import pl.mikbac.example200.testservices.SampleMap;
import pl.mikbac.example200.testservices.SampleNumbers;
import pl.mikbac.example200.testservices.SampleObjectStore;
import pl.mikbac.example200.testservices.SamplePubSub;
import pl.mikbac.example200.testservices.SampleSortedSet;
import pl.mikbac.example200.testservices.SampleTransaction;

/**
 * Created by MikBac on 16.03.2026
 */

@Service
@RequiredArgsConstructor
public class RedissonCommandLineRunner implements CommandLineRunner {

    private final SampleBucketInsert sampleBucketInsert;
    private final SampleObjectStore sampleObjectStore;
    private final SampleNumbers sampleNumbers;
    private final SampleEvents sampleEvents;
    private final SampleMap sampleMap;
    private final SampleLocalCachedMap sampleLocalCachedMap;
    private final SampleList sampleList;
    private final SampleBlockingQueue sampleBlockingQueue;
    private final SampleHyperLogLog sampleHyperLogLog;
    private final SamplePubSub samplePubSub;
    private final SampleBatch sampleBatch;
    private final SampleTransaction sampleTransaction;
    private final SampleSortedSet sampleSortedSet;

    @Override
    public void run(final String... args) {
//        sampleBucketInsert.getSetAndGetValue();
//        sampleBucketInsert.getSetWithExpireAndGetValue();
//        sampleBucketInsert.getSetWithExpireAndExtendAndGetValue();
//        sampleBucketInsert.getMultipleValues();

//        sampleObjectStore.insertObject();

//        sampleNumbers.incrementNumbers();
//        sampleNumbers.decrementNumbers();

//        sampleEvents.expiredEventListener();
//        sampleEvents.deletedEventListener();

//        sampleMap.putIntoMap();
//        sampleMap.putAllIntoMap();
//        sampleMap.putUsersIntoMap();
//        sampleMap.putUsersIntoMapCache();

//        sampleLocalCachedMap.checkLocalCachedMap();

//        sampleList.insertLongValuesIntoList();
//        sampleList.insertStringValuesIntoList();
//        sampleList.produceAndConsumeFromQueue();
//        sampleList.produceAndConsumeFromStack();

//        sampleBlockingQueue.produceAndConsumeFromQueue();

//        sampleHyperLogLog.createCounter();

//        samplePubSub.subscribeTopic();
//        samplePubSub.subscribePatternTopic();

//        sampleBatch.createBatch();

//        sampleTransaction.createTransaction();

//        sampleSortedSet.addToSortedSet();
        sampleSortedSet.createPriorityQueue();

    }

}
