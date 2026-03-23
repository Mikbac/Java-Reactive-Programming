package pl.mikbac.example200.testservices;

import lombok.RequiredArgsConstructor;
import org.redisson.api.RPatternTopicReactive;
import org.redisson.api.RTopicReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.api.listener.PatternMessageListener;
import org.redisson.client.codec.StringCodec;
import org.springframework.stereotype.Service;

/**
 * Created by MikBac on 21.03.2026
 */

@Service
@RequiredArgsConstructor
public class SamplePubSub {

    private final RedissonReactiveClient redissonReactiveClient;

    public void subscribeTopic() {
        RTopicReactive topic = redissonReactiveClient.getTopic("message-topic-1", StringCodec.INSTANCE);
        // publish message-topic-1 test1
        topic.getMessages(String.class)
                .doOnNext(m -> System.out.println("Subscriber-1, message: " + m))
                .subscribe();

        topic.getMessages(String.class)
                .doOnNext(m -> System.out.println("Subscriber-2, message: " + m))
                .subscribe();
    }

    public void subscribePatternTopic() {
        RPatternTopicReactive patternTopic = redissonReactiveClient.getPatternTopic("message-topic-*", StringCodec.INSTANCE);
        // publish message-topic-2 test22
        // publish message-topic-3 test33
        patternTopic.addListener(String.class, new PatternMessageListener<String>() {
            @Override
            public void onMessage(final CharSequence pattern, final CharSequence channel, final String msg) {
                System.out.println("Subscriber-1, pattern: " + pattern + ", channel: " + channel + ", message: " + msg);
            }
        }).subscribe();

        patternTopic.addListener(String.class, new PatternMessageListener<String>() {
            @Override
            public void onMessage(final CharSequence pattern, final CharSequence channel, final String msg) {
                System.out.println("Subscriber-2, pattern: " + pattern + ", channel: " + channel + ", message: " + msg);
            }
        }).subscribe();

    }

    //        Flux.range(1, 10)
//                .delayElements(Duration.ofMillis(500))
//                .doOnNext(i -> System.out.println("Message: " + i))
//                .subscribe();

    //        topic.getMessages(String.class)
//                .doOnError(System.out::println)
//                .doOnNext(System.out::println)
//                .subscribe();

}
