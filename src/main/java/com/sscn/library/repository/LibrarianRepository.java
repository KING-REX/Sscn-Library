package com.sscn.library.repository;

import com.sscn.library.entity.Librarian;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LibrarianRepository extends JpaRepository<Librarian, Integer> {
    boolean existsByLastName(String lastName);
    boolean existsByEmail(String email);

    Optional<Librarian> findByEmail(String email);

    Optional<List<Librarian>> findAllByLastName(String lastName);

    Optional<List<Librarian>> findAllByFirstNameAndLastName(String firstName, String lastName);

    Optional<List<Librarian>> findAllByLastNameAndFirstName(String lastName, String firstName);
}
