package com.sscn.library.repository;

import com.sscn.library.entity.BookIssuance;
import com.sscn.library.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

enum ReturnStatus {
    BORROWED, RETURNED, OVERDUE
}
@Repository
public interface BookIssuanceRepository extends JpaRepository<BookIssuance, Integer> {

    boolean existsByIssuedTo(Member issuedTo);

    boolean existsByDateIssued(LocalDate dataIssued);
    boolean existsByDateDue(LocalDate datadue);

    boolean existsByReturnStatus(ReturnStatus returnStatus);

    Optional<List<BookIssuance>> findAllByIssuedTo(Member issuedTo);
    Optional<List<BookIssuance>> findAllByDateIssued(LocalDate dateIssued);
    Optional<List<BookIssuance>> findAllByDateDue(LocalDate dateDue);
    Optional<List<BookIssuance>> findAllByReturnStatus(ReturnStatus returnStatus);
}