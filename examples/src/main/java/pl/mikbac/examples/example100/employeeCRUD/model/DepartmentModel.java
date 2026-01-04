package pl.mikbac.examples.example100.employeeCRUD.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Created by MikBac on 03.01.2026
 */

@Table(name = "DEPARTMENT")
@Data
public class DepartmentModel {

    @Id
    private Integer id;
    private String name;

}
