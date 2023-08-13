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
	CommandLineRunner runner(BookRepository bookRepository, AuthorRepository authorRepository, UserService userService) {
		return args -> {

//			Author john = new Author("G.P", "Taylor");
//			Author lettuce = new Author("Lettuce", "Correl");
//			Author mary = new Author("Mary", "Doe");
//
//			Book book1 = new Book("ISBN001", "Shadowmancer", LocalDate.of(2004, 8, 13), 30, 30);
//			book1.getAuthors().addAll(List.of(john, lettuce));
//
////			authorRepository.saveAll(List.of(john, lettuce, mary));
//
//
//			bookRepository.save(book1);
//			authorRepository.save(mary);


//			Optional<List<Author>> findBlah = authorRepository.findAllByLastName("Doe");
//			findBlah.ifPresent(a -> System.out.println(a.toString()));
//			findBlah.ifPresent(System.out::println);

//			authorRepository.findAllByLastName("Doe").ifPresent(authorList -> authorList.forEach(System.out::println));


//			User user = new User("admin", "secret", "ADMIN");
//
//			userService.addUser(user);

		};
	}
	public static void main(String[] args) {
		SpringApplication.run(SscnLibraryApplication.class, args);
	}

}
