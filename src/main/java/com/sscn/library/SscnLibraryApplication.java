package com.sscn.library;

import com.sscn.library.entity.Author;
import com.sscn.library.repository.AuthorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class SscnLibraryApplication {

	@Bean
	CommandLineRunner runner(AuthorRepository authorRepository) {
		return args -> {
			authorRepository.save(new Author("John", "Doe"));
			authorRepository.save(new Author("Lettuce", "Correl"));
			authorRepository.save(new Author("Mary", "Doe"));
//			Optional<List<Author>> findBlah = authorRepository.findAllByLastName("Doe");
//			findBlah.ifPresent(a -> System.out.println(a.toString()));
//			findBlah.ifPresent(System.out::println);

//			authorRepository.findAllByLastName("Doe").ifPresent(authorList -> authorList.forEach(System.out::println));



		};
	}
	public static void main(String[] args) {
		SpringApplication.run(SscnLibraryApplication.class, args);
	}

}
