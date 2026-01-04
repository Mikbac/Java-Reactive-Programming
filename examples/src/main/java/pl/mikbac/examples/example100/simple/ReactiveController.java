package pl.mikbac.examples.example100.simple;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.UUID;

/**
 * Created by MikBac on 11.10.2025
 */
@RestController
@RequestMapping("reactive/v1")
public class ReactiveController {

    @GetMapping(path = "/users")
    public Flux<UserModel> getUser() {
        return Flux.just("user1", "user2", "user3", "user4", "user5", "user6")
                .map(name -> new UserModel(UUID.randomUUID().toString(), name))
                .delayElements(Duration.ofSeconds(1));
    }

    @GetMapping(path = "/users/nwdj", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<UserModel> getUserNewlineDelimitedJSON() {
        return Flux.just("user1", "user2", "user3", "user4", "user5", "user6")
                .map(name -> new UserModel(UUID.randomUUID().toString(), name))
                .delayElements(Duration.ofSeconds(1));
    }

    @GetMapping(path = "/users/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<UserModel> getUserStream() {
        return Flux.just("user1", "user2", "user3", "user4", "user5", "user6")
                .map(name -> new UserModel(UUID.randomUUID().toString(), name))
                .delayElements(Duration.ofSeconds(1));
    }

}
