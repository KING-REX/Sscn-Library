package com.sscn.library.repository;

import com.sscn.library.entity.Author;
import com.sscn.library.entity.Book;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {

//    boolean existsByFirstName(String firstName);
    boolean existsByLastName(String lastName);

//    boolean existsByBook(Book book);          No "book" column in Author. It's "books"
//    boolean existsByBooks(List<Book> books); Too complex for the JpaRepository and doesn't even make sense pfft!


//    Optional<Author> findByLastName(String lastName);

    Optional<Author> findByFirstNameAndLastName(String firstName, String lastName);
    Optional<List<Author>> findAllByFirstNameAndLastName(String firstName, String lastName);
    Optional<List<Author>> findAllByLastName(String lastName);



//    Optional<List<Author>> findAllByBook(Book book);          No "book" column in Author. It's "books"
//    Optional<List<Author>> findAllByBooks(List<Book> books); Too complex for the JpaRepository and doesn't even make sense pfft!

}
