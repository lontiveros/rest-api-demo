package dev.leom.restapidemo.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository repository) {
        return args -> {
            Student luis =  Student.builder()
                    .name("Luis")
                    .email("luis@example.com")
                    .dob(LocalDate.of(1990, 6, 11))
                    .build();

            repository.saveAll(List.of(luis));
        };
    }
}
