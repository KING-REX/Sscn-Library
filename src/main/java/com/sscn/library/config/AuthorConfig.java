package com.sscn.library.config;

import com.sscn.library.entity.Author;
import com.sscn.library.repository.AuthorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class AuthorConfig {

    CommandLineRunner runner (AuthorRepository authorRepository) {
        return args -> {
            Author john = new Author("John", "Doe");
            Author lettuce = new Author("Lettuce", "Correl");
            Author mary = new Author("Mary", "Doe");

            authorRepository.saveAll(List.of(john, lettuce, mary));
        };
    };
}
