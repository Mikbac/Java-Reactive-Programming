package pl.mikbac.examples.example103.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.mikbac.examples.example103.dto.EmployeeDto;
import pl.mikbac.examples.example103.dto.UploadResponse;
import pl.mikbac.examples.example103.service.EmployeeService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Created by MikBac on 22.01.2026
 */

@RestController
@RequestMapping("employees/v3")
@RequiredArgsConstructor
@Slf4j
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping(value = "upload", consumes = MediaType.APPLICATION_NDJSON_VALUE)
    public Mono<UploadResponse> uploadEmployees(@RequestBody Flux<EmployeeDto> flux) {
        return this.employeeService.saveEmployees(flux.doOnNext(e -> log.info("New employee [{}]", e.email())))
                .then(this.employeeService.getEmployeesCount())
                .map(count -> new UploadResponse(UUID.randomUUID(), count));
    }

    @GetMapping(value = "download", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<EmployeeDto> downloadEmployees() {
        return this.employeeService.getAllEmployees();
    }

}
