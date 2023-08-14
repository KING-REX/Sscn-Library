package com.sscn.library;

import com.sscn.library.entity.Author;
import com.sscn.library.entity.Authority;
import com.sscn.library.entity.Book;
import com.sscn.library.entity.User;
import com.sscn.library.repository.AuthorRepository;
import com.sscn.library.repository.BookRepository;
import com.sscn.library.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class SscnLibraryApplication {

	@Bean
	CommandLineRunner runner() {
		return args -> {
		};
	}
	public static void main(String[] args) {
		SpringApplication.run(SscnLibraryApplication.class, args);
	}

}
