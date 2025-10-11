package pl.mikbac.examples.example100;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.UUID;

/**
 * Created by MikBac on 11.10.2025
 */
@RestController
@RequestMapping("test/v1")
public class SampleController {

    @GetMapping(path = "/users", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<UserModel> getUser() {
        return Flux.just("user1", "user2", "user3", "user4", "user5", "user6")
                .map(name -> new UserModel(UUID.randomUUID().toString(), name));
    }

}
