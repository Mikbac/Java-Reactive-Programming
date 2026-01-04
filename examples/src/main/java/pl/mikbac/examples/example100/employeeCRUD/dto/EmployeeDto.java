package pl.mikbac.examples.example100.employeeCRUD.dto;

import lombok.Builder;

/**
 * Created by MikBac on 04.01.2026
 */

@Builder
public record EmployeeDto(Integer id,
                          String name,
                          String email) {
}
