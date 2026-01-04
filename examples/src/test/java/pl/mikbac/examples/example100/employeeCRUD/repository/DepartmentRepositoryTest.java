package pl.mikbac.examples.example100.employeeCRUD.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.mikbac.examples.example100.employeeCRUD.AbstractTest;
import reactor.test.StepVerifier;

/**
 * Created by MikBac on 03.01.2026
 */

@Slf4j
class DepartmentRepositoryTest extends AbstractTest {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Test
    public void findEmployeeByDepartmentName() {
        this.departmentRepository.findEmployeeByDepartmentName("Engineering")
                .doOnNext(e -> log.info("{}", e))
                .as(StepVerifier::create)
                .expectNextCount(2)
                .expectComplete()
                .verify();
    }
    @Test
    public void findEmployeeWithDepartment() {
        this.departmentRepository.findEmployeeWithDepartment("alice@mail.com")
                .doOnNext(e -> log.info("{}", e))
                .as(StepVerifier::create)
                .assertNext(e -> {
                    Assertions.assertEquals("alice@mail.com", e.email());
                    Assertions.assertEquals("Human Resources", e.departmentName());
                })
                .expectComplete()
                .verify();
    }

}
