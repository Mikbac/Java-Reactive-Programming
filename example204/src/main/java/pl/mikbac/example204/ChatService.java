package pl.mikbac.example204;

import lombok.RequiredArgsConstructor;
import org.redisson.api.RListReactive;
import org.redisson.api.RTopicReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.client.codec.StringCodec;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

/**
 * Created by MikBac on 31.03.2026
 */

@Service
@RequiredArgsConstructor
public class ChatService implements WebSocketHandler {
//    @Override
//    public List<String> getSubProtocols() {
//        return WebSocketHandler.super.getSubProtocols();
//    }

    private final RedissonReactiveClient redissonReactiveClient;

    @Override
    public Mono<Void> handle(final WebSocketSession session) {
        String chatRoom = getChatRoom(session);
        RTopicReactive topic = redissonReactiveClient.getTopic(chatRoom, StringCodec.INSTANCE);
        RListReactive<String> list = this.redissonReactiveClient.getList("history:" + chatRoom, StringCodec.INSTANCE);

        // subscribe, receives messages
        session.receive()
                .map(WebSocketMessage::getPayloadAsText)
                .flatMap(message -> list.add(message).then(topic.publish(message)))
                .doOnError(System.out::println)
                .doFinally(s -> System.out.println("Receiver/Subscriber finally: " + s))
                .subscribe();

        // publish, sends messages
        final Flux<WebSocketMessage> fluxMessages = topic.getMessages(String.class)
                .startWith(list.iterator())
                .doOnError(System.out::println)
                .doFinally(s -> System.out.println("Sender/Publisher finally: " + s))
                .map(session::textMessage);
        return session.send(fluxMessages);
    }

    private String getChatRoom(WebSocketSession session){
        URI uri = session.getHandshakeInfo().getUri();
        return UriComponentsBuilder.fromUri(uri)
                .build()
                .getQueryParams()
                .toSingleValueMap()
                .getOrDefault("room", "default");
    }

}
