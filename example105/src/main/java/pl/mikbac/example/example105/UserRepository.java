package pl.mikbac.example.example105;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by MikBac on 08.03.2026
 */

@Repository
public interface UserRepository extends ReactiveCrudRepository<UserDocument, String> {
}

