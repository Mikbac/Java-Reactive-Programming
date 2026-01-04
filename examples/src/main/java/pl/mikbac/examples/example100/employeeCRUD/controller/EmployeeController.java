package pl.mikbac.examples.example100.employeeCRUD.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.mikbac.examples.example100.employeeCRUD.dto.EmployeeDto;
import pl.mikbac.examples.example100.employeeCRUD.service.EmployeeService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by MikBac on 04.01.2026
 */

@RestController
@RequestMapping("employees/v1")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public Flux<EmployeeDto> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("{id}")
    public Mono<EmployeeDto> getEmployeeById(@PathVariable final Integer id) {
        return employeeService.getEmployeeById(id);
    }

    @PostMapping
    public Mono<EmployeeDto> saveEmployee(@RequestBody final Mono<EmployeeDto> employeeDto) {
        return employeeService.saveEmployee(employeeDto);
    }

    @PutMapping("{id}")
    public Mono<EmployeeDto> updateEmployee(@PathVariable final Integer id,
                                            @RequestBody final Mono<EmployeeDto> employeeDto) {
        return employeeService.updateEmployee(id, employeeDto);
    }

    @DeleteMapping("{id}")
    public Mono<Void> deleteEmployee(@PathVariable final Integer id) {
        return employeeService.deleteEmployee(id);
    }

}
