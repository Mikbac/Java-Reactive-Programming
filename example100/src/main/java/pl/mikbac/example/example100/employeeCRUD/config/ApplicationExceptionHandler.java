package pl.mikbac.example.example100.employeeCRUD.config;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import pl.mikbac.example.example100.employeeCRUD.exception.EmployeeNotFoundException;
import pl.mikbac.example.example100.employeeCRUD.exception.InvalidInputException;
import reactor.core.publisher.Mono;

import java.net.URI;

/**
 * Created by MikBac on 17.01.2026
 */

@Service
public class ApplicationExceptionHandler {

    public Mono<ServerResponse> handleException(EmployeeNotFoundException ex, ServerRequest request) {
        final ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(404), ex.getMessage());
        problem.setType(URI.create("http://domain.com/problems/employee-not-found"));
        problem.setTitle("Employee Not Found");
        problem.setInstance(URI.create(request.path()));
        return ServerResponse.status(HttpStatusCode.valueOf(404)).bodyValue(problem);
    }

    public Mono<ServerResponse> handleException(InvalidInputException ex, ServerRequest request) {
        final ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400), ex.getMessage());
        problem.setTitle("Invalid Input");
        problem.setInstance(URI.create(request.path()));
        return ServerResponse.status(HttpStatusCode.valueOf(400)).bodyValue(problem);
    }

}
