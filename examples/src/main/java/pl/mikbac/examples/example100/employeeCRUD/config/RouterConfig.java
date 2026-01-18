package pl.mikbac.examples.example100.employeeCRUD.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import pl.mikbac.examples.example100.employeeCRUD.exception.EmployeeNotFoundException;
import pl.mikbac.examples.example100.employeeCRUD.exception.InvalidInputException;

/**
 * Created by MikBac on 13.01.2026
 */

// Functional endpoints

@Slf4j
@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "employees", name = "functional.endpoints", havingValue = "true")
public class RouterConfig {

    private final ApplicationExceptionHandler exceptionHandler;
    private final EmployeeRequestHandler employeeRequestHandler;

    @Bean
    public RouterFunction<ServerResponse> employeeRoutes() {
        return RouterFunctions.route()
                .path("/employees/v2", this::getEmployeeRoutes)
                .POST("/employees/v2", employeeRequestHandler::saveEmployee)
                .PUT("/employees/v2/{id}", employeeRequestHandler::updateEmployee)
                .DELETE("/employees/v2/{id}", employeeRequestHandler::deleteEmployee)
                .onError(EmployeeNotFoundException.class, this.exceptionHandler::handleException)
                .onError(InvalidInputException.class, this.exceptionHandler::handleException)
                .filter(((request, next) -> {
                    log.info("Incoming request: method={}, path={}, headers={}",
                            request.method(),
                            request.path(),
                            request.headers().asHttpHeaders());
                    return next.handle(request);
                }))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> getEmployeeRoutes() {
        return RouterFunctions.route()
                .GET("/paginated", employeeRequestHandler::getPaginatedEmployees)
                .GET("/{id}", employeeRequestHandler::getEmployeeById)
                .GET(employeeRequestHandler::getAllEmployees)
                .build();
    }

}
