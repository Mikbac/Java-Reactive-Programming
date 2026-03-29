package pl.mikbac.example202.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.mikbac.example202.model.UserModel;
import pl.mikbac.example202.service.UserServiceV1;
import reactor.core.publisher.Mono;

/**
 * Created by MikBac on 26.03.2026
 */

@RestController
@RequestMapping("users/v1")
@RequiredArgsConstructor
public class UserControllerV1 {

    private final UserServiceV1 userService;

    @GetMapping("{id}")
    public Mono<UserModel> getUser(@PathVariable int id){
        return userService.getUser(id);
    }

    @PutMapping("{id}")
    public Mono<UserModel> updateUser(@PathVariable int id, @RequestBody Mono<UserModel> userMono){
        return userService.updateUser(id, userMono);
    }
}
