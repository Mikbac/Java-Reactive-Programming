package pl.mikbac.examples.example100.employeeCRUD.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import pl.mikbac.examples.example100.employeeCRUD.AbstractTest;
import pl.mikbac.examples.example100.employeeCRUD.model.EmployeeModel;
import reactor.test.StepVerifier;

/**
 * Created by MikBac on 03.01.2026
 */

@Slf4j
class EmployeeRepositoryTest extends AbstractTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void findAllShouldReturnAllEmployees() {
        this.employeeRepository.findAll()
                .doOnNext(e -> log.info("{}", e))
                .as(StepVerifier::create)
                .expectNextCount(8)
                .expectComplete()
                .verify();
    }

    @Test
    public void findById() {
        this.employeeRepository.findById(3)
                .doOnNext(e -> log.info("{}", e))
                .as(StepVerifier::create)
                .assertNext(e -> Assertions.assertEquals("john@mail.com", e.getEmail()))
                .expectComplete()
                .verify();
    }

    @Test
    public void findByEmail() {
        this.employeeRepository.findByEmail("alice@mail.com")
                .doOnNext(e -> log.info("{}", e))
                .as(StepVerifier::create)
                .assertNext(e -> Assertions.assertEquals("alice@mail.com", e.getEmail()))
                .expectComplete()
                .verify();
    }

    @Test
    public void findByEmailEndingWith() {
        this.employeeRepository.findByEmailEndsWith("email.com")
                .doOnNext(e -> log.info("{}", e))
                .as(StepVerifier::create)
                .expectNextCount(4)
                .expectComplete()
                .verify();
    }

    @Test
    public void findByWithPageable() {
        this.employeeRepository.findBy(PageRequest.of(0, 3).withSort(Sort.by("email").ascending()))
                .doOnNext(e -> log.info("{}", e))
                .as(StepVerifier::create)
                .assertNext(e -> Assertions.assertEquals("alice@mail.com", e.getEmail()))
                .assertNext(e -> Assertions.assertEquals("bob@mail.com", e.getEmail()))
                .assertNext(e -> Assertions.assertEquals("john@mail.com", e.getEmail()))
                .expectComplete()
                .verify();
    }

    @Test
    public void insertAndDeleteEmployee() {
        // insert
        final EmployeeModel employee = EmployeeModel.builder()
                .email("newUser@mail.com")
                .departmentId(2)
                .build();
        this.employeeRepository.save(employee)
                .doOnNext(e -> log.info("{}", e))
                .as(StepVerifier::create)
                .assertNext(e -> Assertions.assertNotNull(e.getId()))
                .expectComplete()
                .verify();

        // count
        this.employeeRepository.count()
                .as(StepVerifier::create)
                .expectNext(9L)
                .expectComplete()
                .verify();

        // delete
        this.employeeRepository.deleteById(9)
                .then(this.employeeRepository.count())
                .as(StepVerifier::create)
                .expectNext(8L)
                .expectComplete()
                .verify();
    }

    @Test
    public void updateEmployee() {
        this.employeeRepository.findByEmail("bob@mail.com")
                .doOnNext(e -> e.setDepartmentId(3))
                .flatMap(e -> this.employeeRepository.save(e))
                .doOnNext(e -> log.info("{}", e))
                .as(StepVerifier::create)
                .assertNext(e -> Assertions.assertEquals(3, e.getDepartmentId()))
                .expectComplete()
                .verify();
    }

}
