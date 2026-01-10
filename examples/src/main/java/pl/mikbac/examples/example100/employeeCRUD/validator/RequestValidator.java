package pl.mikbac.examples.example100.employeeCRUD.validator;

import org.apache.commons.lang3.StringUtils;
import pl.mikbac.examples.example100.employeeCRUD.dto.EmployeeDto;
import pl.mikbac.examples.example100.employeeCRUD.exception.ApplicationExceptions;
import reactor.core.publisher.Mono;

import java.util.function.Predicate;
import java.util.function.UnaryOperator;

/**
 * Created by MikBac on 6.01.2026
 */
public class RequestValidator {

    public static UnaryOperator<Mono<EmployeeDto>> validate() {
        return mono -> mono.filter(hasName())
                .switchIfEmpty(ApplicationExceptions.missingName())
                .filter(hasValidEmail())
                .switchIfEmpty(ApplicationExceptions.missingValidEmail());
    }

    private static Predicate<EmployeeDto> hasName() {
        return dto -> StringUtils.isNotBlank(dto.name());
    }

    private static Predicate<EmployeeDto> hasValidEmail() {
        return dto -> StringUtils.isNotBlank(dto.email()) && dto.email().contains("@");
    }

}
