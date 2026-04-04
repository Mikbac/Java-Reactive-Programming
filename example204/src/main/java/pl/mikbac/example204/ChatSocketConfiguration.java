package pl.mikbac.example204;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;

import java.util.Map;

/**
 * Created by MikBac on 31.03.2026
 */

@Configuration
@RequiredArgsConstructor
public class ChatSocketConfiguration {

    private final ChatService chatService;

    @Bean
    public HandlerMapping handlerMapping(){
        Map<String, WebSocketHandler> map = Map.of(
                "/chat", chatService
        );
        return new SimpleUrlHandlerMapping(map, -1);
    }

}
