package pl.mikbac.examples.example100.employeeCRUD.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.mikbac.examples.example100.employeeCRUD.dto.EmployeeDto;
import pl.mikbac.examples.example100.employeeCRUD.exception.ApplicationExceptions;
import pl.mikbac.examples.example100.employeeCRUD.filter.UserRole;
import pl.mikbac.examples.example100.employeeCRUD.service.EmployeeService;
import pl.mikbac.examples.example100.employeeCRUD.validator.RequestValidator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by MikBac on 04.01.2026
 */

@RestController
@RequestMapping("employees/v1")
@RequiredArgsConstructor
@Slf4j
@ConditionalOnProperty(prefix = "employees", name = "functional.endpoints", havingValue = "false")
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public Flux<EmployeeDto> getAllEmployees(@RequestAttribute("role") UserRole userRole) {
        log.info("Requested by user with role [userRole={}]", userRole.toString());
        return employeeService.getAllEmployees();
    }

    @GetMapping("paginated")
    public Flux<EmployeeDto> getAllEmployees(@RequestParam(defaultValue = "1") Integer page,
                                             @RequestParam(defaultValue = "3") Integer size) {
        return employeeService.getAllEmployees(page, size);
    }

    @GetMapping("{id}")
    public Mono<EmployeeDto> getEmployeeById(@PathVariable final Integer id) {
        return employeeService.getEmployeeById(id)
                .switchIfEmpty(ApplicationExceptions.employeeNotFound(id));
    }

    @PostMapping
    public Mono<EmployeeDto> saveEmployee(@RequestBody final Mono<EmployeeDto> employeeDto) {
        return employeeDto.transform(RequestValidator.validate())
                .as(employeeService::saveEmployee);
    }

    @PutMapping("{id}")
    public Mono<EmployeeDto> updateEmployee(@PathVariable final Integer id,
                                            @RequestBody final Mono<EmployeeDto> employeeDto) {
        return employeeDto.transform(RequestValidator.validate())
                .as(validReq -> this.employeeService.updateEmployee(id, validReq))
                .switchIfEmpty(ApplicationExceptions.employeeNotFound(id));
    }

    @DeleteMapping("{id}")
    public Mono<Void> deleteEmployee(@PathVariable final Integer id) {
        return employeeService.deleteEmployee(id)
                .filter(b -> b)
                .switchIfEmpty(ApplicationExceptions.employeeNotFound(id))
                .then();
    }

}
