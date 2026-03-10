package pl.mikbac.example.example105;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * Created by MikBac on 08.03.2026
 */

@Configuration
@RequiredArgsConstructor
public class RouterConfig {

    @Bean
    public RouterFunction<ServerResponse> usersRoutes(UserRepository userRepository) {
        return RouterFunctions.route()
                .GET("/users", request -> ServerResponse.ok().body(userRepository.findAll(), UserDocument.class))
                .build();
    }
}
