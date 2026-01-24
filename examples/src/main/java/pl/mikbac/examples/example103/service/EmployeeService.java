package pl.mikbac.examples.example103.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mikbac.examples.example103.dto.EmployeeDto;
import pl.mikbac.examples.example103.mapper.EmployeeMapper;
import pl.mikbac.examples.example103.repository.EmployeeRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by MikBac on 22.01.2026
 */

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public Flux<EmployeeDto> saveEmployees(Flux<EmployeeDto> employeeDtoFlux) {
        return employeeDtoFlux.map(EmployeeMapper::toModel)
                .as(this.employeeRepository::saveAll)
                .map(EmployeeMapper::toDto);
    }

    public Mono<Long> getEmployeesCount() {
        return this.employeeRepository.count();
    }

    public Flux<EmployeeDto> getAllEmployees() {
        return this.employeeRepository.findAll()
                .map(EmployeeMapper::toDto);
    }

}
