package pl.mikbac.examples.example100;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

/**
 * Created by MikBac on 11.10.2025
 */

@SpringBootApplication(scanBasePackages = {"pl.mikbac.examples.example100.simple",
        "pl.mikbac.examples.example100.employeeCRUD"})
@EnableR2dbcRepositories(basePackages = "pl.mikbac.examples.example100.employeeCRUD")
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

}
