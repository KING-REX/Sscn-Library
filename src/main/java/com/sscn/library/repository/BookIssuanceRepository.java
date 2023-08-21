package com.sscn.library.repository;

import com.sscn.library.entity.Book;
import com.sscn.library.entity.BookIssuance;
import com.sscn.library.entity.Member;
import com.sscn.library.entity.BorrowStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@Repository
public interface BookIssuanceRepository extends JpaRepository<BookIssuance, Integer> {

    boolean existsByBookIssued(Book bookIssued);
    boolean existsByIssuedTo(Member issuedTo);

    boolean existsByDateIssued(LocalDate dataIssued);
    boolean existsByDateDue(LocalDate dateDue);

    boolean existsByBorrowStatus(BorrowStatus borrowStatus);

    Optional<BookIssuance> findByBookIssued(Book book);
    Optional<BookIssuance> findByIssuedTo(Member issuedTo);

    Optional<List<BookIssuance>> findAllByBookIssued(Book book);

    Optional<List<BookIssuance>> findAllByIssuedTo(Member issuedTo);

    Optional<BookIssuance> findByDateIssued(LocalDate dateIssued);
    Optional<List<BookIssuance>> findAllByDateIssued(LocalDate dateIssued);
    Optional<List<BookIssuance>> findAllByDateDue(LocalDate dateDue);
//    Optional<List<BookIssuance>> findAllByCopiesIssued(Integer copiesIssued);
    Optional<List<BookIssuance>> findAllByCopiesIssued(Integer copiesIssued);
    Optional<List<BookIssuance>> findAllByBorrowStatus(BorrowStatus borrowStatus);
}
