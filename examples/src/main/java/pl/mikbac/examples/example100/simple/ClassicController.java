package pl.mikbac.examples.example100.simple;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * Created by MikBac on 11.10.2025
 */
@RestController
@RequestMapping("classic/v1")
public class ClassicController {

    @GetMapping(path = "/users", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public List<UserModel> getUser() {
        return Stream.of("user1", "user2", "user3", "user4", "user5", "user6")
                .map(name -> new UserModel(UUID.randomUUID().toString(), name))
                .toList();
    }

}
