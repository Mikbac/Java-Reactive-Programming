package pl.mikbac.example203;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Example203Application {

    public static void main(String[] args) {
        SpringApplication.run(Example203Application.class, args);
    }

}
