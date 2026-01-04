package pl.mikbac.examples.example100.employeeCRUD.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import pl.mikbac.examples.example100.employeeCRUD.dto.EmployeeDepartmentDto;
import pl.mikbac.examples.example100.employeeCRUD.model.DepartmentModel;
import pl.mikbac.examples.example100.employeeCRUD.model.EmployeeModel;
import reactor.core.publisher.Flux;

/**
 * Created by MikBac on 03.01.2026
 */

@Repository
public interface DepartmentRepository extends ReactiveCrudRepository<DepartmentModel, Integer> {

    @Query("""
            SELECT
                e.*
            FROM employee e
            INNER JOIN department d ON e.department_id = d.id
            WHERE
                d.name = :departmentName
            """)
    Flux<EmployeeModel> findEmployeeByDepartmentName(String departmentName);

    @Query("""
            SELECT
                e.email AS email,
                d.name AS department_name
            FROM employee e
            INNER JOIN department d ON e.department_id = d.id
            WHERE
                e.email = :employeeEmail
            """)
    Flux<EmployeeDepartmentDto> findEmployeeWithDepartment(String employeeEmail);

}
