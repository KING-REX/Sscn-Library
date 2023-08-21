package com.sscn.library.repository;

import com.sscn.library.entity.Book;
import com.sscn.library.entity.BookIssuance;
import com.sscn.library.entity.BookReturns;
import com.sscn.library.entity.ReturnStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookReturnsRepository extends JpaRepository<BookReturns, Integer> {

    boolean existsByBookReturned(Book bookReturned);
    boolean existsByBookIssuance(BookIssuance bookIssuance);
    boolean existsByDateReturned(LocalDate dateReturned);

    Optional<List<BookReturns>> findAllByBookReturned(Book bookReturned);
    Optional<List<BookReturns>> findAllByBookIssuance(BookIssuance bookIssuance);
    Optional<List<BookReturns>> findAllByReturnStatus(ReturnStatus returnStatus);
    Optional<List<BookReturns>> findAllByDateReturned(LocalDate dateReturned);
}
