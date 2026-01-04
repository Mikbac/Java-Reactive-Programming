package pl.mikbac.examples.example100.employeeCRUD.databaseClient;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.r2dbc.core.DatabaseClient;
import pl.mikbac.examples.example100.employeeCRUD.AbstractTest;
import pl.mikbac.examples.example100.employeeCRUD.dto.EmployeeDepartmentDto;
import reactor.test.StepVerifier;

/**
 * Created by MikBac on 03.01.2026
 */

@Slf4j
public class SimpleTest extends AbstractTest {

    @Autowired
    private DatabaseClient client;

    @Test
    public void orderDetailsByProduct() {
        var query = """
                SELECT
                    e.email AS email,
                    d.name AS department_name
                FROM employee e
                INNER JOIN department d ON e.department_id = d.id
                WHERE
                    e.email = :employeeEmail
                """;
        this.client.sql(query)
                .bind("employeeEmail", "alice@mail.com")
                .mapProperties(EmployeeDepartmentDto.class)
                .all()
                .doOnNext(dto -> log.info("{}", dto))
                .as(StepVerifier::create)
                .assertNext(e -> {
                    Assertions.assertEquals("alice@mail.com", e.email());
                    Assertions.assertEquals("Human Resources", e.departmentName());
                })
                .expectComplete()
                .verify();
    }

}
