package pl.mikbac.example.example104.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.mikbac.example.example104.dto.EmployeeDto;
import reactor.core.publisher.Sinks;

/**
 * Created by MikBac on 25.01.2026
 */

@Configuration
public class AppConfig {

    @Bean
    public Sinks.Many<EmployeeDto> sink(){
        return Sinks.many().replay().limit(1);
    }

}
