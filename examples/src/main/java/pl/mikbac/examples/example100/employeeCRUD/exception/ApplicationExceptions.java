package pl.mikbac.examples.example100.employeeCRUD.exception;

import reactor.core.publisher.Mono;

/**
 * Created by MikBac on 6.01.2026
 */

public class ApplicationExceptions {

    public static <T> Mono<T> employeeNotFound(Integer id) {
        return Mono.error(new EmployeeNotFoundException(id));
    }

    public static <T> Mono<T> missingName() {
        return Mono.error(new InvalidInputException("Name is required"));
    }

    public static <T> Mono<T> missingValidEmail() {
        return Mono.error(new InvalidInputException("Valid email is required"));
    }

}
