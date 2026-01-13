package pl.mikbac.examples.example100.employeeCRUD.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * Created by MikBac on 13.01.2026
 */

// Functional endpoints

@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "employees", name = "functional.endpoints", havingValue = "true")
public class RouterConfig {

    //    private final ApplicationExceptionHandler exceptionHandler;
    private final EmployeeRequestHandler employeeRequestHandler;

    @Bean
    public RouterFunction<ServerResponse> employeeRoutes() {
        return RouterFunctions.route()
                .GET("/employees/v2", employeeRequestHandler::getAllEmployees)
//                .GET("/employees/v2/paginated", employeeRequestHandler::paginatedEmployees)
                .GET("/employees/v2/{id}", employeeRequestHandler::getEmployeeById)
                .POST("/employees/v2", employeeRequestHandler::saveEmployee)
                .PUT("/employees/v2/{id}", employeeRequestHandler::updateEmployee)
                .DELETE("/employees/v2/{id}", employeeRequestHandler::deleteEmployee)
//                .onError(EmployeeNotFoundException.class, this.exceptionHandler::handleException)
//                .onError(InvalidInputException.class, this.exceptionHandler::handleException)
                .build();
    }

}
