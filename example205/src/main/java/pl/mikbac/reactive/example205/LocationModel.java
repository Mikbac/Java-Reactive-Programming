package pl.mikbac.reactive.example205;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created by MikBac on 05.04.2026
 */

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LocationModel {

    private String name;
    private double longitude;
    private double latitude;

}
