package pl.mikbac.examples.example100.employeeCRUD.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.mikbac.examples.example100.employeeCRUD.dto.EmployeeDto;
import pl.mikbac.examples.example100.employeeCRUD.mapper.EmployeeMapper;
import pl.mikbac.examples.example100.employeeCRUD.repository.EmployeeRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by MikBac on 04.01.2026
 */

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public Flux<EmployeeDto> getAllEmployees() {
        return employeeRepository.findAll()
                .map(EmployeeMapper::toDto);
    }

    public Flux<EmployeeDto> getAllEmployees(int page, int size) {
        return employeeRepository.findBy(PageRequest.of(page - 1, size))
                .map(EmployeeMapper::toDto);
    }

    public Mono<EmployeeDto> getEmployeeById(final Integer id) {
        return employeeRepository.findById(id)
                .map(EmployeeMapper::toDto);
    }

    public Mono<EmployeeDto> saveEmployee(final Mono<EmployeeDto> employeeDto) {
        return employeeDto.map(EmployeeMapper::toModel)
                .flatMap(employeeRepository::save)
                .map(EmployeeMapper::toDto);
    }

    public Mono<EmployeeDto> updateEmployee(final Integer id, final Mono<EmployeeDto> employeeDto) {
        return employeeRepository.findById(id)
                .flatMap(e -> employeeDto)
                .map(EmployeeMapper::toModel)
                .doOnNext(e -> e.setId(id))
                .flatMap(employeeRepository::save)
                .map(EmployeeMapper::toDto);
    }

    public Mono<Boolean> deleteEmployee(final Integer id) {
        return employeeRepository.deleteEmployeeById(id);
    }

}
