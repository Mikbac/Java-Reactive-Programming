package pl.mikbac.examples.example103.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

/**
 * Created by MikBac on 03.01.2026
 */

@Table(name = "EMPLOYEE")
@Data
@Builder
public class EmployeeModel {

    @Id
    private Integer id;
    private String name;
    private String email;
    private Instant createdAt;

}
