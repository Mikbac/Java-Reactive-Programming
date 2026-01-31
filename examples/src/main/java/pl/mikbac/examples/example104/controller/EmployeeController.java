package pl.mikbac.examples.example104.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.mikbac.examples.example104.dto.EmployeeDto;
import pl.mikbac.examples.example104.service.EmployeeService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by MikBac on 22.01.2026
 */

@RestController
@RequestMapping("employees/v4")
@RequiredArgsConstructor
@Slf4j
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    public Mono<EmployeeDto> saveEmployee(@RequestBody Mono<EmployeeDto> mono) {
        return this.employeeService.saveEmployee(mono);
    }

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<EmployeeDto> employeesStream() {
        return this.employeeService.getEmployeeStream();
    }

}
