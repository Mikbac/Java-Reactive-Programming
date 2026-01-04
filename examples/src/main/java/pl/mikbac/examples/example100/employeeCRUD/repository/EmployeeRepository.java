package pl.mikbac.examples.example100.employeeCRUD.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import pl.mikbac.examples.example100.employeeCRUD.model.EmployeeModel;
import reactor.core.publisher.Flux;

/**
 * Created by MikBac on 03.01.2026
 */

@Repository
public interface EmployeeRepository extends ReactiveCrudRepository<EmployeeModel, Integer> {

    Flux<EmployeeModel> findByEmail(String email);

    Flux<EmployeeModel> findByEmailEndsWith(String domain);

    Flux<EmployeeModel> findBy(Pageable pageable);

}
