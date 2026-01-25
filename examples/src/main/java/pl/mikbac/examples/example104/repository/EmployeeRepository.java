package pl.mikbac.examples.example104.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import pl.mikbac.examples.example104.model.EmployeeModel;

/**
 * Created by MikBac on 03.01.2026
 */

@Repository
public interface EmployeeRepository extends ReactiveCrudRepository<EmployeeModel, Integer> {

}
