package pl.mikbac.examples.example100.employeeCRUD.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import pl.mikbac.examples.example100.employeeCRUD.dto.EmployeeDto;
import pl.mikbac.examples.example100.employeeCRUD.exception.ApplicationExceptions;
import pl.mikbac.examples.example100.employeeCRUD.filter.UserRole;
import pl.mikbac.examples.example100.employeeCRUD.service.EmployeeService;
import pl.mikbac.examples.example100.employeeCRUD.validator.RequestValidator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by MikBac on 13.01.2026
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeRequestHandler {

    private final EmployeeService employeeService;

    public Mono<ServerResponse> getAllEmployees(ServerRequest request) {
        log.info("Requested for employees [pathVariables={}, roleAttribute={}, headers={}, queryParams={}]",
                request.pathVariables(),
                request.attribute("role"),
                request.headers(),
                request.queryParams());

        return employeeService.getAllEmployees()
                .as(f -> ServerResponse.ok().body(f, EmployeeDto.class));
    }

//
//    public Flux<EmployeeDto> getAllEmployees(@RequestParam(defaultValue = "1") Integer page,
//                                             @RequestParam(defaultValue = "3") Integer size) {
//        return employeeService.getAllEmployees(page, size);
//    }
//
    public Mono<ServerResponse> getEmployeeById(ServerRequest request) {
        int id = Integer.parseInt(request.pathVariable("id"));
        return employeeService.getEmployeeById(id)
                .switchIfEmpty(ApplicationExceptions.employeeNotFound(id))
                .flatMap(ServerResponse.ok()::bodyValue);
    }

    public Mono<ServerResponse>  saveEmployee(ServerRequest request) {
        return request.bodyToMono(EmployeeDto.class)
                .transform(RequestValidator.validate())
                .as(employeeService::saveEmployee)
                .flatMap(ServerResponse.ok()::bodyValue);
    }

    public Mono<ServerResponse> updateEmployee(ServerRequest request) {
        int id = Integer.parseInt(request.pathVariable("id"));
        return request.bodyToMono(EmployeeDto.class)
                .transform(RequestValidator.validate())
                .as(validReq -> this.employeeService.updateEmployee(id, validReq))
                .switchIfEmpty(ApplicationExceptions.employeeNotFound(id))
                .flatMap(ServerResponse.ok()::bodyValue);
    }

    public Mono<ServerResponse> deleteEmployee(ServerRequest request) {
        int id = Integer.parseInt(request.pathVariable("id"));

        return employeeService.deleteEmployee(id)
                .filter(b -> b)
                .switchIfEmpty(ApplicationExceptions.employeeNotFound(id))
                .then(ServerResponse.ok().build());
    }

}
