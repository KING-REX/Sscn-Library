package com.sscn.library.repository;

import com.sscn.library.entity.Book;
import com.sscn.library.entity.BookIssuance;
import com.sscn.library.entity.BookReturns;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookReturnsRepository extends JpaRepository<BookReturns, Integer> {

    boolean existsByBookIssued(Book bookIssued);

    boolean existsByBookIssuance(BookIssuance bookIssuance);
    boolean existsByDateReturned(LocalDate dateReturned);

    Optional<List<BookReturns>> findAllByBookIssued(Book bookIssued);
    Optional<List<BookReturns>> findAllByBookIssuance(BookIssuance bookIssuance);
    Optional<List<BookReturns>> findAllByDateReturned(LocalDate dateReturned);
}
