package pl.mikbac.example202.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mikbac.example202.model.UserModel;
import reactor.core.publisher.Mono;

/**
 * Created by MikBac on 26.03.2026
 */

@Service
@AllArgsConstructor
public class UserServiceV3 {

    private UserLocalCacheService userLocalCacheService;

    public Mono<UserModel> getUser(int id) {
        return userLocalCacheService.get(id);
    }

    public Mono<UserModel> updateUser(int id, Mono<UserModel> userMono) {
        return userMono.flatMap(u -> userLocalCacheService.update(id, u));
    }

}
