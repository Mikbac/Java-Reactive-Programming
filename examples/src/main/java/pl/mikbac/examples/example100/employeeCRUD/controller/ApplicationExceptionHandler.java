package pl.mikbac.examples.example100.employeeCRUD.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.mikbac.examples.example100.employeeCRUD.exception.EmployeeNotFoundException;
import pl.mikbac.examples.example100.employeeCRUD.exception.InvalidInputException;

import java.net.URI;

/**
 * Created by MikBac on 7.01.2026
 */

@ControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ProblemDetail handleException(EmployeeNotFoundException ex) {
        final ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problem.setType(URI.create("http://domain.com/problems/employee-not-found"));
        problem.setTitle("Employee Not Found");
        return problem;
    }

    @ExceptionHandler(InvalidInputException.class)
    public ProblemDetail handleException(InvalidInputException ex) {
        final ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problem.setTitle("Invalid Input");
        return problem;
    }

}
