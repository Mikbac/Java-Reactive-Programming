package pl.mikbac.example201;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableCaching
@EnableScheduling
@SpringBootApplication
public class Example201Application {

    public static void main(String[] args) {
        SpringApplication.run(Example201Application.class, args);
    }

}
