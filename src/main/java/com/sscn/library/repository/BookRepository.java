package com.sscn.library.repository;

import com.sscn.library.entity.Author;
import com.sscn.library.entity.Book;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToMany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    boolean existsByTitle(String title);

    boolean existsByAuthor(Author author);

    boolean existsByDatePurchased(LocalDate datePurchased);

    Optional<Book> findByTitle(String title);
    Optional<List<Book>> findAllByTitle(String Title);

    Optional<List<Book>> findAllByDatePurchased(LocalDate datePurchased);

    Optional<List<Book>> findAllByAuthor(Author author);
}