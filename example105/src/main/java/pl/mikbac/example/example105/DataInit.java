package pl.mikbac.example.example105;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

/**
 * Created by MikBac on 08.03.2026
 */

@Component
@RequiredArgsConstructor
public class DataInit {

    private final UserRepository userRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        final var usersFlux = Flux.just("user1", "user2", "user3")
                .map(username -> new UserDocument(null, username))
                .flatMap(userRepository::save);

        userRepository.deleteAll()
                .thenMany(usersFlux)
                .subscribe();
    }

}
