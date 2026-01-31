package pl.mikbac.examples.example104.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mikbac.examples.example104.dto.EmployeeDto;
import pl.mikbac.examples.example104.mapper.EmployeeMapper;
import pl.mikbac.examples.example104.repository.EmployeeRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

/**
 * Created by MikBac on 22.01.2026
 */

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final Sinks.Many<EmployeeDto> sink;

    public Mono<EmployeeDto> saveEmployee(Mono<EmployeeDto> employeeDtoMono) {
        return employeeDtoMono.map(EmployeeMapper::toModel)
                .flatMap(this.employeeRepository::save)
                .map(EmployeeMapper::toDto)
                .doOnNext(sink::tryEmitNext);
    }

    public Flux<EmployeeDto> getEmployeeStream() {
        return sink.asFlux();
    }


}
