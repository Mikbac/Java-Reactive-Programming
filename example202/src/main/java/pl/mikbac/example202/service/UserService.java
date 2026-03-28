package pl.mikbac.example202.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mikbac.example202.model.UserModel;
import pl.mikbac.example202.repository.UserRepository;
import reactor.core.publisher.Mono;

/**
 * Created by MikBac on 26.03.2026
 */

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;

    public Mono<UserModel> getUser(int id) {
        return userRepository.findById(id);
    }

    public Mono<UserModel> updateUser(int id, Mono<UserModel> userMono) {
        return userRepository.findById(id)
                .flatMap(eu -> userMono.map(uu -> {
                            eu.setUsername(uu.getUsername());
                            eu.setEmail(uu.getEmail());
                            return eu;
                        })
                ).flatMap(userRepository::save);
    }

}
