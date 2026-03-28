package pl.mikbac.example202.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import pl.mikbac.example202.model.UserModel;

/**
 * Created by MikBac on 26.03.2026
 */

@Repository
public interface UserRepository extends ReactiveCrudRepository<UserModel, Integer> {

}
