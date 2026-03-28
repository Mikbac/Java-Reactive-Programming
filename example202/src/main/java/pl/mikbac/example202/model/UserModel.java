package pl.mikbac.example202.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Created by MikBac on 26.03.2026
 */

@Data
@Builder
@Table("users")
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {
    @Id
    private Integer id;
    private String username;
    private String email;
}
