package pl.mikbac.example.example105;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

/**
 * Created by MikBac on 08.03.2026
 */

@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
public class UserDocument {

    @MongoId
    private String id;
    private String username;

}
