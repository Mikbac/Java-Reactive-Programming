package pl.mikbac.examples.example100.employeeCRUD.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import pl.mikbac.examples.example100.employeeCRUD.exception.EmployeeNotFoundException;
import pl.mikbac.examples.example100.employeeCRUD.exception.InvalidInputException;
import reactor.core.publisher.Mono;

import java.net.URI;

/**
 * Created by MikBac on 17.01.2026
 */

@Service
public class ApplicationExceptionHandler {

    public Mono<ServerResponse> handleException(EmployeeNotFoundException ex, ServerRequest request) {
        final ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problem.setType(URI.create("http://domain.com/problems/employee-not-found"));
        problem.setTitle("Employee Not Found");
        problem.setInstance(URI.create(request.path()));
        return ServerResponse.status(HttpStatus.NOT_FOUND).bodyValue(problem);
    }

    public Mono<ServerResponse> handleException(InvalidInputException ex, ServerRequest request) {
        final ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problem.setTitle("Invalid Input");
        problem.setInstance(URI.create(request.path()));
        return ServerResponse.status(HttpStatus.BAD_REQUEST).bodyValue(problem);
    }

}
