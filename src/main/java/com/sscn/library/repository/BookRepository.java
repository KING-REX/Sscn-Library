package com.sscn.library.repository;

import com.sscn.library.entity.Author;
import com.sscn.library.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    boolean existsByTitle(String title);

//    boolean existsByAuthor(Author author);                No "author" column in Book. It's "authors"
//    boolean existsByAuthors(ArrayList<Author> authors); Too complex for the JpaRepository and doesn't even make sense pfft!

    boolean existsByDatePurchased(LocalDate datePurchased);

    boolean existsByIsbn(String isbn);

    Optional<Book> findByIsbn(String isbn);
    Optional<Book> findByTitle(String title);
    Optional<List<Book>> findAllByTitle(String Title);

    Optional<List<Book>> findAllByDatePurchased(LocalDate datePurchased);

    Optional<List<Book>> findByAuthors(Author author);

//    Optional<List<Book>> findAllByAuthor(Author author);              No "author" column in Book. It's "authors"
//    Optional<List<Book>> findAllByAuthors(ArrayList<Author> authors); Too complex for the JpaRepository and doesn't even make sense pfft!

//    @Transactional
//    void deleteByIsbn(String isbn);

//    @Transactional
//    void deleteAllByTitle(String title);
}