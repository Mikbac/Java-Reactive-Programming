package pl.mikbac.examples.example100.employeeCRUD.mapper;

import lombok.experimental.UtilityClass;
import pl.mikbac.examples.example100.employeeCRUD.dto.EmployeeDto;
import pl.mikbac.examples.example100.employeeCRUD.model.EmployeeModel;

/**
 * Created by MikBac on 04.01.2026
 */

@UtilityClass
public class EmployeeMapper {

    public EmployeeModel toModel(final EmployeeDto employee) {
        return EmployeeModel.builder()
                .id(employee.id())
                .name(employee.name())
                .email(employee.email())
                .build();
    }

    public EmployeeDto toDto(final EmployeeModel employeeModel) {
        return EmployeeDto.builder()
                .id(employeeModel.getId())
                .name(employeeModel.getName())
                .email(employeeModel.getEmail())
                .build();
    }

}
