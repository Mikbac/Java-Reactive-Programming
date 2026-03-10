package pl.mikbac.example.example103;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

/**
 * Created by MikBac on 11.10.2025
 */

@SpringBootApplication
@EnableR2dbcRepositories(basePackages = "pl.mikbac.example.example103")
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }


}
